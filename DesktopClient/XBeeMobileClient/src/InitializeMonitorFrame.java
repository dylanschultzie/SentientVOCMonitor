import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/******************************************************************************
 * Author: Dylan Schultz
 * Date Created: 5/3/14
 * Date Modified: 5/20/14
 *
 *
 * void ZipField()
 * Purpose: Creates textfield on GUI and saves values
 * Entry: None
 * Exit: Saves textfield values
 *
 * void LatitudeField()
 * Purpose: Creates textfield on GUI and saves values
 * Entry: None
 * Exit: Saves textfield values
 *
 * void LongitudeField()
 * Purpose: Creates textfield on GUI and saves values
 * Entry: None
 * Exit: Saves textfield values
 *
 * void SensorField()
 * Purpose: Creates textfield on GUI and saves values
 * Entry: None
 * Exit: Saves textfield values
 *
 * void TimeField()
 * Purpose: Creates textfield on GUI and saves values
 * Entry: None
 * Exit: Saves textfield values
 *
 * void AcceptButton()
 * Purpose: Accept entered changes button
 * Entry: None
 * Exit: If entered, sanity checks all text fields
 *
 *
 * void CancelButton
 * Purpose: Cancel out of initialize screen
 * Entry: None
 * Exit: Exits initialize screen
 *
 * boolean CheckTime( String time )
 * Purpose: Checks to ensure the time entered is in the correct format
 * Entry: String time
 * Exit: whether the time is in the correct format or not
 *
 * boolean WithinBounds( double x )
 * Purpose: Checks to ensure the latitude/longitude are within the allotted bounds
 * Entry: double containing the lat/long
 * Exit: returns whether the value is correct or not
 *
 * *****************************************************************************/

 public class InitializeMonitorFrame extends JPanel implements ActionListener
{
    protected JTextField longitudeField;
    protected JTextField zipField;
    protected JTextField latitudeField;
    protected JTextField sensorField;
    protected JTextField timeField;

    private JButton cancel;
    private JButton accept;
    private String mTime;

    private final static String newline = "\n";

    private MobileClient mClient;
    private JFrame mFrame;

    InitializeMonitorFrame(MobileClient client, JFrame frame)
    {
        super(new GridBagLayout());
        mFrame = frame;
        mClient = client;

        SensorField();
        LongitudeField();
        LatitudeField();
        ZipField();
        TimeField();
        AcceptButton();
        CancelButton();

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;
        add(longitudeField, c);
        add(latitudeField, c);
        add(zipField, c);
        add(timeField, c);
        add(sensorField, c);
        add(accept, c);
        add(cancel, c);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        boolean initializeComplete = false;
        if("accept".equals(e.getActionCommand()))
        {
            double longitude = Double.parseDouble(longitudeField.getText());
            Double.parseDouble(latitudeField.getText());
            double latitude = Double.parseDouble(latitudeField.getText());

            try
            {
                if( WithinBounds(longitude) && WithinBounds(latitude) && CheckTime(timeField.getText()) )
                {
                    String init = latitudeField.getText() + ":" + longitudeField.getText() + ":" +
                                  zipField.getText() + ":" + sensorField.getText() + ":";
                    initializeComplete = mClient.Initialize( init, mTime);

                    JFrame frame = new JFrame("Initialization");

                    if( initializeComplete  )
                        JOptionPane.showMessageDialog(frame, "Initialization successful!");
                    else
                        JOptionPane.showMessageDialog(frame, "Initialization failed");

                    mFrame.dispose();
                }
            }catch(Exception xcpt){}
        }
        else if("cancel".equals(e.getActionCommand()))
        {
            mFrame.dispose();
        }
    }
    void ZipField()
    {
        zipField = new JTextField(20);

        zipField.setActionCommand("zip");
        zipField.addActionListener(this);
        //zipField.setText("Enter zip code ex. 97601");
        zipField.setText("97601");
    }
    void LatitudeField()
    {
        latitudeField = new JTextField(20);

        latitudeField.setActionCommand("latitude");
        latitudeField.addActionListener(this);
        //latitudeField.setText("Enter monitor latitude ex. 120.44576");
        latitudeField.setText("42.256081");
    }
    void LongitudeField()
    {
        longitudeField = new JTextField(20);

        longitudeField.setActionCommand("longitude");
        longitudeField.addActionListener(this);
        //longitudeField.setText("Enter monitor longitude ex. 120.44576");
        longitudeField.setText("-121.788205");
    }
    void SensorField()
    {
        sensorField = new JTextField(20);

        sensorField.setActionCommand("sensor");
        sensorField.addActionListener(this);
        //sensorField.setText("Enter sensor type ex. CO");
        sensorField.setText("Carbon Monoxide");
    }
    void TimeField()
    {
        timeField = new JTextField(20);

        timeField.setActionCommand("time");
        timeField.addActionListener(this);
        //timeField.setText("Enter time/date in format: min/hh/dd/mm/yy");
        timeField.setText("12/12/12/12/14");
    }
    void AcceptButton()
    {
        accept = new JButton();
        accept.setText("Accept");
        accept.setActionCommand("accept");
        accept.addActionListener(this);
    }
    void CancelButton()
    {
        cancel = new JButton();
        cancel.setText("Cancel");
        cancel.setActionCommand("cancel");
        cancel.addActionListener(this);
    }
    boolean WithinBounds( double x )
    {
        boolean check = false;
        if( x < 999.999999 && x > -999.999999 )
            check = true;
        else
        {
            JFrame frame = new JFrame("Incorrect Entry");
            JOptionPane.showMessageDialog(frame, "Please make sure latitude/longitude is in the xxx.xxxxxx format");
        }
        return check;
    }
    boolean CheckTime( String time )
    {
        boolean y = true;
        String[] parts;
        parts = time.split("/");
        JFrame frame = new JFrame("Incorrect entry");
        int min = Integer.parseInt(parts[0]);
        int hour = Integer.parseInt(parts[1]);
        int day  = Integer.parseInt(parts[2]);
        int month = Integer.parseInt(parts[3]);
        mTime = parts[2] + "/" + parts[3] + "/" + parts[4] + "," + parts[1];

        if( min < 0 || min > 60 )
        {
            JOptionPane.showMessageDialog(frame, "Minutes must be within 0 and 59");
            y = false;
        }
        else if( hour > 12 || hour < 0 )
        {
            JOptionPane.showMessageDialog(frame, "Hours must be between 0 and 12");
            y = false;
        }
        else if( day > 31 || day < 0 )
        {
            JOptionPane.showMessageDialog(frame, "Days must be between 0 and 31");
            y = false;
        }
        else if( month > 12 || month < 0 )
        {
            JOptionPane.showMessageDialog(frame, "Months must be between 0 and 12");
            y = false;
        }
        return y;
    }
}
