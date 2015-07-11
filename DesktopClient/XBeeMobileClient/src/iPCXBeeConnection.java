import com.rapplogic.xbee.api.XBeeException;

/******************************************************************************
 * Author: Dylan Schultz
 * Date Created: 5/3/14
 * Date Modified: 5/20/14
 *
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
public interface iPCXBeeConnection
{
    int ParsePacket();
    void WriteToFile();
    boolean PingArduino() throws XBeeException;
    boolean SendDownload() throws Exception;
    int SendDisconnect() throws Exception;
    int ReceivePacket() throws Exception;
    int SendShutdown() throws Exception;
    int SendInitialize( String initString, String time ) throws Exception;
}
