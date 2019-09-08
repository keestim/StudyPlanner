package activites;

import javax.swing.*;
import java.awt.*;

public class GuiMain
{
    private static JFrame frame;
    private static JPanel panel;
    private static data.DBActions db_actions;
    private static int user_id;

    public GuiMain (JFrame input_frame, JPanel input_panel, data.DBActions input_db_actions)
    {
        frame = input_frame;
        panel = input_panel;
        db_actions = input_db_actions;
    }

    public static void main(String args[])
    {
        db_actions = new data.DBActions();
        db_actions.DBExists();

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                GUIFrame frame = new GUIFrame("Program", db_actions);
                frame.setVisible(true);
            }
        });

        /*
        // Create and set up a frame window
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Layout");
        frame.setPreferredSize(new Dimension(1280, 720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define the panel to hold the components
        panel = new JPanel();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        HomeActivity sign_in = new HomeActivity(new GuiMain(frame, panel, db_actions));
        sign_in.displayScreen(new GuiMain(frame, panel, db_actions));
         */
    }
}