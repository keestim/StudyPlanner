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

    private ArrayList<TimetableEntry> entries;

    public EditDay(GUIFrame input_frame, Object[] args, boolean draw_gui)
    {
        super(input_frame, args, draw_gui);
        entries = getDb_access().getTimetableEntriesForDay(getFrame().getUserID(), (Integer) args[0]);

        edit_buttons = new Button[entries.size()];
        remove_buttons = new Button[entries.size()];

        day = (int) args[0];

        error_output = new JTextPane();
        //getPanel().setLayout(new GridLayout(2, 4, 5, 10));
    }

    @Override
    public void displayForm()
    {
        GridBagConstraints jConstraints = new GridBagConstraints();

        ArrayList<SubjectEntry> subjectEntries = getDb_access().getUserSubjects(getFrame().getUserID());
        String[] subjects = new String[subjectEntries.size()];

        for (int i = 0; i < subjects.length; i++)
        {
            subjects[i] = subjectEntries.get(i).getSubjectID() + " | " + subjectEntries.get(i).getSubjectName();
        }

        JLabel label = new JLabel("Add event:");
        getPanel().add(label, jConstraints);

        jConstraints.gridwidth = 2;
        jConstraints.gridy = 1;

        subjectList = new JComboBox(subjects);
        getPanel().add(subjectList, jConstraints);

        jConstraints.gridy = 2;
        label = new JLabel("Start Time: ");
        start_time = new TextField(15);
        getPanel().add(label, jConstraints);
        getPanel().add(start_time, jConstraints);

        jConstraints.gridy = 3;
        label = new JLabel("End Time: ");
        end_time = new TextField(15);
        getPanel().add(label, jConstraints);
        getPanel().add(end_time, jConstraints);

        jConstraints.gridwidth = 1;
        jConstraints.gridy = 4;
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

                if (((TextField) start_time).getText().matches("\\d{1,}\\:\\d{2}"))
                {
                    error_string += "Make sure start time follows: hh:mm";
                }

                if (((TextField) end_time).getText().matches("\\d{1,}\\:\\d{2}"))
                {
                    error_string += "Make sure end time follows: hh:mm";
                }

                if (error_string.length() == 0)
                {
                    if (!(getDb_access().entryInTimeFrame(getFrame().getUserID(), start_time.getText(), end_time.getText(), day)))
                    {
                        getDb_access().addTimetableEntry(getFrame().getUserID(), (String) subjectList.getSelectedItem(), start_time.getText(), end_time.getText(), day);
                        //getDb_access().addUserSubject(getFrame().getUserID(), ((TextField) text_input[0]).getText());
                        getFrame().getPanel().removeAll();
                        getFrame().pack();

                        getFrame().startActivity(new HomeActivity(getFrame()));
                    }
                    else
                    {
                        error_string += "Specified time overlaps with a current activity!"
                    }
                }

                error_output.setText(error_string);

            }
        });

        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new HomeActivity(getFrame()));
            }
        });


        for (int i = 0; i < entries.size(); i++)
        {
            final int index = i;
            edit_buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });

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
