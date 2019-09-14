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

    public ChangeSubjects(GUIFrame input_frame)
    {
        super(input_frame);
        entries = getDb_access().getUserSubjects(getFrame().getUserID());

        edit_subjects = new Button[entries.size()];
        remove_subjects = new Button[entries.size()];
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
        getFrame().setLayout(new GridBagLayout());
        GridBagConstraints jConstraints = new GridBagConstraints();
        jConstraints.gridwidth = 2;
        jConstraints.gridy = 1;

        for (int i = 0; i < entries.size(); i++)
        {
            jConstraints.gridy++;
            JLabel label = new JLabel(entries.get(i).getSubjectName());
            getPanel().add(label, jConstraints);

            Button edit = new Button("Edit");
            Button remove = new Button("Remove");

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

        for (int i = 0; i < entries.size(); i++)
        {
            final int index = i;

            edit_subjects[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    getFrame().startActivity(new EditSubject(getFrame(), new Object[]{entries.get(index).getSubjectID()}));
                }
            });

            remove_subjects[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getDb_access().removeSubject(entries.get(index).getSubjectID());
                    getFrame().getPanel().removeAll();
                    getFrame().pack();
                    getFrame().startActivity(new HomeActivity(getFrame()));
                }
            });
        }
    }
}
