package data;

public class TimetableEntry
{
    private String subject_name;
    private String start_time;
    private String end_time;
    private int day;

    public String getSubject_name() {
        return subject_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public int getDay() {
        return day;
    }

    public TimetableEntry(String subject_name, String start_time, String end_time, int day)
    {
        this.subject_name = subject_name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.day = day;
    }
}
