import com.rapplogic.xbee.api.XBeeException;

/******************************************************************************
 * Author: Dylan Schultz
 * Date Created: 5/3/14
 * Date Modified: 5/20/14
 *
 * MobileClient()
 * Purpose: opens connection with XBee and begins the XBee connection process.
 * Entry: None
 * Exit: XBee serial port is connected.
 *
 * boolean Disconnect()
 * Purpose: begins the disconnect sequence of events with iPCXBeeConnection
 * Entry: None
 * Exit: dialog box will be displayed for whether it was successful or not.
 *
 * boolean Download()
 * Purpose: begins the download sequence of events with iPCXBeeConnection
 * Entry: None
 * Exit: dialog box will be displayed for whether it was successful or not.
 *
 * boolean Connect()
 * Purpose: begins the connect sequence of events with iPCXBeeConnection
 * Entry: None
 * Exit: dialog box will be displayed for whether it was successful or not.
 *
 * boolean Shutdown()
 * Purpose: begins the shutdown sequence of events with iPCXBeeConnection
 * Entry: None
 * Exit: dialog box will be displayed for whether it was successful or not.
 *
 * boolean Initialize()
 * Purpose: begins the initialize sequence of events with iPCXBeeConnection
 * Entry: None
 * Exit: dialog box will be displayed for whether it was successful or not.
 *
 *
 * *****************************************************************************/
public class MobileClient implements iMobileClient
{
    private iPCXBeeConnection xbeeConn;

    public MobileClient() throws Exception
    {
        PCVolatileTextFile comFile = new PCVolatileTextFile();
        xbeeConn = new PCXBeeConnectionSeries1( comFile.ReadSerialPort() );
    }

    @Override
    public boolean Connect() throws XBeeException
    {
        boolean connection;
        connection = xbeeConn.PingArduino();
        return connection;
    }

    @Override
    public boolean Download() throws Exception
    {
        boolean connection;
        connection = xbeeConn.SendDownload();
        return connection;
    }

    @Override
    public boolean Disconnect() throws Exception
    {
        boolean disconnectSucceed = false;
        if( xbeeConn.SendDisconnect() == 1 )
            disconnectSucceed = true;
        return disconnectSucceed;
    }
    @Override
    public boolean Shutdown() throws Exception
    {
        boolean shutdownSucceed = false;
        if( xbeeConn.SendShutdown() == 1 )
            shutdownSucceed = true;
        return shutdownSucceed;
    }

    @Override
    public boolean Initialize( String initString, String time ) throws Exception
    {
        boolean initializeSucceed = false;
        if( xbeeConn.SendInitialize(initString, time) == 1 )
            initializeSucceed = true;
        return initializeSucceed;
    }
}
