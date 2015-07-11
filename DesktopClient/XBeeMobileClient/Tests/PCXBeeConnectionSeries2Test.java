import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Created by Kurteisliga on 4/20/14.
 */
public class PCXBeeConnectionSeries2Test
{
    PCXBeeConnectionStubSeries2 pcx;

    @Before
    public void setUp() throws Exception
    {
        pcx = new PCXBeeConnectionStubSeries2("COM4");
    }

    @Test
    public void testReceiveConnect() throws Exception
    {
        int[] intArray = new int[1];
        intArray[0] = 'C';

        pcx.SetData(intArray);
        assertEquals( 3, pcx.ParsePacket());
    }

    @Test
    public void testReceiveDownload() throws Exception
    {
        int[] intArray = new int[1];
        intArray[0] = 'T';

        pcx.SetData(intArray);
        assertEquals( 2, pcx.ParsePacket());
    }

    @Test
    public void testReceiveDisconnect() throws Exception
    {
        int[] intArray = new int[1];
        intArray[0] = 'D';

        pcx.SetData(intArray);
        assertEquals( 1, pcx.ParsePacket());
    }

    @Test
    public void testReceivePacket() throws Exception
    {
        int[] numberOfPackets = new int[1];
        numberOfPackets[0] = 0;

        pcx.SetData( numberOfPackets );
        pcx.SetDownloadSize( numberOfPackets );
        assertEquals( 0, pcx.ReceivePacket() );
    }

    @Test
    public void testWriteReceivedPacket() throws Exception
    {
        int[] intArray = new int[1];
        intArray[0] = 'W';
        pcx.SetData(intArray);
        pcx.ParsePacket();

        File f = new File("C://java//VOCLevels");
        assertTrue( f.exists() );
    }
}
