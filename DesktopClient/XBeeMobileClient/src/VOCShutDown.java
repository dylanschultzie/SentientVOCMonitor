import javax.swing.*;

/******************************************************************************
 * Author: Dylan Schultz
 * Date Created: 5/3/14
 * Date Modified: 5/20/14
 *
 *
 * boolean ButtonPress( MobileClient mClient )
 * Purpose: Establishes the button press event
 * Entry: None
 * Exit: event (connect, disconnect, etc.) completed.
 *
 * void SuccessDialog()
 * Purpose: displays a success dialog box
 * Entry: None
 * Exit: dialog box displayed
 *
 * void FailureDialog()
 * Purpose: displays a dialog box if the button press event was not successful.
 * Entry: None
 * Exit: dialog box displayed to screen.
 *
 * *****************************************************************************/
public class VOCShutDown implements VOCButton
{
    private JButton shutdown;
    VOCShutDown()
    {
        shutdown = new JButton();
        shutdown.setText("Shut Down");
    }
    @Override
    public boolean ButtonPress(MobileClient mClient)
    {
        boolean shutdownComplete = false;
        try
        {
            shutdownComplete = mClient.Shutdown();
        } catch (Exception e1) {e1.printStackTrace();}

        if( shutdownComplete )
            SuccessDialog();
        else
            FailureDialog();

        return shutdownComplete;
    }

    @Override
    public JButton getButton()
    {
        return shutdown;
    }

    @Override
    public void SuccessDialog()
    {
        JFrame frame = new JFrame("Shutdown Succeed");
        JOptionPane.showMessageDialog(frame, "Shutdown was successful.");
    }

    @Override
    public void FailureDialog()
    {
        JFrame frame = new JFrame("Shutdown Failed");
        JOptionPane.showMessageDialog(frame, "Shutdown was not successful.");
    }
}
