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
public class VOCDownload implements VOCButton
{
    private JButton download;

    VOCDownload()
    {
        download = new JButton();
        download.setText("Download");
    }
    @Override
    public boolean ButtonPress(MobileClient mClient)
    {
        boolean downloadComplete = false;
        try
        {
            downloadComplete = mClient.Download();
        } catch (Exception e1) {e1.printStackTrace();}

        if( downloadComplete )
            SuccessDialog();
        else
            FailureDialog();

        return downloadComplete;
    }

    @Override
    public JButton getButton()
    {
        return download;
    }

    @Override
    public void SuccessDialog()
    {
        JFrame frame = new JFrame("Download Complete");
        JOptionPane.showMessageDialog(frame, "Downloading of VOC data complete.");
    }

    @Override
    public void FailureDialog()
    {
        JFrame frame = new JFrame("Download Incomplete");
        JOptionPane.showMessageDialog(frame, "Downloading of VOC data did not succeed.");
    }
}
