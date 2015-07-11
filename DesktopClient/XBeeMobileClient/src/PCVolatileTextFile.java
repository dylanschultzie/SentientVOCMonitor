/******************************************************************************
 * Author: Dylan Schultz
 * Date Created: 2/24/14
 * Date Modified: 3/9/14
 *
 * PCVolatileTextFile( XBeeAddress64 addr, int[] packet )
 * Purpose: constructor. takes in an address and packet to be written to file.
 *          the address is the identifier of the arduino, and the packet is
 *          the data within the packet.
 * Entry: takes in an address and packet to be written to file.
 *        the address is the identifier of the arduino, and the packet is
 *        the data within the packet.
 * Exit: PCVolatileTextFile object is created
 *
 * public void WriteToFile()
 * Purpose: creates a new file and writes to said file.
 * Entry: None
 * Exit: File written to
 *
 * private boolean isStreamClosed(FileOutputStream out)
 * Purpose: self-defined function that accomplishes the c++ function "isOpen()"
 * Entry: the output stream to be checked if open or not
 * Exit: returns whether the file is open or not.
 *
 * void WriteInitialize()
 * Purpose: writes the initialize data to a file to be uploaded to the database
 * Entry: None
 * Exit: initialize data written to file
 *
 * String ReadSerialPort()
 * Purpose: reads in from config file the COM port the XBee is connected to.
 * Entry: None
 * Exit: com string pulled from file and returned to calling function.
 *
 * *****************************************************************************/
import com.rapplogic.xbee.api.XBeeAddress;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeAddress64;

import java.io.*;
import java.nio.channels.FileChannel;

public class PCVolatileTextFile
{
    private String serial;
    private int[] packetData;
    private String directory;

    PCVolatileTextFile() {}

    PCVolatileTextFile( XBeeAddress64 addr, int[] packet )
    {
        this.serial = addr.toString();
        this.packetData = packet;
        directory = "C://java//VOCLevels.txt";
    }
    PCVolatileTextFile( XBeeAddress addr, int[] packet )
    {
        this.serial = addr.toString();
        this.packetData = packet;
        directory = "C://java//VOCLevels.txt";
    }
    public void WriteToFile() throws Exception
    {
        File createFile = new File(directory);
        FileOutputStream oStream = new FileOutputStream(createFile, true);

        if( isStreamClosed(oStream) )
        {
            oStream.write(serial.getBytes());
            oStream.write(',');
            oStream.write('-');
            for( int i = 0; i < packetData.length; i++)
                oStream.write(packetData[i]);
            oStream.write(';');

            oStream.close();
        }
    }
    public void WriteInitialize( XBeeAddress xb, String data ) throws IOException
    {
        File createFile = new File("C://java//Initialize.txt");
        FileOutputStream oStream = new FileOutputStream(createFile);

        if( isStreamClosed(oStream) )
        {
            for( int i = 0; i < data.length(); i++)
                oStream.write(data.charAt(i));
            oStream.write(xb.toString().getBytes());
            oStream.write(';');

            oStream.close();
        }
    }
    public String ReadSerialPort() throws Exception
    {
        BufferedReader reader = new BufferedReader(new FileReader("C://java//config.txt"));
        String line = null;
        String serial = null;
        String[] parts = null;
        while ((line = reader.readLine()) != null)
        {
            parts = line.split("=");
            serial = parts[1];
        }
        return serial;
    }
    private boolean isStreamClosed(FileOutputStream out)
    {
        try
        {
            FileChannel fc = out.getChannel();
            return fc.position() >= 0L; // This may throw a ClosedChannelException.
        }
        catch (java.nio.channels.ClosedChannelException cce)
        {
            return false;
        }
        catch (IOException e)
        {
        }
        return true;
    }

    public String getSerial()
    {
        return serial;
    }
    public void setSerial(String serial)
    {
        this.serial = serial;
    }
    public void setPacketData( int[] packetData ) { this.packetData = packetData; }
    public void setDirectory( String dir ) { this.directory = dir; }
}
