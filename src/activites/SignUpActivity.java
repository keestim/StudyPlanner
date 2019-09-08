package activites;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpActivity extends Activity {
    private Button Submit;
    private Button Return;
    private Object[] text_input;

    public SignUpActivity(GuiMain gui)
    {
        super(gui);
    }
/*
    public void checkInputs(GuiMain gui)
    {
        Submit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(((TextField) text_input[1]).getText());
            }
        });

        Return.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println(((TextField) text_input[1]).getText());
                gui.showText(((TextField) text_input[1]).getText());

            }
        });
    }

    @Override
    public void displayScreen(GuiMain gui)
    {
        SpringLayout layout = new SpringLayout();

        String[] input_fields = new String[]{"Username", "Password", "Confirm Password"};
        text_input = new Object[input_fields.length];

        for (int i = 0; i < input_fields.length; i++)
        {
            JLabel label = new JLabel(input_fields[i] + ": ");
            TextField text = new TextField(15);
            text_input[i] = text;

            gui.getPanel().setSize(300, 300);
            gui.getPanel().setLayout(layout);
            gui.getPanel().add(label);
            gui.getPanel().add(text);

            // Put constraint on components
            layout.putConstraint(SpringLayout.WEST, label, 20, SpringLayout.WEST, gui.getPanel());
            layout.putConstraint(SpringLayout.NORTH, label, 20 + i * 25, SpringLayout.NORTH, gui.getPanel());
            layout.putConstraint(SpringLayout.WEST, text, 20, SpringLayout.EAST, label);
            layout.putConstraint(SpringLayout.NORTH, text, 20 + i * 25, SpringLayout.NORTH, gui.getPanel());
        }

        // Set the window to be visible as the default to be false
        Submit = new Button("Submit");
        gui.getPanel().add(Submit);
        layout.putConstraint(SpringLayout.WEST, Submit, 20, SpringLayout.WEST, gui.getPanel());
        layout.putConstraint(SpringLayout.NORTH, Submit, 20 + input_fields.length * 25, SpringLayout.NORTH, gui.getPanel());

        Return = new Button("Return");
        gui.getPanel().add(Return);
        layout.putConstraint(SpringLayout.WEST, Return, 20, SpringLayout.WEST, gui.getPanel());
        layout.putConstraint(SpringLayout.NORTH, Return, 20 + (input_fields.length + 1) * 25, SpringLayout.NORTH, gui.getPanel());
        checkInputs(gui);
    }

 */

}
