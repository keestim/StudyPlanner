package activites;

import GUIComponents.GUIFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpActivity extends Activity {
    private Button Submit;
    private Button Return;
    private Object[] text_input;

    private JTextPane error_output;

    public SignUpActivity(GUIFrame input_frame)
    {
        super(input_frame, false);
        getPanel().setLayout(new GridLayout(5, 2, 5, 10));
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

                if (((TextField) text_input[1]).getText().length() == 0)
                {
                    error_string += "Password must have a length greater than 0" +'\n';
                }

                if (((TextField) text_input[0]).getText().length() == 0)
                {
                    error_string += "Username must have a length greater than 0" +'\n';
                }

                if (!((TextField) text_input[1]).getText().contentEquals((((TextField) text_input[2]).getText())))
                {
                    error_string += "Password must match" +'\n';
                }

                if (error_string.length() == 0)
                {
                    getDb_access().createUser(((TextField) text_input[0]).getText(), ((TextField) text_input[1]).getText());
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    getFrame().startActivity(new SignInActivity(getFrame()));
                }
                else
                {
                    error_output.setText(error_string);
                }
            }
        });

        Return.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new SignInActivity(getFrame()));
            }
        });
    }

    @Override
    public void displayForm()
    {
        GridBagConstraints jConstraints;
        jConstraints = new GridBagConstraints();
        jConstraints.gridwidth = GridBagConstraints.REMAINDER;
        jConstraints.anchor = GridBagConstraints.WEST;

        String[] input_fields = new String[]{"Username", "Password", "Confirm Password"};
        text_input = new Object[input_fields.length];

        for (int i = 0; i < input_fields.length; i++)
        {
            JLabel label = new JLabel(input_fields[i] + ": ");
            TextField text = new TextField(15);
            text_input[i] = text;

            jConstraints.gridy = i;
            getPanel().setSize(300, 300);;
            getPanel().add(label, jConstraints);
            getPanel().add(text, jConstraints);
        }

        // Set th
        // e window to be visible as the default to be false
        jConstraints.weightx = 2;
        jConstraints.gridy = input_fields.length;
        Submit = new Button("Submit");
        getPanel().add(Submit, jConstraints);

        Return = new Button("Return");
        getPanel().add(Return, jConstraints);

        jConstraints.gridy++;
        getPanel().add(error_output, jConstraints);
    }
}
