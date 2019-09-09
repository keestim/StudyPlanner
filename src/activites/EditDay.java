package activites;

import GUIComponents.GUIFrame;
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

    private ArrayList<TimetableEntry> entries;

    public EditDay(GUIFrame input_frame, Object[] args)
    {
        super(input_frame, args);
        entries = getDb_access().getTimetableEntriesForDay(getFrame().getUserID(), (Integer) args[0]);
    }

    @Override
    public void displayForm()
    {
        GridBagConstraints jConstraints = new GridBagConstraints();

        for (int i = 0; i < entries.size(); i++)
        {
            JLabel label = new JLabel(entries.get(i).getSubject_name());

            getPanel().add(label, jConstraints);
        }

        Submit = new Button("Submit");
        getPanel().add(Submit, jConstraints);

        Return = new Button("Return");
        getPanel().add(Return, jConstraints);
    }

    @Override
    public void checkInputs()
    {
        Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //getDb_access().addUserSubject(getFrame().getUserID(), ((TextField) text_input[0]).getText());
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new ChangeSubjects(getFrame()));
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
    }
}
