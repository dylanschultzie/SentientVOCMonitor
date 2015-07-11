import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kurteisliga on 4/17/14.
 */
public class MobileClientTestSucceed
{
    private MobileClientStub mC;

    @Before
    public void setUp() throws Exception
    {
        mC = new MobileClientStub();
    }

    @Test
    public void testConnectToRemoteMonitor() throws Exception
    {
        boolean connected = false;
        connected = mC.Connect();
        assertTrue(connected);
    }

    @Test
    public void testDownloadFromRemoteMonitor() throws Exception
    {
        boolean downloaded = false;
        mC.Connect();
        downloaded = mC.Download();
        assertTrue(downloaded);
    }

    @Test
    public void testDisconnectFromRemoteMonitor() throws Exception
    {
        boolean disconnected = false;
        mC.Connect();
        disconnected = mC.Disconnect();
        assertTrue(disconnected);
    }
}
