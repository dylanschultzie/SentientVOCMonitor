import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
interface VOCButton
{
    public boolean ButtonPress( MobileClient mClient );
    JButton getButton();
    void SuccessDialog();
    void FailureDialog();
}
