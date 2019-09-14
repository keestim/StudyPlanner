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

    public SignUpActivity(GUIFrame input_frame)
    {
        super(input_frame, false);
        getPanel().setLayout(new GridLayout(4, 2, 5, 10));
    }

    @Override
    public void checkInputs()
    {
        Submit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getDb_access().createUser(((TextField) text_input[0]).getText(), ((TextField) text_input[1]).getText());
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new SignInActivity(getFrame()));
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
        GridBagConstraints jConstraints = new GridBagConstraints();
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
    }
}
