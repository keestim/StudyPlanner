package activites;

import GUIComponents.GUIFrame;
import data.DBActions;
import data.SubjectEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditSubject extends Activity
{
    private Button Submit;
    private Button Return;
    private Object[] text_input;
    private SubjectEntry entry;

    public EditSubject(GUIFrame input_frame, Object[] args)
    {
        super(input_frame);
        entry = getDb_access().getSubject((int) args[0]);
    }

    @Override
    public void displayForm()
    {
        getFrame().setLayout(new GridBagLayout());
        GridBagConstraints jConstraints = new GridBagConstraints();
        jConstraints.gridwidth = 2;
        jConstraints.gridy = 1;

        String[] input_fields = new String[]{"Name"};
        text_input = new Object[input_fields.length];


        JLabel label = new JLabel(input_fields[0] + ": ");
        TextField text = new TextField(15);
        text.setText(entry.getSubjectName());
        text_input[0] = text;

        getPanel().setSize(300, 300);;
        getPanel().add(label, jConstraints);
        getPanel().add(text, jConstraints);
        jConstraints.gridy++;


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
                getDb_access().updateUserSubject(entry.getSubjectID(), ((TextField) text_input[0]).getText());
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
                getFrame().startActivity(new ChangeSubjects(getFrame()));
            }
        });
    }
}
