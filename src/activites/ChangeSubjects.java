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

    private ArrayList<SubjectEntry> entries;

    public ChangeSubjects(GUIFrame input_frame)
    {
        super(input_frame);
        entries = getDb_access().getUserSubjects(getFrame().getUserID());
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

        for (int i = 0; i < entries.size(); i++)
        {
            jConstraints.gridy = i;
            JLabel label = new JLabel(entries.get(i).getSubjectName());
            getPanel().add(label, jConstraints);

            Button edit = new Button("edit");
            Button remove = new Button("remove");

            getPanel().add(edit, jConstraints);
            getPanel().add(remove, jConstraints);
        }

        addSubject = new Button("Add Subject");
        getPanel().add(addSubject);

        Return = new Button("Return");
        getPanel().add(Return);
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
    }
}
