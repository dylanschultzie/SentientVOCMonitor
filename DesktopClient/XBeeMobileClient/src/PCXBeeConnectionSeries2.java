/******************************************************************************
 * Author: Dylan Schultz
 * Date Created: 2/18/14
 * Date Modified: 4/20/14
 *
 * PCXBeeConnectionSeries2( String com, String addr )
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
import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.util.ByteUtils;

public class PCXBeeConnectionSeries2 implements iPCXBeeConnection
{
    private XBee xbee = new XBee();
    private XBeeAddress64 myAddr64 = new XBeeAddress64();
    private XBeeAddress64 theirAddr64 = new XBeeAddress64();
    private XBeeResponse response;
    private ZNetRxResponse zReceive;
    private PCVolatileTextFile outFile;
    private int[] downloadSize;
    private int downloadsReceived;
    private int [][] downloadLevels;

    PCXBeeConnectionSeries2(String com) throws XBeeException
    {
        try
        {
            xbee.open(com, 9600);
        }
        catch( XBeeException x )
        {

        }
    }
    public int ParsePacket()
    {
        int write = 0;
        int [] packet = zReceive.getData();
        if( packet[0] == 'D' )           //packet[0] will be 'D' if it's a disconnect packet. 'D' in ascii is 68
        {
            write = 1;
        }
        else
        {
            if( outFile == null )
                outFile = new PCVolatileTextFile( zReceive.getRemoteAddress64(), zReceive.getData() );
            else
            {
                outFile.setPacketData(zReceive.getData());
                downloadLevels[downloadsReceived++] = zReceive.getData();
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
        ZNetTxRequest rq = new ZNetTxRequest(XBeeAddress64.BROADCAST, payload);
        rq.setOption(ZNetTxRequest.Option.BROADCAST);
        try
        {
            xbee.sendAsynchronous(rq);                                 //send packet
        }
        catch( XBeeException x){}
        try
        {
            rsp = xbee.getResponse(5000);                           //wait for a response
            if( rsp.getApiId() == ApiId.ZNET_TX_STATUS_RESPONSE )
                rsp = xbee.getResponse(5000);
        }
        catch( Exception x){ rsp = null; }

        if( rsp != null && rsp.getApiId() == ApiId.ZNET_RX_RESPONSE)
        {
            zReceive = (ZNetRxResponse)rsp;
            theirAddr64 = zReceive.getRemoteAddress64();           //sets remote address much like an ARP
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
        ZNetTxRequest rq = new ZNetTxRequest(theirAddr64, payload);
        xbee.sendAsynchronous(rq);                                 //send packet
        //xbee.sendAsynchronous(rq);
        if( ReceivePacket() == 2 )
            download = true;

        return download;
    }
    public int SendDisconnect() throws Exception
    {
        int disconnect = 0;
        // this is the SH + SL address of the remote radio -- broadcast looking for address
        int[] payload = ByteUtils.stringToIntArray("Disconnect");
        ZNetTxRequest rq = new ZNetTxRequest(theirAddr64, payload);
        xbee.sendAsynchronous(rq);                                 //send packet

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
            if( response != null && response.getApiId() == ApiId.ZNET_TX_STATUS_RESPONSE)
                response = xbee.getResponse(5000);
        }catch( Exception xcpt ){System.out.println("download XBee Timeout.");}

        if( response.getApiId() == ApiId.ZNET_RX_RESPONSE )
        {
            responseCaptured = 0;
            while( responseCaptured != 1 && (downloadSizeCheck != downloadSize[0] + 2) )
            {
                downloadSizeCheck++;
                zReceive = (ZNetRxResponse)response;                //convert response into a Rx response packet so it can be worked with
                responseCaptured = ParsePacket();                   //parse packet. if responseCaptured is false, connection is severed.
                response = xbee.getResponse(5000);                  //wait 5 seconds for next packet
            }

            if( downloadSizeCheck != (downloadSize[0] + 2))
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
        return 1;
    }

    @Override
    public int SendInitialize(String initString, String time) throws Exception {
        return 0;
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
    public void SetAddress(int b1, int b2, int b3, int b4, int b5, int b6, int b7, int b8)
    {
        myAddr64 = new XBeeAddress64( b1, b2, b3, b4, b5, b6, b7, b8 );
    }
    public void SetAddress(String addressStr)
    {
        myAddr64 = new XBeeAddress64(addressStr);
    }
    public void SetAddress(int[] address)
    {
        myAddr64 = new XBeeAddress64(address);
    }
}
