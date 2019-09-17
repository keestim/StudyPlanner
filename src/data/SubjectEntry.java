package data;

//class is used for database results that come from the usersubjects table
public class SubjectEntry
{
    private int SubjectID;
    private String SubjectName;

    public SubjectEntry(int subjectID, String subjectName) {
        SubjectID = subjectID;
        SubjectName = subjectName;
    }

    public int getSubjectID() {
        return SubjectID;
    }

    public String getSubjectName() {
        return SubjectName;
    }
}
