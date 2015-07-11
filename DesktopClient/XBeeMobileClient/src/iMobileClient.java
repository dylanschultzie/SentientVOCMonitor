import com.rapplogic.xbee.api.XBeeException;

/******************************************************************************
 * Author: Dylan Schultz
 * Date Created: 5/3/14
 * Date Modified: 5/20/14
 *
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
public interface iMobileClient
{
    boolean Disconnect() throws Exception;
    boolean Download() throws Exception;
    boolean Connect() throws XBeeException;
    boolean Shutdown() throws Exception;
    boolean Initialize( String initString, String time ) throws Exception;
}
