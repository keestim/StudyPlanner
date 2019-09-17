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

    private JTextPane error_output;

    //Displays the edit page for a selected user subject
    public EditSubject(GUIFrame input_frame, Object[] args)
    {
        super(input_frame);
        entry = getDb_access().getSubject((int) args[0]);

        error_output = new JTextPane();
    }

    //displays all of the form components for the activity
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

        jConstraints.gridy++;
        jConstraints.gridx++;
        getPanel().add(error_output, jConstraints);
    }

    //checks form then the class Submit or Return buttons are clicked by user
    @Override
    public void checkInputs()
    {
        Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String error_string = "";

                if (((TextField) text_input[0]).getText().length() == 0)
                {
                    error_string = "Ensure subject name is entered";
                }

                //if there are no errors, the edited subject is updated in the database
                if (error_string.length() == 0)
                {
                    getDb_access().updateUserSubject(entry.getSubjectID(), ((TextField) text_input[0]).getText());
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    getFrame().startActivity(new ChangeSubjects(getFrame()));
                }
                else
                {
                    //errors are displayed if they exist
                    error_output.setText(error_string);
                }
            }
        });

        //Returns the user to ChangeSubjects activity
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
