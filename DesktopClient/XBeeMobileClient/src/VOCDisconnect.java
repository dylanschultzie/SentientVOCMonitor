import com.rapplogic.xbee.api.XBeeException;

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
public class VOCDisconnect implements VOCButton
{
    private JButton disconnect;

    VOCDisconnect()
    {
        disconnect = new JButton();
        disconnect.setText("Disconnect");
    }
    @Override
    public boolean ButtonPress(MobileClient mClient)
    {
        boolean connectionSevered = false;

        try
        {
            if( mClient.Disconnect() )
                connectionSevered = true;
        } catch (Exception e) {e.printStackTrace();}
        SuccessDialog();

        return connectionSevered;
    }

    @Override
    public JButton getButton()
    {
        return disconnect;
    }

    @Override
    public void SuccessDialog()
    {
        JFrame frame = new JFrame("Disconnect Successful");
        JOptionPane.showMessageDialog(frame, "Disconnect from Monitor successful.");
    }

    @Override
    public void FailureDialog()
    {

    }
}
