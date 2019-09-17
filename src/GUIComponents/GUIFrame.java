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

    //stores ID of currently logged in user
    private int userID;

    //stores constraints for the main panel
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
        //program is started with SignInAcitivity
        startActivity(new SignInActivity(this));
    }

    public void startActivity(Activity input_activity)
    {
        getContentPane().removeAll();
        getContentPane().update(getGraphics());
        getContentPane().setLayout(new GridBagLayout());

        //updates constraints for graphics panel
        lConstraints.weighty = 1;
        lConstraints.gridx = 0;
        lConstraints.gridy = 0;

        //if activity specifies Graphics conponent to be drawn then Graphics is added
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

        //updates constraints for form
        lConstraints.gridheight = 1;
        lConstraints.gridy = 1;
        lConstraints.gridwidth = GridBagConstraints.REMAINDER;
        lConstraints.weighty = 0;
        lConstraints.anchor = GridBagConstraints.PAGE_END;
        add( panel, lConstraints);

        //call activity methods to display form content and
        input_activity.displayForm();
        //runs method to asyncronously check form inputs
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
}
