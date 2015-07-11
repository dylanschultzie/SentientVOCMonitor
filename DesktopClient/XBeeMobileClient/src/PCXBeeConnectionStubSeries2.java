import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.util.ByteUtils;

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
public class PCXBeeConnectionStubSeries2 implements iPCXBeeConnection
{
    private XBee xbee = new XBee();
    private XBeeAddress64 myAddr64 = new XBeeAddress64();
    private XBeeAddress64 theirAddr64 = new XBeeAddress64();
    private XBeeResponse response;
    private ZNetRxResponse zReceive;
    private PCVolatileTextFile outFile;
    private int[] downloadSize;

    PCXBeeConnectionStubSeries2(String com) throws XBeeException
    {
        zReceive = new ZNetRxResponse();
        int[] packet = new int[5];
        for( int i = 0; i < 5; i++ )
            packet[i]=i;
        try
        {
            xbee.open(com, 9600);
        }
        catch( XBeeException x )
        {

        }
        zReceive.setData(packet);
        zReceive.setRemoteAddress64(myAddr64);
    }
    public int ParsePacket()
    {
        int write = 0;
        int [] packet = zReceive.getData();
        if( packet[0] == 'D' )           //packet[0] will be 'D' if it's a disconnect packet. 'D' in ascii is 68
        {
            write = 1;
            System.out.println("Connection Severed");
        }
        else if( packet[0] == 'T' )
        {
            write = 2;
        }
        else if( packet[0] == 'C' )
        {
            write = 3;
        }
        else
        {
            if( outFile == null )
                outFile = new PCVolatileTextFile( zReceive.getRemoteAddress64(), zReceive.getData() );
            else
                outFile.setPacketData(zReceive.getData());
            WriteToFile();
            System.out.println("Wrote to file");
        }
        return write;
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

    @Override
    public boolean PingArduino() throws XBeeException
    {
        return false;
    }

    @Override
    public boolean SendDownload() throws Exception {
        return false;
    }

    @Override
    public int SendDisconnect() throws Exception {
        return 0;
    }

    private void SetDownloadSize()
    {
        downloadSize = zReceive.getData();
    }

    public boolean PingArduino( ZNetRxResponse zRx) throws XBeeException
    {
        boolean connection = false;
        if( zRx.getApiId() == ApiId.ZNET_RX_RESPONSE)
        {
            zReceive = zRx;
            theirAddr64 = zReceive.getRemoteAddress64();           //sets remote address much like an ARP
            SetDownloadSize();                                     //sets how many packets should arrive
            System.out.println("Connection Established");
            connection = true;
        }
        return connection;
    }
    public boolean SendDownload(ZNetRxResponse zRx) throws Exception
    {
        boolean download = false;

        if( zRx.getApiId() == ApiId.ZNET_RX_RESPONSE)
        {
            zReceive = zRx;
            download = true;
            ReceivePacket();
        }
        return download;
    }
    public int SendDisconnect(ZNetRxResponse zRx) throws Exception
    {
        int disconnect = 0;

        zReceive = zRx;
        disconnect = ReceivePacket();
        return disconnect;
    }
    public int ReceivePacket() throws Exception
    {
        int responseCaptured = 0;
        int downloadSizeCheck = 0;

        downloadSizeCheck++;
        responseCaptured = ParsePacket();                   //parse packet. if responseCaptured is false, connection is severed.

        if( downloadSizeCheck != (downloadSize[0] + 1))
        {
            //System.out.println("Packets lost");                 //compares how many packets were received to how many were to be sent.
            System.out.println( downloadSize[0]);
            System.out.println(downloadSizeCheck);
        }
        return responseCaptured;
    }

    @Override
    public int SendShutdown() throws Exception
    {
        return 0;
    }

    @Override
    public int SendInitialize(String initString, String time) throws Exception {
        return 0;
    }


    public void SetData( int[] packet )
    {
        zReceive.setData(packet);
    }
    public void SetDownloadSize( int[] x )
    {
        this.downloadSize = x;
    }
}
