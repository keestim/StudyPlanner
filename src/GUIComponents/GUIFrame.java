package GUIComponents;

import activites.Activity;
import activites.SignInActivity;
import data.DBActions;

import java.awt.*;
import javax.swing.*;

public class GUIFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private static DBActions db_access;
    private int userID;

    public GUIFrame(String frameTitle, DBActions input_db_access)
    {
        super(frameTitle);
        db_access = input_db_access;

        // window decorations
        JFrame.setDefaultLookAndFeelDecorated( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        panel = new JPanel();
        startActivity(new SignInActivity(this));
    }

    public void startActivity(Activity input_activity)
    {
        getContentPane().removeAll();
        getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints lConstraints = new GridBagConstraints();

        lConstraints.weightx = 0.5;
        lConstraints.gridx = 0;
        lConstraints.gridy = 0;
        //lConstraints.gridwidth = GridBagConstraints.REMAINDER;

        Activity act = input_activity;
        add(act, lConstraints);

        lConstraints.gridwidth = 3;
        lConstraints.gridy = 1;
        lConstraints.anchor = GridBagConstraints.PAGE_END;
        add( panel, lConstraints);

        act.displayForm();
        act.checkInputs();


        // pack layout
        this.setVisible(true);
        pack();
    }

    public JPanel getPanel() {
        return panel;
    }

    public DBActions getDb_access() {
        return db_access;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setPanel(JPanel panel)
    {
        this.panel = panel;
    }

    public void clearScreen()
    {
        panel.removeAll();
        this.pack();
    }

    public void setNewScreen(Activity input_screen)
    {
        System.out.println("new screen");

        this.pack();
        this.setVisible(true);
    }

    public void showText(String input_string)
    {
        TextArea text = new TextArea("Aas");
        panel.add(text);
        this.add(panel);

        this.pack();
    }
}
