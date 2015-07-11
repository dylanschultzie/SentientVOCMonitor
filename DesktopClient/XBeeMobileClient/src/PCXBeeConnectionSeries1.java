import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.wpan.RxResponse16;
import com.rapplogic.xbee.api.wpan.TxRequest16;
import com.rapplogic.xbee.api.wpan.TxRequestBase;
import com.rapplogic.xbee.api.wpan.TxStatusResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.util.ByteUtils;

import javax.swing.*;

/******************************************************************************
 * Author: Dylan Schultz
 * Date Created: 5/3/14
 * Date Modified: 5/20/14
 *
 * PCXBeeConnectionSeries1( String com, String addr )
 * Purpose: constructor. creates the connection between arduino and mobile
 *          client.
 * Entry: 2 strings, for the com port and address of remote arduino.
 * Exit: com port is opened for communication
 *
 * private boolean ParsePacket()
 * Purpose: Checks what type of file is received. If the received packet
 *          begins with "D", the connection will terminate. Else, will
 *          write packet data to file.
 * Entry: None
 * Exit: Writes packet data to file
 *
 * private void WriteToFile()
 * Purpose: writes packet data to file from external class
 * Entry: None
 * Exit: Writes packet data to file
 *
 * public void PingArduino()
 * Purpose: Sends an initial connect packet to arduino. This begins the packet
 *          transfer between arduino and mobile client.
 * Entry: None
 * Exit: None
 *
 * public boolean ReceivePacket()
 * Purpose: Allows for receiving packets from the remote arduino. Once packets are
 *          received, they are sent to the parsepacket() function in order to
 *          analyze their purpose.
 * Entry: None
 * Exit: returns bool if everything works correctly.
 *
 * public boolean OpenSerialConnection( String comPort )
 * Purpose: This can be used in leu of the constructor. Opens com port for
 *          data transfer.
 * Entry: None
 * Exit: returns boolean if everything works correctly, or not.
 *
 * public int SendShutdown()
 * Purpose: sends a shutdown packet to remote arduino. This packet will make
 *          the arduino unresponsive.
 * Entry: None
 * Exit: arduino will be unresponsive. shutdown dialog box will display.
 *
 * public int SendInitialize()
 * Purpose: sends an initialize packet to the remote arduino. this will
 *          set the time to begin counting from. DOES NOT REQUIRE TO CONNECT
 *          FIRST.
 * Entry: None
 * Exit: arduino will be fully ready to download packets from, once connected.
 *
 * *****************************************************************************/
public class PCXBeeConnectionSeries1 implements iPCXBeeConnection
{
    private XBee xbee = new XBee();
    private XBeeResponse response;
    private PCVolatileTextFile outFile;
    private int[] downloadSize;
    private int downloadsReceived;
    private XBeeAddress16 myAddr16 = new XBeeAddress16();
    private XBeeAddress16 theirAddr16 = new XBeeAddress16();
    private RxResponse16 zReceive;

    private int [][] downloadLevels;

