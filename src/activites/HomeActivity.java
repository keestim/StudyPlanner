package activites;

import data.DBActions;
import data.TimetableEntry;

import javax.swing.*;
import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomeActivity extends Activity
{
    private static JPanel panel;
    private static DBActions db_access;
    private static GUIFrame frame;

    private Object[] edit_day_buttons;
    private Button change_subjects;

    private ArrayList<TimetableEntry> entries;

    public HomeActivity(GUIFrame input_frame)
    {
        super(null);
        panel = input_frame.getPanel();
        db_access = input_frame.getDb_access();
        frame = input_frame;

        entries = db_access.getTimetableEntries(frame.getUserID());
    }

    public void paint( Graphics g )
    {
        update( g ); // repaint canvas
    }

    // repaint the canvas
    public void update( Graphics g )
    {
        // 1. clear background
        g.setColor( Color.WHITE );
        g.fillRect( 0, 0, getWidth(), getHeight() );

        g.setColor(Color.BLACK);
        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        for (int i = 0; i < days.length; i++)
        {
            g.fillRect((i * 180) + 20,50,150, 600);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < entries.size(); i++)
        {
            String start = entries.get(i).getStart_time();
            float start_fraction = timeFraction(start) * 25;

            String end = entries.get(i).getEnd_time();
            float end_fraction = timeFraction(end) * 25;

            System.out.println(start_fraction + "|" + end_fraction);

            g.fillRect((entries.get(i).getDay() - 1) * 180 + 20, Math.round(start_fraction) + 50, 150, Math.round(end_fraction - start_fraction));
        }
    }

    public float timeFraction(String time)
    {
        int start_hour = Integer.valueOf(time.split(":")[0]);
        int start_minutes = Integer.valueOf(time.split(":")[1]);
        return start_hour + start_minutes/60;
    }

    @Override
    public void displayForm()
    {
        frame.setLayout(new GridBagLayout());
        GridBagConstraints jConstraints = new GridBagConstraints();

        jConstraints.fill = GridBagConstraints.HORIZONTAL;
        jConstraints.gridy = 2;

        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        edit_day_buttons = new Object[days.length];
        for (int i = 0; i < days.length; i++)
        {
            Button btn = new Button(days[i]);
            panel.add(btn, jConstraints);
            edit_day_buttons[i] = btn;
        }

        change_subjects = new Button("Change Subjects");
        panel.add(change_subjects, jConstraints);
    }

    public void checkInputs()
    {

        for (int i = 0; i < edit_day_buttons.length; i++)
        {
            final int index = i;
            ((Button) edit_day_buttons[i]).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(index);
                }
            });
        }
    }
}
