import com.rapplogic.xbee.api.XBeeException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***********************************************************************************
 * Author: Dylan Schultz
 * Date Created: 2/18/14
 * Date Modified: 5/31/14
 *
 * Purpose: Purpose of this class is to simply act as a driver for the
 *          PCXBeeConnectionSeries1 and VOCTextFile classes. It simply opens the connection
 *          for USB/serial communication, "ARP"s the remote arduino (which leads to
 *          the chain of events of downloading raw data) and begins receiving
 *          the packets from the arduino. The program ends when the arduino sends
 *          a disconnect packet. This packet is the final packet to be received and
 *          sent.
 *
 * Functions:
 *      actionPerformed
 *          Purpose: handles events pertaining to the frames and buttons
 *          Entry: an event must have been established through user interaction
 *          Exit: Event is handled, and user is directed of what happened
 *
 *      ConnectFrame
 *          Purpose: Initial frame that has connect and initialize buttons
 *          Entry: None
 *          Exit: Program will either enter initialize or downloaddisconnect frames
 *
 *      ConnectButton
 *          Purpose: Creates the connect button
 *          Entry: none
 *          Exit: Button is created
 *
 *      DownloadButton
 *          Purpose: Creates the download button
 *          Entry: none
 *          Exit: Button is created
 *
 *      DisconnectButton
 *          Purpose: Creates the disconnect button
 *          Entry: none
 *          Exit: Button is created
 *
 *      InitializeButton
 *          Purpose: Creates the initialize button
 *          Entry: none
 *          Exit: Button is created
 *
 *      ShutdownButton
 *          Purpose: Creates the shutdown button
 *          Entry: none
 *          Exit: Button is created
 *
 *      DownloadDisconnectFrame
 *          Purpose: Creates the download/disconnect/shutdown frame as well as
 *                   calls the functions to create said buttons
 *          Entry: can only enter this window when a connection is established with
 *                 monitor
 *          Exit: when the frame is closed, the app is closed as well.
 *
 **********************************************************************************/
public class MobileClientUI extends JPanel implements ActionListener
{
    //JButton connect, disconnect, download;
    //JButton download;
    private static MobileClient mClient;
    private VOCButton connect = new VOCConnect();
    private VOCButton disconnect = new VOCDisconnect();
    private VOCButton download = new VOCDownload();
    private VOCButton shutdown = new VOCShutDown();
    private VOCButton initialize = new VOCInitialize();

    private static JFrame connectInit;
    private static JFrame disconnectShutdown;

    //Initiates UI behavior
    public static void main(String[] args) throws Exception
    {
        mClient = new MobileClient();
        javax.swing.SwingUtilities.invokeLater(new Runnable()   //Invoking a new thread, global variables at risk
        {
            public void run()
            {
               ConnectFrame();
            }
        });
    }
    //Directs button-dependent behavior
    public void actionPerformed(ActionEvent e)
    {
        if ("connect".equals(e.getActionCommand()))
        {
            boolean connectionEstablished = false;
            connectionEstablished = connect.ButtonPress(mClient);
            if( connectionEstablished )
            {
                DownloadDisconnectFrame(); //Upon successfully connecting, provide VOC entry download UI
                connectInit.dispose();
            }
        }
        else if("disconnect".equals(e.getActionCommand()))
        {
            boolean connectionSevered = false;
            connectionSevered = disconnect.ButtonPress(mClient);
           if( connectionSevered )
               disconnectShutdown.dispose(); //Discontinue UI
        }
        else if("download".equals(e.getActionCommand()))
        {
            download.ButtonPress(mClient);
           // ClickDownload();
        }
        else if("shutdown".equals(e.getActionCommand()))
        {
            shutdown.ButtonPress(mClient);
        }
        else if("initialize".equals(e.getActionCommand()))
        {
            initialize.ButtonPress(mClient);
        }
    }
    //Details the window for connecting and initialising
    private static void ConnectFrame()
    {
        //Create and set up the window.
        connectInit = new JFrame("VOC Monitoring System Connect");
        connectInit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Describing the window
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(300, 500));
        connectInit.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        //Include the following buttons and panes
        MobileClientUI newContentPane = new MobileClientUI();
        newContentPane.ConnectButton();
        newContentPane.InitializeButton();
        newContentPane.setOpaque(true); //content panes must be opaque
        connectInit.setContentPane(newContentPane);

        //Display the window.
        connectInit.pack();
        connectInit.setVisible(true);
    }
    //Details the connect button
    private void ConnectButton()
    {
        connect.getButton().setActionCommand("connect");
        connect.getButton().addActionListener(this);
        add(connect.getButton());
    }
    //Details the download button
    private void DownloadButton()
    {
        download.getButton().setActionCommand("download");
        download.getButton().addActionListener(this);
        add(download.getButton());
    }
    //Detail the connect button
    private void DisconnectButton()
    {
        disconnect.getButton().setActionCommand("disconnect");
        disconnect.getButton().addActionListener(this);
        add(disconnect.getButton());
    }
    //Detail the shut down button
    private void ShutdownButton()
    {
        shutdown.getButton().setActionCommand("shutdown");
        shutdown.getButton().addActionListener(this);
        add(shutdown.getButton());
    }
    //Details the initialize button
    private void InitializeButton()
    {
        initialize.getButton().setActionCommand("initialize");
        initialize.getButton().addActionListener(this);
        add(initialize.getButton());
    }
    //Details the download disconnect button
    private void DownloadDisconnectFrame()
    {
        //Create window specifications
        disconnectShutdown = new JFrame("DownloadDisconnect");
        disconnectShutdown.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(300, 500));
        disconnectShutdown.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        //Add the following buttons and panes to window
        MobileClientUI newContentPane = new MobileClientUI();
        newContentPane.DownloadButton();
        newContentPane.DisconnectButton();
        newContentPane.ShutdownButton();
        newContentPane.setOpaque(true); //content panes must be opaque
        disconnectShutdown.setContentPane(newContentPane);

        disconnectShutdown.pack();
        disconnectShutdown.setVisible(true);
    }
}
