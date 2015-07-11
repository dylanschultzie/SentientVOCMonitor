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
 * void InitializeFrame( MobileClient mClient)
 * Purpose: Creates the initialize frame
 * Entry: Passed MobileClient in order to create monitorframe
 * Exit: None
 *
 * *****************************************************************************/
public class VOCInitialize extends JPanel implements VOCButton, ActionListener
{
    protected JTextField textField;
    protected JTextArea textArea;
    private JButton initialize;
    private final static String newline = "\n";

    VOCInitialize()
    {
        initialize = new JButton();
        initialize.setText("Initialize");
    }

    public void actionPerformed(ActionEvent e)
    {
        if ("input".equals(e.getActionCommand()))
        {
            String text = textField.getText();
            textArea.append(text + newline);
            textField.selectAll();

            //Make sure the new text is visible, even if there
            //was a selection in the text area.
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    @Override
    public boolean ButtonPress(MobileClient mClient)
    {
        boolean initializeComplete = false;
        try
        {
            InitializeFrame(mClient);
        } catch (Exception e1) {e1.printStackTrace();}

        return initializeComplete;
    }

    @Override
    public JButton getButton()
    {
        return initialize;
    }

    @Override
    public void SuccessDialog()
    {
        JFrame frame = new JFrame("Initialize Succeed");
        JOptionPane.showMessageDialog(frame, "Initialization was successful.");
    }

    @Override
    public void FailureDialog()
    {
        JFrame frame = new JFrame("Initialize Failed");
        JOptionPane.showMessageDialog(frame, "Initialization was not successful.");
    }
    public void InitializeFrame( MobileClient client)
    {
        JFrame frame = new JFrame("Initialization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(300, 500));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        frame.add(new InitializeMonitorFrame(client, frame));

        frame.pack();
        frame.setVisible(true);
    }
}
