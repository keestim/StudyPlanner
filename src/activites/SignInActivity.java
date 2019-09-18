package activites;

import GUIComponents.GUIFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInActivity extends Activity
{
    private Button Submit;
    private Button SignUp;
    private Object[] text_input;
    private JTextPane error_output;

    //Displays User Login page
    public SignInActivity(GUIFrame input_frame)
    {
        //tell frame not to draw Graphics section
        super(input_frame, false);

        //creates a grid layout where form components are drawn
        getPanel().setLayout(new GridLayout(4, 4, 5, 10));
        error_output = new JTextPane();
    }

    @Override
    public void checkInputs()
    {
        Submit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String error_string = "";

                String user_name = ((TextField) text_input[0]).getText();
                String password = ((TextField) text_input[1]).getText();

                //Validates fields, to ensure they are filled out as required
                if (user_name.length() == 0)
                {
                    error_string += "Username must be entered" + '\n';
                }

                if (password.length() == 0)
                {
                    error_string += "Password must be entered" + '\n';
                }

                //if there are no errors, the sign up process is finalized
                if (error_string.length() == 0)
                {
                    //if username and password match Database entry they user is logged in
                    if (getDb_access().AuthenticateUser(user_name, password))
                    {
                        getFrame().setUserID(getDb_access().getUserIDByUsername(user_name));
                        getFrame().getPanel().removeAll();
                        getFrame().pack();
                        getFrame().startActivity(new HomeActivity(getFrame()));
                    }
                    else
                    {
                        error_string += "User with entered Username and Password doesn't exist" + '\n';
                    }
                }

                //display errors if they exist
                error_output.setText(error_string);
            }
        });

        //sends up to SignUp page
        SignUp.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getFrame().getPanel().removeAll();
                getFrame().pack();

                getFrame().startActivity(new SignUpActivity(getFrame()));
            }
        });
    }

    //displays all of the form components for the activity
    @Override
    public void displayForm()
    {
        GridBagConstraints jConstraints = new GridBagConstraints();

        jConstraints.gridwidth = GridBagConstraints.REMAINDER;
        jConstraints.anchor = GridBagConstraints.WEST;

        String[] input_fields = new String[]{"Username", "Password"};
        text_input = new Object[input_fields.length];

        //loops through array of field names to create all required elements
        for (int i = 0; i < input_fields.length; i++)
        {
            JLabel label = new JLabel(input_fields[i] + ": ");
            TextField text = new TextField(15);
            text_input[i] = text;

            getPanel().setSize(300, 300);

            //increments grid y value, to push new elements down to next vertical position
            jConstraints.gridy++;
            getPanel().add(label, jConstraints);
            getPanel().add(text, jConstraints);
        }

        // Set the window to be visible as the default to be false
        Submit = new Button("Login");
        getPanel().add(Submit, jConstraints);

        SignUp = new Button("Sign Up");
        getPanel().add(SignUp, jConstraints);

        jConstraints.gridy++;
        getPanel().add(error_output, jConstraints);

        getFrame().pack();
        getFrame().setVisible(true);
    }
}
