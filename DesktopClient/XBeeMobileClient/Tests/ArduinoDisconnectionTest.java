import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;

/**
 * Created by Thyanna on 4/22/2014.
 * Tests the logic behind disconnecting the systems
 */
public class ArduinoDisconnectionTest
{
    private PCXBeeConnectionSeries2 disconnect;

    //Ensuring systems are connected before test
    @Before
    public void setUp() throws Exception
    {
        disconnect = new PCXBeeConnectionSeries2("COM4");
        disconnect.PingArduino();
    }

    //Tests if Arduino sends a Disconnect confirmation packet
    @Test
    public void testArduinoDisconnectPacketSentAndRecieved() throws Exception
    {
        boolean test = false;
        int type;
        type = disconnect.SendDisconnect();
        if( type == 2 )
            test = true;
        assertFalse( test );
    }
}
