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
    GridBagConstraints lConstraints;

    public GUIFrame(String frameTitle, DBActions input_db_access)
    {
        super(frameTitle);
        db_access = input_db_access;

        // window decorations
        JFrame.setDefaultLookAndFeelDecorated( true );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        panel = new JPanel();
        lConstraints = new GridBagConstraints();
        startActivity(new SignInActivity(this));
    }

    public void startActivity(Activity input_activity)
    {
        getContentPane().removeAll();
        getContentPane().update(getGraphics());
        getContentPane().setLayout(new GridBagLayout());

        lConstraints.weighty = 1;
        lConstraints.gridx = 0;
        lConstraints.gridy = 0;
        //lConstraints.gridwidth = GridBagConstraints.REMAINDER;

        if (input_activity.isDraw_gui())
        {
            add(input_activity, lConstraints);
            input_activity.paint(getGraphics());
            getContentPane().paintComponents(getGraphics());
        }
        else
        {
            setPreferredSize( new Dimension( 1280, 720 ) );
            setSize(1280,780);
            //setPreferredSize( new Dimension( 1280, 720 ) );
            this.pack();
            this.setVisible(true);


        }

        lConstraints.gridheight = 1;
        lConstraints.gridy = 1;
        lConstraints.gridwidth = GridBagConstraints.REMAINDER;
        lConstraints.weighty = 0;
        lConstraints.anchor = GridBagConstraints.PAGE_END;
        add( panel, lConstraints);


        input_activity.displayForm();
        input_activity.checkInputs();

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

    public GridBagConstraints defaultConstraints()
    {
        return lConstraints;
    }

    public void showText(String input_string)
    {
        TextArea text = new TextArea("Aas");
        panel.add(text);
        this.add(panel);

        this.pack();
    }
}
