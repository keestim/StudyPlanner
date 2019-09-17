package activites;

import GUIComponents.GUIFrame;
import data.SubjectEntry;
import data.TimetableEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditDay extends Activity
{
    private Button Submit;
    private Button Return;

    private TextField start_time;
    private TextField end_time;

    private JComboBox subjectList;

    private Button[] edit_buttons;
    private Button[] remove_buttons;

    private JTextPane error_output;

    private int day;

    //array for each of the days of the week
    private String[] days_of_week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private ArrayList<TimetableEntry> entries;

    //Page is used from creating a new event of a specific day or view and editing/removing existing events for a day
    public EditDay(GUIFrame input_frame, Object[] args, boolean draw_gui)
    {
        super(input_frame, draw_gui);
        entries = getDb_access().getTimetableEntriesForDay(getFrame().getUserID(), (Integer) args[0]);

        edit_buttons = new Button[entries.size()];
        remove_buttons = new Button[entries.size()];

        day = (int) args[0];

        error_output = new JTextPane();
    }

    //displays all of the form components for the activity
    @Override
    public void displayForm()
    {
        GridBagConstraints jConstraints = new GridBagConstraints();

        //users subjects is grabbed from database and then added to list
        ArrayList<SubjectEntry> subjectEntries = getDb_access().getUserSubjects(getFrame().getUserID());
        String[] subjects = new String[subjectEntries.size()];

        for (int i = 0; i < subjects.length; i++)
        {
            subjects[i] = subjectEntries.get(i).getSubjectID() + " | " + subjectEntries.get(i).getSubjectName();
        }

        jConstraints.gridwidth = 2;
        jConstraints.gridy = 1;

        JLabel label = new JLabel(days_of_week[day - 1]);
        getPanel().add(label, jConstraints);

        jConstraints.gridy++;

        label = new JLabel("Add event:");
        getPanel().add(label, jConstraints);

        jConstraints.gridy++;

        //dropdown is created of all a user's subjects
        subjectList = new JComboBox(subjects);
        getPanel().add(subjectList, jConstraints);

        jConstraints.gridy++;
        label = new JLabel("Start Time: ");
        start_time = new TextField(15);
        getPanel().add(label, jConstraints);
        getPanel().add(start_time, jConstraints);

        jConstraints.gridy++;
        label = new JLabel("End Time: ");
        end_time = new TextField(15);
        getPanel().add(label, jConstraints);
        getPanel().add(end_time, jConstraints);

        jConstraints.gridwidth = 1;
        jConstraints.gridy++;
        Submit = new Button("Submit");
        getPanel().add(Submit, jConstraints);

        Return = new Button("Return");
        getPanel().add(Return, jConstraints);

        jConstraints.gridy++;
        getPanel().add(error_output, jConstraints);

        GridBagConstraints kConstraints = new GridBagConstraints();
        kConstraints.fill = GridBagConstraints.EAST;
        kConstraints.anchor = GridBagConstraints.PAGE_END;
        kConstraints.gridwidth = 1;
        kConstraints.gridy = 1;
        kConstraints.gridx = 5;

        //if there and time table entries present on the selected day, data for the entry
        //and button for removing and editing are displayed on the screen
        if (entries.size() > 0)
        {
            label = new JLabel("Edit Day: ");
            getPanel().add(label, kConstraints);
            kConstraints.gridy++;

            for (int i = 0; i < entries.size(); i++) {
                label = new JLabel(entries.get(i).getSubject_name() + " | " + entries.get(i).getStart_time() + " - " + entries.get(i).getEnd_time());

                getPanel().add(label, kConstraints);

                Button edit = new Button("Edit");
                Button remove = new Button("Remove");
                edit_buttons[i] = edit;
                remove_buttons[i] = remove;

                kConstraints.gridx++;
                getPanel().add(edit, kConstraints);
                kConstraints.gridx++;
                getPanel().add(remove, kConstraints);

                kConstraints.gridx = 5;
                kConstraints.gridy++;
            }
        }
    }

    @Override
    public void checkInputs()
    {
        Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String error_string = "";

                //ensures that the start/end times for event follow regex formatting
                if (!((TextField) start_time).getText().matches("\\d{1,}\\:\\d{2}"))
                {
                    error_string += "Make sure start time follows: hh:mm" + '\n';
                }

                if (!((TextField) end_time).getText().matches("\\d{1,}\\:\\d{2}"))
                {
                    error_string += "Make sure end time follows: hh:mm" + '\n';
                }

                //if there are no errors, user is sent to home activity and database entry is added
                if (error_string.length() == 0)
                {
                    if ((getDb_access().entryInTimeFrame(getFrame().getUserID(), start_time.getText(), end_time.getText(), day)) == 0)
                    {
                        getDb_access().addTimetableEntry(getFrame().getUserID(), (String) subjectList.getSelectedItem(), start_time.getText(), end_time.getText(), day);
                        getFrame().getPanel().removeAll();
                        getFrame().pack();

                        getFrame().startActivity(new HomeActivity(getFrame()));
                    }
                    else
                    {
                        error_string += "Specified time overlaps with a current activity!" + '\n';
                    }
                }

                //if there are errors they are displayed on the screen
                error_output.setText(error_string);
            }
        });

        //Returns the user to the home page
        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new HomeActivity(getFrame()));
            }
        });


        //loops through the array of buttons and sets listeners for each one
        for (int i = 0; i < entries.size(); i++)
        {
            final int index = i;
            edit_buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    //starts new activity, for edit time table entry, on day i
                    //array of edit button's positions correspond to an timetable entry element in entries arraylist and position i
                    getFrame().startActivity(new EditTimetableEntry(getFrame(), new Object[]{entries.get(index).getStart_time(), entries.get(index).getEnd_time(), entries.get(index).getDay()}, false));
                }
            });

            //removes the timetable event at possible i
            remove_buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getDb_access().removeTimetableEntry(entries.get(index).getStart_time(), entries.get(index).getEnd_time(), day, getFrame().getUserID());
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    getFrame().startActivity(new HomeActivity(getFrame()));
                }
            });
        }
    }
}
