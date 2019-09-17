package GUIComponents;

import javax.swing.*;

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
                //create GUI frame in which elements are rendered to
                GUIFrame frame = new GUIFrame("Program", db_actions);
                frame.setVisible(true);
            }
        });
    }
}