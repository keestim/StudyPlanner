package activites;

import GUIComponents.GUIFrame;
import data.TimetableEntry;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomeActivity extends Activity
{
    private Object[] edit_day_buttons;
    private Button change_subjects;
    private Button edit_timetable;

    private ArrayList<TimetableEntry> entries;

    //the main screen for the program, where the user timetable is displayed
    public HomeActivity(GUIFrame input_frame)
    {
        super(input_frame, true);
        entries = getDb_access().getTimetableEntries(getFrame().getUserID());
        getPanel().setLayout(new GridBagLayout());
    }

    // repaint the canvas
    public void update( Graphics g )
    {
        // 1. clear background
        g.setColor( Color.WHITE );
        g.fillRect( 0, 0, getWidth(), getHeight() );

        g.setColor(Color.BLACK);
        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        //draws outline rectangle for days
        for (int i = 0; i < days.length; i++)
        {
            g.fillRect((i * 180) + 20,50,150, 600);
            g.drawString(days[i], (i * 180) + 20,40);
        }

        //loops through all of the entries collected from DB and displays them it their specified position for their day and start and end times
        g.setColor(Color.RED);
        for (int i = 0; i < entries.size(); i++)
        {
            String start = entries.get(i).getStart_time();
            float start_fraction = timeFraction(start) * 25;

            String end = entries.get(i).getEnd_time();
            float end_fraction = timeFraction(end) * 25;

            //rectangle is drawn
            g.fillRect((entries.get(i).getDay() - 1) * 180 + 20, Math.round(start_fraction) + 50, 150, Math.round(end_fraction - start_fraction));
        }

        //text, describing the timetable activities is
        g.setColor(Color.WHITE);
        for (int i = 0; i < entries.size(); i++)
        {
            String start = entries.get(i).getStart_time();
            float start_fraction = timeFraction(start) * 25;

            String end = entries.get(i).getEnd_time();
            float end_fraction = timeFraction(end) * 25;

            g.drawString(entries.get(i).getSubject_name(), (entries.get(i).getDay() - 1) * 180 + 20, Math.round(start_fraction) + 60);

            g.drawString("Start Time: " +
                    entries.get(i).getStart_time(), (entries.get(i).getDay() - 1) * 180 + 20, Math.round(start_fraction) + 75);

            g.drawString("End Time: " +
                    entries.get(i).getEnd_time(), (entries.get(i).getDay() - 1) * 180 + 20, Math.round(start_fraction) + 90);
        }
    }

    //splits time into just hours, with minutes being a decimal of an hour, this how the positioning for graphics elements is determined
    public float timeFraction(String time)
    {
        int start_hour = Integer.valueOf(time.split(":")[0]);
        int start_minutes = Integer.valueOf(time.split(":")[1]);
        return start_hour + start_minutes/60;
    }

    //displays all of the form components for the activity
    @Override
    public void displayForm()
    {
        GridBagConstraints jConstraints = new GridBagConstraints();

        jConstraints.fill = GridBagConstraints.PAGE_END;
        jConstraints.gridy = 2;

        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        edit_day_buttons = new Object[days.length];
        for (int i = 0; i < days.length; i++)
        {
            Button btn = new Button(days[i]);
            getPanel().add(btn, jConstraints);
            edit_day_buttons[i] = btn;
        }

        change_subjects = new Button("Change Subjects");
        getPanel().add(change_subjects, jConstraints);

        edit_timetable = new Button("Edit Timetable");
        getPanel().add(edit_timetable, jConstraints);
    }

    //checks form then the class Submit or Return buttons are clicked by user
    public void checkInputs()
    {
        //loops through the array of buttons and sets listeners for each one
        for (int i = 0; i < edit_day_buttons.length; i++)
        {
            //listeners have access to their array position
            final int index = i;
            ((Button) edit_day_buttons[i]).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    //element at position 0, represent day 1, hence 1 must be added to get solution day value
                    getFrame().startActivity(new EditDay(getFrame(), new Object[]{(index + 1)}, false));                }
            });
        }

        change_subjects.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new ChangeSubjects(getFrame()));
            }
        });

        //opens the edit timetable window
        edit_timetable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new EditTimetable(getFrame()));
            }
        });
    }
}
