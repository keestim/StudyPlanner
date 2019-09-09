package data;

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
