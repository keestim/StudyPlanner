package activites;

import GUIComponents.GUIFrame;
import data.DBActions;

import javax.swing.*;
import java.awt.*;

public class Activity extends Canvas
{
    private static JPanel panel;
    private static DBActions db_access;
    private static GUIFrame frame;
    private static Object[] args;


    //have default constructor pass in an array of arguments!
    public Activity(GUIFrame input_frame)
    {
        super();
        panel = input_frame.getPanel();
        db_access = input_frame.getDb_access();
        frame = input_frame;
        setPreferredSize( new Dimension( 1280, 720 ) );
    }

    public Activity(GUIFrame input_frame, Object[] args)
    {
        super();
        panel = input_frame.getPanel();
        db_access = input_frame.getDb_access();
        frame = input_frame;
        this.args = args;
        setPreferredSize( new Dimension( 1280, 720 ) );
    }

    public void paint( Graphics g )
    {
        update( g ); // repaint canvas
    }

    // repaint the canvas
    public void update( Graphics g )
    {

    }

    public void displayForm()
    {

    }

    public void displayForm(GridBagConstraints constraint)
    {

    }

    public void checkInputs()
    {

    }

    public static Object[] getArgs() {
        return args;
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static DBActions getDb_access() {
        return db_access;
    }

    public static GUIFrame getFrame() {
        return frame;
    }
}
