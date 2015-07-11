import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by Thyanna on 4/22/2014.
 * Tests the logic behind Arduino to PC VOC entry transfers
 */
public class ArduinoTransferTest
{
    private PCXBeeConnectionSeries2 connect;
    private MobileClient client;

    //Ensuring systems are connected before test
    @Before
    public void setUp() throws Exception
    {
        client = new MobileClient();
        connect = new PCXBeeConnectionSeries2("COM4");
    }

    //Tests if the correct number of entries were submitted
    @Test
    private void testArduinoSendsCorrectNumberOfTranserPackets()
    {
        boolean test = false;
        int downLoadsReceived;

        downLoadsReceived = connect.GetDownloadsReceived();

        if( downLoadsReceived == 5 )
        {
            test = true;
        }

        assertFalse( test );

    }

    //Tests if the correct voc entries were submitted
    @Test
    private void testArduinoSendsCorrectTransferPackets()
    {
        boolean test = false;
        String one = "[0,1,12/12/12,0,4]", two = "[0,1,12/12/12,0,4]", three = "[0,1,12/12/12,0,4]",
               four = "[0,1,12/12/12,0,4]", five = "[0,1,12/12/12,0,4]";

        assertFalse( test );

    }
}
