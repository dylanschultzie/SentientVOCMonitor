import com.rapplogic.xbee.api.XBeeException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kurteisliga on 4/17/14.
 */
public class MobileClientTestFailed
{
    private PCXBeeConnectionSeries2 pcX;
    private MobileClient mC;

    @Before
    public void setUp() throws Exception
    {
        mC = new MobileClient();
        pcX = new PCXBeeConnectionSeries2("COM4");
    }

    @Test
    public void testConnectWithWrongComPort() throws Exception
    {
        pcX.OpenSerialConnection("COM3");
        boolean connected = false;
        connected = mC.Connect();
        assertFalse(connected);
    }

    @Test(expected = XBeeException.class)
    public void testDownloadWithoutFirstConnecting() throws Exception
    {
        boolean downloaded = false;
        downloaded = mC.Download();
        assertFalse(downloaded);
    }

}
