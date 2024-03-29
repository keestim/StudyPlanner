package activites;

import GUIComponents.GUIFrame;
import data.SubjectEntry;
import data.TimetableEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditTimetableEntry extends Activity
{
    private TimetableEntry entry;

    private Button Submit;
    private Button Return;
    private Button Remove;

    private TextField start_time;
    private TextField end_time;

    private JComboBox subjectList;

    private JTextPane error_output;

    private int day;

    //Display page for users to edit a specific timetable entry
    public EditTimetableEntry(GUIFrame input_frame, Object[] args, boolean draw_gui)
    {
        super(input_frame, draw_gui);
        //gets timetable entry from database
        entry = getDb_access().getTimetableEntry(getFrame().getUserID(), (String) args[0], (String) args[1], (Integer) args[2]);

        error_output = new JTextPane();

        start_time = new TextField();
        start_time.setText((String) args[0]);

        end_time = new TextField();
        end_time.setText((String) args[1]);

        day = entry.getDay();

        int selected_item = 0;
        //gets all users subjects
        ArrayList<SubjectEntry> subjectEntries = getDb_access().getUserSubjects(getFrame().getUserID());
        String[] subjects = new String[subjectEntries.size()];

        //loops through all the users subject gather from database, a sets as option for JComboBox element
        for (int i = 0; i < subjects.length; i++)
        {
            subjects[i] = subjectEntries.get(i).getSubjectID() + " | " + subjectEntries.get(i).getSubjectName();
            if (subjectEntries.get(i).getSubjectName().contentEquals(entry.getSubject_name()))
            {
                selected_item = i;
            }
        }
        subjectList = new JComboBox(subjects);
        subjectList.setSelectedIndex(selected_item);
    }

    //displays all of the form components for the activity
    @Override
    public void displayForm()
    {
        GridBagConstraints jConstraints = new GridBagConstraints();

        jConstraints.gridwidth = 2;
        jConstraints.gridy = 1;

        JLabel label = new JLabel("Modify event: ");
        getPanel().add(label, jConstraints);

        label = new JLabel(entry.getSubject_name() + " | " + entry.getStart_time() + " - " + entry.getEnd_time());
        getPanel().add(label, jConstraints);

        jConstraints.gridy++;

        getPanel().add(subjectList, jConstraints);

        jConstraints.gridy++;
        label = new JLabel("Start Time: ");
        getPanel().add(label, jConstraints);
        getPanel().add(start_time, jConstraints);

        jConstraints.gridy++;
        label = new JLabel("End Time: ");
        getPanel().add(label, jConstraints);
        getPanel().add(end_time, jConstraints);

        jConstraints.gridwidth = 1;
        jConstraints.gridy++;
        Submit = new Button("Submit");
        getPanel().add(Submit, jConstraints);

        Return = new Button("Return");
        getPanel().add(Return, jConstraints);

        Remove = new Button("Remove");
        getPanel().add(Remove, jConstraints);

        jConstraints.gridy++;
        getPanel().add(error_output, jConstraints);
    }

    //checks form then the class Submit or Return buttons are clicked by user
    @Override
    public void checkInputs() {
        Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String error_string = "";

                //uses regex to ensure input is in correct format
                if (!((TextField) start_time).getText().matches("\\d{1,}\\:\\d{2}")) {
                    error_string += "Make sure start time follows: hh:mm" + '\n';
                }

                if (!((TextField) end_time).getText().matches("\\d{1,}\\:\\d{2}")) {
                    error_string += "Make sure end time follows: hh:mm" + '\n';
                }

                if (error_string.length() == 0) {
                    //checks there is no overlap with existing activites, and if there is no overlap, activity is updated
                    if ((getDb_access().entryInTimeFrame(getFrame().getUserID(), start_time.getText(), end_time.getText(), day)) <= 0) {
                        getDb_access().updateTimetableEntry(getFrame().getUserID(), (String) subjectList.getSelectedItem(), entry.getStart_time(), entry.getEnd_time(), start_time.getText(), end_time.getText(), day);
                        getFrame().getPanel().removeAll();
                        getFrame().pack();

                        getFrame().startActivity(new HomeActivity(getFrame()));
                    } else {
                        error_string += "Specified time overlaps with a current activity!" + '\n';
                    }
                }

                error_output.setText(error_string);

            }
        });

        //returns the user to the home activity
        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new HomeActivity(getFrame()));
            }
        });

        //removes the selected timetable entry
        Remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDb_access().removeTimetableEntry(entry.getStart_time(), entry.getEnd_time(), day, getFrame().getUserID());
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new HomeActivity(getFrame()));
            }
        });
    }
}
