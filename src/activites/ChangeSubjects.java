package activites;

import data.DBActions;

import javax.swing.*;
import java.awt.*;

public class ChangeSubjects extends Activity
{
    private static JPanel panel;
    private static DBActions db_access;
    private static GUIFrame frame;

    public ChangeSubjects(GUIFrame input_frame)
    {
        super(null);
        panel = input_frame.getPanel();
        db_access = input_frame.getDb_access();
        frame = input_frame;
    }

    public void paint( Graphics g )
    {
        update( g ); // repaint canvas
    }

    // repaint the canvas
    public void update( Graphics g )
    {

    }

    @Override
    public void displayForm()
    {


    }

    public void checkInputs()
    {

    }
}
