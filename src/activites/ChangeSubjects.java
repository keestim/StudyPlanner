package activites;

import GUIComponents.GUIFrame;
import data.SubjectEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChangeSubjects extends Activity
{
    private Button Return;
    private Button addSubject;

    private Button[] edit_subjects;
    private Button[] remove_subjects;

    private ArrayList<SubjectEntry> entries;

    //Displays Screen to change user subjects
    public ChangeSubjects(GUIFrame input_frame)
    {
        super(input_frame);
        //get an array of user subjects from database
        entries = getDb_access().getUserSubjects(getFrame().getUserID());

        //specifies array size by number of subject entries returned from Database
        edit_subjects = new Button[entries.size()];
        remove_subjects = new Button[entries.size()];
    }

    //displays all of the form components for the activity
    @Override
    public void displayForm()
    {
        getFrame().setLayout(new GridBagLayout());
        GridBagConstraints jConstraints = new GridBagConstraints();
        jConstraints.gridwidth = 2;
        jConstraints.gridy = 1;

        //loops through all of the object in entries list, outputted appropiate data to page
        for (int i = 0; i < entries.size(); i++)
        {
            jConstraints.gridy++;
            JLabel label = new JLabel(entries.get(i).getSubjectName());
            getPanel().add(label, jConstraints);

            Button edit = new Button("Edit");
            Button remove = new Button("Remove");

            //assign the various subject inputs
            edit_subjects[i] = edit;
            remove_subjects[i] = remove;

            getPanel().add(edit, jConstraints);
            getPanel().add(remove, jConstraints);
        }

        jConstraints.gridy++;

        addSubject = new Button("Add Subject");
        getPanel().add(addSubject, jConstraints);

        Return = new Button("Return");
        getPanel().add(Return, jConstraints);
    }

    //checks form then the class Submit or Return buttons are clicked by user
    @Override
    public void checkInputs()
    {
        addSubject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new AddSubject(getFrame()));
            }
        });

        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().getPanel().removeAll();
                getFrame().pack();
                getFrame().startActivity(new HomeActivity(getFrame()));
            }
        });

        //loops through the array of buttons and sets listeners for each one
        for (int i = 0; i < entries.size(); i++)
        {
            //listeners have access to their array position
            final int index = i;

            edit_subjects[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    //the button's position in the array, matches the position of the associated Subject Object in the entries list
                    getFrame().startActivity(new EditSubject(getFrame(), new Object[]{entries.get(index).getSubjectID()}));
                }
            });

            remove_subjects[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //the button's position in the array, matches the position of the associated Subject Object in the entries list
                    getDb_access().removeSubject(entries.get(index).getSubjectID());
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    getFrame().startActivity(new HomeActivity(getFrame()));
                }
            });
        }
    }
}
