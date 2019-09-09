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

    public SignInActivity(GUIFrame input_frame)
    {
        super(input_frame);
    }

    @Override
    public void checkInputs()
    {
        Submit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String user_name = ((TextField) text_input[0]).getText();
                String password = ((TextField) text_input[1]).getText();
                if (getDb_access().AuthenticateUser(user_name, password))
                {
                    getFrame().setUserID(getDb_access().GetUserIDByUsername(user_name));
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    getFrame().startActivity(new HomeActivity(getFrame()));
                }
                else
                {
                    System.out.println("USER AIN'T EXISTING" + user_name);
                    //display error message!!!!
                    //implement method in gui main to draw error messages
                }
            }
        });

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

    public void paint( Graphics g )
    {
        update( g ); // repaint canvas
    }

    // repaint the canvas
    public void update( Graphics g )
    {

    }

    @Override
    public void displayForm()
    {
        GridBagConstraints jConstraints = new GridBagConstraints();

        jConstraints.fill = GridBagConstraints.HORIZONTAL;

        String[] input_fields = new String[]{"Username", "Password"};
        text_input = new Object[input_fields.length];

        for (int i = 0; i < input_fields.length; i++)
        {
            JLabel label = new JLabel(input_fields[i] + ": ");
            TextField text = new TextField(15);
            text_input[i] = text;

            getPanel().setSize(300, 300);

            jConstraints.gridy = i;
            getPanel().add(label, jConstraints);
            getPanel().add(text, jConstraints);
        }

        // Set the window to be visible as the default to be false
        Submit = new Button("Login");
        getPanel().add(Submit, jConstraints);


        SignUp = new Button("Sign Up");
        getPanel().add(SignUp, jConstraints);
        getFrame().pack();
        getFrame().setVisible(true);
    }
}