    PCXBeeConnectionSeries1(String com) throws XBeeException
    {
        try
        {
            xbee.open(com, 9600);
        }
        catch( XBeeException x ){ }
        downloadsReceived = 0;
    }
    public int ParsePacket()
    {
        int write = 0;
        int [] packet = zReceive.getData();
        if( packet[0] == 'D' )           //packet[0] will be 'D' if it's a disconnect packet. 'D' in ascii is 68
        {
            write = 1;
        }
        else if( packet[0] == 'N' )
        {
            JFrame frame = new JFrame("Download compelete.");
            JOptionPane.showMessageDialog(frame, "There are no more packets to download.");
            write = 2;
        }
        else if( packet[0] == 'S' )
        {
            return 3;
        }
        else
        {
            if( outFile == null )
                outFile = new PCVolatileTextFile( zReceive.getSourceAddress(), zReceive.getData() );
            else
            {
                outFile.setPacketData(zReceive.getData());
                downloadsReceived++;
            }
            WriteToFile();
            write = 2;
        }
        return write;
    }
    public int [][] GetDownLoadLevels()
    {
        return downloadLevels;
    }
    public void WriteToFile()
    {
        try
        {
            outFile.WriteToFile();
        }
        catch( Exception ctch )
        {
            System.out.println(ctch.getMessage());
        }
    }
    private void SetDownloadSize()
    {
        downloadSize = zReceive.getData();
    }
    public int GetDownloadsReceived()
    {
        return downloadsReceived;
    }
    public int[] GetDownloadSize( )
    {
        return downloadSize;
    }
    public boolean PingArduino() throws XBeeException
    {
        boolean connection = false;
        XBeeResponse rsp;
        // this is the SH + SL address of the remote radio -- broadcast looking for address
        int[] payload = ByteUtils.stringToIntArray("Connect");
        TxRequest16 tx = new TxRequest16(new XBeeAddress16(0x00, 0x01), payload);
        //tx.setOption(TxRequest16.Option.BROADCAST);

        try
        {
            xbee.sendAsynchronous(tx);                                 //send packet
        }
        catch( XBeeException x){}
        try
        {
            rsp = xbee.getResponse(2000);                           //wait for a response
            if( rsp.getApiId() == ApiId.TX_STATUS_RESPONSE )
                rsp = xbee.getResponse(2000);
        }
        catch( Exception x){ rsp = null; }

        if( rsp != null && rsp.getApiId() == ApiId.RX_16_RESPONSE)
        {
            zReceive = (RxResponse16)rsp;
            theirAddr16 = zReceive.getRemoteAddress();           //sets remote address much like an ARP
            SetDownloadSize();                                     //sets how many packets should arrive
            System.out.println( zReceive.toString());
            connection = true;
        }
        return connection;
    }
    public boolean SendDownload() throws Exception
    {
        boolean download = false;

        // this is the SH + SL address of the remote radio -- broadcast looking for address
        int[] payload = ByteUtils.stringToIntArray("Transfer");
        TxRequest16 tx = new TxRequest16(theirAddr16, payload);

        xbee.sendAsynchronous(tx);                                 //send packet

        if( ReceivePacket() == 2 )
            download = true;

        return download;
    }
    public int SendDisconnect() throws Exception
    {
        int disconnect = 0;
        // this is the SH + SL address of the remote radio -- broadcast looking for address
        int[] payload = ByteUtils.stringToIntArray("Disconnect");
        TxRequest16 tx = new TxRequest16(theirAddr16, payload);
        xbee.sendAsynchronous(tx);                                 //send packet

        disconnect = ReceivePacket();
        return disconnect;
    }
    public int ReceivePacket() throws Exception
    {
        int responseCaptured = 1;
        int downloadSizeCheck = 0;
        try
        {
            response = xbee.getResponse(5000);
            if(  response.getApiId() == ApiId.TX_STATUS_RESPONSE)
                response = xbee.getResponse(5000);
        }
        catch( Exception xcpt ){System.out.println("Download XBee Timeout.");}

        if( response.getApiId() == ApiId.RX_16_RESPONSE )
        {
            responseCaptured = 0;
            while( responseCaptured != 1 && (downloadSizeCheck != downloadSize[0]) )
            {
                downloadSizeCheck++;
                zReceive = (RxResponse16)response;                //convert response into a Rx response packet so it can be worked with
                responseCaptured = ParsePacket();                   //parse packet. if responseCaptured is false, connection is severed.
                if(downloadSizeCheck != downloadSize[0])
                {
                    try
                    {
                        response = xbee.getResponse(5000);                  //wait 5 seconds for next packet
                    }catch(Exception xcpt){ }
                }
            }

            if( downloadSizeCheck != (downloadSize[0]))
            {
                //System.out.println("Packets lost");                 //compares how many packets were received to how many were to be sent.
                System.out.println( downloadSize[0]);
                System.out.println(downloadSizeCheck);
            }
        }
        return responseCaptured;
    }

    @Override
    public int SendShutdown() throws Exception
    {
        int shutdown = 0;

        // this is the SH + SL address of the remote radio -- broadcast looking for address
        int[] payload = ByteUtils.stringToIntArray("Shutdown");
        TxRequest16 tx = new TxRequest16(theirAddr16, payload);

        xbee.sendAsynchronous(tx);                                 //send packet
        if( ReceivePacket() == 3 )
            shutdown = 1;

        return shutdown;
    }

    @Override
    public int SendInitialize( String initString, String time ) throws Exception
    {
        int init = 0;
        PCVolatileTextFile outfile = new PCVolatileTextFile();
        XBeeResponse rsp;
        // this is the SH + SL address of the remote radio -- broadcast looking for address
        int[] payload = ByteUtils.stringToIntArray("Initialize");
        TxRequest16 tx = new TxRequest16(new XBeeAddress16(0x00, 0x01), payload);

        outfile.WriteInitialize(new XBeeAddress16(0x00, 0x01), initString);

        try
        {
            xbee.sendAsynchronous(tx);                                 //send packet
        }
        catch( XBeeException x){}

        try
        {
            rsp = xbee.getResponse(2000);                           //wait for a response
            if( rsp.getApiId() == ApiId.TX_STATUS_RESPONSE )
            {
                //time += "11111";
                payload = ByteUtils.stringToIntArray(time);
                Thread.sleep(200);
                tx = new TxRequest16(new XBeeAddress16(0x00, 0x01), payload);
                xbee.sendAsynchronous(tx);
                rsp = xbee.getResponse(2000);
                if( rsp.getApiId() == ApiId.TX_STATUS_RESPONSE )
                    init = 1;
            }
        }
        catch( Exception x){ rsp = null; }
        return init;
    }

    public boolean OpenSerialConnection( String comPort )
    {
        try
        {
            xbee.open(comPort, 9600 );
        }
        catch (XBeeException xcpt)
        {
            System.out.print(xcpt.getMessage());
        }
        return xbee.isConnected();
    }
    public void SetAddress(int b1, int b2)
    {
        myAddr16 = new XBeeAddress16( b1, b2 );
    }
    public void SetAddress(String addressStr)
    {
        myAddr16 = new XBeeAddress16(addressStr.charAt(0),addressStr.charAt(1));
    }
    public void SetAddress(int[] address)
    {
        myAddr16 = new XBeeAddress16(address);
    }
}