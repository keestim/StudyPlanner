package data;

public class TimetableSettings
{
    private String start_time;
    private String end_time;

    public TimetableSettings(String start_time, String end_time) {
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }
}
