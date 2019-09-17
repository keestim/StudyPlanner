package activites;

import GUIComponents.GUIFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSubject extends Activity
{
    private Button Submit;
    private Button Return;
    private Object[] text_input;

    private JTextPane error_output;

    //Displays page for users add a subject
    public AddSubject(GUIFrame input_frame)
    {
        super(input_frame);
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

        //loops through array of field names, displaying form element for the field
        for (int i = 0; i < input_fields.length; i++)
        {
            jConstraints.gridy++;
            JLabel label = new JLabel(input_fields[i] + ": ");
            TextField text = new TextField(15);
            text_input[i] = text;

            getPanel().setSize(300, 300);;
            getPanel().add(label, jConstraints);
            getPanel().add(text, jConstraints);
        }

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

                //if there are no errors subject is added to database
                if (error_string.length() == 0)
                {
                    getDb_access().addUserSubject(getFrame().getUserID(), ((TextField) text_input[0]).getText());
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

        //Returns user to home activity
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
