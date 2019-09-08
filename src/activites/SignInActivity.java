package activites;

import data.DBActions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignInActivity extends Activity
{
    private Button Submit;
    private Button SignUp;
    private Object[] text_input;
    private static JPanel panel;
    private static DBActions db_access;
    private static GUIFrame frame;

    public SignInActivity(GUIFrame input_frame)
    {
        super(null);
        panel = input_frame.getPanel();
        db_access = input_frame.getDb_access();
        frame = input_frame;
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
                if (db_access.AuthenticateUser(user_name, password))
                {
                    frame.setUserID(db_access.GetUserIDByUsername(user_name));
                    frame.getPanel().removeAll();
                    frame.setPanel(new JPanel());
                    frame.pack();
                    frame.startActivity(new HomeActivity(frame));
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
                //gui.clearScreen();
                //gui.setNewScreen(new SignUpActivity(gui));
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
        frame.setLayout(new GridBagLayout());
        GridBagConstraints jConstraints = new GridBagConstraints();
        jConstraints.gridheight = 6;
        jConstraints.gridwidth = 6;
        jConstraints.fill = GridBagConstraints.HORIZONTAL;

        String[] input_fields = new String[]{"Username", "Password"};
        text_input = new Object[input_fields.length];

        for (int i = 0; i < input_fields.length; i++)
        {
            JLabel label = new JLabel(input_fields[i] + ": ");
            TextField text = new TextField(15);
            text_input[i] = text;

            panel.setSize(300, 300);

            jConstraints.gridy = i;
            panel.add(label, jConstraints);
            panel.add(text, jConstraints);
        }

        // Set the window to be visible as the default to be false
        Submit = new Button("Login");
        panel.add(Submit, jConstraints);


        SignUp = new Button("Sign Up");
        panel.add(SignUp, jConstraints);
        frame.pack();
        frame.setVisible(true);
    }
}
