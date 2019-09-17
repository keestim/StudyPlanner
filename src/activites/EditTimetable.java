package activites;

import GUIComponents.GUIFrame;
import data.TimetableSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditTimetable extends Activity
{
    private TimetableSettings timetableSettings;

    private Button Submit;
    private Button Return;
    private Object[] text_input;
    private JTextPane error_output;

    //Displays the screen which allows users to edit their global timetable settings
    public EditTimetable(GUIFrame input_frame)
    {
        super(input_frame);
        //gets user's timetable settings as object from database
        timetableSettings = getDb_access().getTimetableSettings(getFrame().getUserID());
        error_output = new JTextPane();
    }

    //displays all of the form components for the activity
    @Override
    public void displayForm()
    {
        getFrame().setLayout(new GridBagLayout());
        GridBagConstraints jConstraints = new GridBagConstraints();
        jConstraints.gridwidth = 2;

        String[] input_fields = new String[]{"Start Time", "End Time"};
        text_input = new Object[input_fields.length];
        jConstraints.gridy = 1;

        //loops through all the field names in array and displays them on screen
        for (int i = 0; i < input_fields.length; i++)
        {
            JLabel label = new JLabel(input_fields[i] + ": ");
            TextField text = new TextField(15);
            if (i == 0)
            {
                text.setText(timetableSettings.getStart_time());
            }
            else
            {

                text.setText(timetableSettings.getEnd_time());
            }

            text_input[i] = text;
            getPanel().setSize(300, 300);
            getPanel().add(label, jConstraints);
            getPanel().add(text, jConstraints);
            jConstraints.gridy++;
        }

        Submit = new Button("Submit");
        getPanel().add(Submit, jConstraints);

        Return = new Button("Return");
        getPanel().add(Return, jConstraints);

        jConstraints.gridy++;
        getPanel().add(error_output, jConstraints);
    }

    //checks form then the class Submit or Return buttons are clicked by user
    @Override
    public void checkInputs()
    {
        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new HomeActivity(getFrame()));
            }
        });

        Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String error_string = "";

                //regex makes sure that input is in correct format
                if (!((TextField) text_input[0]).getText().matches("\\d{1,}\\:\\d{2}"))
                {
                    error_string += "Make sure start time follows: hh:mm";
                }

                if (!((TextField) text_input[1]).getText().matches("\\d{1,}\\:\\d{2}"))
                {
                    error_string += "Make sure end time follows: hh:mm";
                }

                //if there is no error then timetables is updated
                if (error_string.length() == 0)
                {
                    getDb_access().updateUsertimeTable(getFrame().getUserID(), ((TextField) text_input[0]).getText(), ((TextField) text_input[1]).getText());
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    //user if returned to home activity
                    getFrame().startActivity(new HomeActivity(getFrame()));
                }
                else
                {
                    //display errors, if present
                    error_output.setText(error_string);
                }
            }
        });

    }
}
