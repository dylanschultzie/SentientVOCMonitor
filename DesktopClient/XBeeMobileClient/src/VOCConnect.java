import com.rapplogic.xbee.api.XBeeException;

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
public class VOCConnect implements VOCButton
{
    private JButton connect;

    VOCConnect()
    {
        connect = new JButton();
        connect.setText("Connect");
    }

    @Override
    public JButton getButton()
    {
        return connect;
    }

    @Override
    public boolean ButtonPress( MobileClient mClient )
    {
        boolean connectionEstablished = true;
        try
        {
            connectionEstablished = mClient.Connect();
        } catch (XBeeException e1) {e1.printStackTrace();}

        if( !connectionEstablished )
            FailureDialog();

        return connectionEstablished;
    }

    @Override
    public void SuccessDialog()
    {

    }

    @Override
    public void FailureDialog()
    {
        JFrame frame = new JFrame("Connection Failed");
        JOptionPane.showMessageDialog(frame, "Connection was not established with remote monitor.");
    }
}
