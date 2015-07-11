import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by Kurteisliga on 4/18/14.
 */
public class PCVolatileTextFileTest
{
    PCVolatileTextFile pcv = new PCVolatileTextFile();

    @Test
    public void testReadSerialPort() throws Exception
    {
        assertTrue( pcv.ReadSerialPort().equals( "COM4") );
    }

    @Test
    public void testWriteToFile() throws Exception
    {
        int[] packet = new int[5];

        File f = new File("C://java//Test");
        f.delete();

        for(int i = 0; i < 5; i++ )
            packet[i] = i+65;
        pcv.setDirectory("C://java//Test");
        pcv.setSerial("456");
        pcv.setPacketData(packet);
        pcv.WriteToFile();

        assertTrue( f.exists() );
    }
}
