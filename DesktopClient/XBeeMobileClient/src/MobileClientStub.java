import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;


/******************************************************************************
 * Author: Dylan Schultz
 * Date Created: 5/3/14
 * Date Modified: 5/31/14
 *
 * MobileClientStub()
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
public class MobileClientStub implements iMobileClient
{
    PCXBeeConnectionStubSeries2 pcx;

    public MobileClientStub() throws XBeeException
    {
        pcx = new PCXBeeConnectionStubSeries2("COM4");
    }

    @Override
    public boolean Disconnect() throws Exception
    {
        int[] data = new int[1];
        boolean successfulDisconnect = false;
        data[0] = 'D';
        ZNetRxResponse zrx = new ZNetRxResponse();
        zrx.setData(data);
        zrx.setApiId(ApiId.ZNET_RX_RESPONSE);

        if( pcx.SendDisconnect(zrx) == 1)
            successfulDisconnect = true;
        return successfulDisconnect;
    }

    @Override
    public boolean Download() throws Exception
    {
        int[] data = new int[1];
        data[0] = 'T';
        ZNetRxResponse zrx = new ZNetRxResponse();
        zrx.setData(data);
        zrx.setApiId(ApiId.ZNET_RX_RESPONSE);

        return pcx.SendDownload(zrx);
    }

    @Override
    public boolean Connect() throws XBeeException
    {
        int[] data = new int[1];
        data[0] = 1;
        ZNetRxResponse zrx = new ZNetRxResponse();
        zrx.setData(data);
        zrx.setApiId(ApiId.ZNET_RX_RESPONSE);

        return pcx.PingArduino(zrx);
    }

    @Override
    public boolean Shutdown() throws Exception
    {
        return false;
    }

    @Override
    public boolean Initialize(String initString, String time) throws Exception {
        return false;
    }
}
