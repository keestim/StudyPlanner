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

    public EditTimetable(GUIFrame input_frame)
    {
        super(input_frame);
        //if timetable settings don't exist for the user, then create entry!
        timetableSettings = getDb_access().getTimetableSettings(getFrame().getUserID());
    }

    @Override
    public void displayForm()
    {
        getFrame().setLayout(new GridBagLayout());
        GridBagConstraints jConstraints = new GridBagConstraints();
        jConstraints.gridwidth = 2;

        String[] input_fields = new String[]{"Start Time", "End Time"};
        text_input = new Object[input_fields.length];
        jConstraints.gridy = 1;

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
    }

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

    }
}
