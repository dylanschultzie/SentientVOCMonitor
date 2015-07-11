import com.rapplogic.xbee.api.XBeeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Thyanna on 4/22/2014.
 * Tests the logic behind connecting the to Arduino
 */

//Tests the connection behavior of the arduino
public class ArduinoConnectionTest
{
    private PCXBeeConnectionSeries2 connect;

    @Before
    public void setUp() throws Exception
    {
        connect = new PCXBeeConnectionSeries2("COM4");
    }

    //Tests if a connection can be sent
    @Test
    public void testArduinoConnectionPacketSentAndResponseRecieved() throws XBeeException
    {
        boolean test = false;
        test = connect.PingArduino();
        assertTrue(test);
    }

    //Tests if the download size returned is correct
    @Test
    public void testArduinoDownloadPacketCorrectValue() throws XBeeException
    {
        boolean test = false;
        //connect.PingArduino();
        int [] size = connect.GetDownloadSize();

        if( size[0] == 5 )
        {
            test = true;
        }

        assertFalse( test );
    }

}
