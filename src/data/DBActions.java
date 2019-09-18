package data;

import activites.HomeActivity;

import javax.rmi.CORBA.Tie;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class DBActions
{
    private static Connection fDBConnection;

    //Class is for database interactions
    public DBActions()
    {
        try
        {
            //loads in the required drive for database connection
            Class.forName("com.mysql.jdbc.Driver");

            //gets the connection value for the system database
            fDBConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studyplanner", "root", "");
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    //checks the database tables exist, if not the database tables are created
    public void DBExists()
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Users (ID INT AUTO_INCREMENT PRIMARY KEY, Username VARCHAR(256) NOT NULL, Password VARCHAR(256) NOT NULL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS UserTimeTables(UserID INT, SubjectID INT, StartTime VARCHAR(20), EndTime VARCHAR(20), Day INT);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TimeTableSettings(UserID INT, StartTime VARCHAR(20) NOT NULL, EndTime VARCHAR(20) NOT NULL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS UserSubjects(SubjectID INT AUTO_INCREMENT PRIMARY KEY, SubjectName VARCHAR(50) NOT NULL, UserID INT)");
            stmt.executeUpdate("ALTER TABLE TimeTableSettings ADD FOREIGN KEY (UserID) REFERENCES Users(ID)");
            stmt.executeUpdate("ALTER TABLE UserTimeTables ADD FOREIGN KEY (UserID) REFERENCES Users(ID)");
            stmt.executeUpdate("ALTER TABLE UserTimeTables ADD FOREIGN KEY (SubjectID) REFERENCES UserSubjects(SubjectID)");
            stmt.executeUpdate("ALTER TABLE UserSubjects ADD FOREIGN KEY (UserID) REFERENCES Users(ID)");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    //check if user with input username and password exist
    public boolean AuthenticateUser(String username, String password)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM users WHERE Username = '" + username + "' AND Password = '" + password + "'");
            result.next();
            result.last();

            if (result.getRow() > 0)
            {
                //if there is a match (i.e there is atleast one row returned, then true is returned)
                return true;
            }

            //if there isn't a match (i.e. there isn't any row return, then return false)
            return false;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    //user for check if there is a user that exists the input username
    public int getUserIDByUsername(String username)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM users WHERE Username = '" + username + "'");
            result.next();
            result.last();

            if (result.getRow() > 0)
            {
                return result.getInt(1);
            }

            return -1;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return -1;
    }

    //gets all of the timetable entries for a given input User ID
    public ArrayList<TimetableEntry> getTimetableEntries(int UserID)
    {
        ArrayList<TimetableEntry> return_entries = new ArrayList<>();

        try
        {
            Statement stmt = fDBConnection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM usertimetables INNER JOIN usersubjects ON usertimetables.SubjectID = usersubjects.SubjectID WHERE usertimetables.UserID = '" + UserID + "'");

            while (result.next())
            {
                return_entries.add(new TimetableEntry(result.getString(7), result.getString(3), result.getString(4), Integer.valueOf(result.getString(5))));
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return return_entries;
    }

    //gets all of the timetable entries for a given input User ID on a specific day
    //day ranges from (1-7) with 1 being monday and 7 being sunday
    public ArrayList<TimetableEntry> getTimetableEntriesForDay(int UserID, int Day)
    {
        ArrayList<TimetableEntry> return_entries = new ArrayList<>();

        try
        {
            Statement stmt = fDBConnection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM usertimetables INNER JOIN usersubjects ON usertimetables.SubjectID = usersubjects.SubjectID WHERE usertimetables.UserID = '" + UserID + "' AND usertimetables.Day = '" + Day + "' ORDER BY usertimetables.StartTime");

            while (result.next())
            {
                return_entries.add(new TimetableEntry(result.getString(7), result.getString(3), result.getString(4), Integer.valueOf(result.getString(5))));
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return return_entries;
    }

    //creates user account for the solution, from a given input username and password
    public boolean createUser(String username, String password)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            int result = stmt.executeUpdate("INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')");
            if (result >= 0)
            {
                ResultSet resultSet = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
                resultSet.next();
                int userID = resultSet.getInt(1);

                //users are required to have a associated entry in timetablesettings, so UserID is gathered and then entry is added to table
                result = stmt.executeUpdate("INSERT INTO timetablesettings VALUES ('" + userID + "', '00:00', '24:00')");
                if (result >= 0)
                {
                    return true;
                }

                return false;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    //gets all of the subject of a user for a given input UserID
    public ArrayList<SubjectEntry> getUserSubjects(int UserID)
    {
        ArrayList<SubjectEntry> return_entries = new ArrayList<>();

        try
        {
            Statement stmt = fDBConnection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM usersubjects WHERE usersubjects.UserID = '" + UserID + "'");

            while (result.next())
            {
                return_entries.add(new SubjectEntry(Integer.valueOf(result.getString(1)), result.getString(2)));
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return return_entries;
    }

    //adds a subject associated with a specific user
    public boolean addUserSubject(int UserID, String SubjectName)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            int result = stmt.executeUpdate("INSERT INTO usersubjects (UserID, SubjectName) VALUES ('" + UserID +"', '" + SubjectName + "')");

            if (result >= 0)
            {
                return true;
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    //returns a given users timetable settings as a TimetableSettings object
    public TimetableSettings getTimetableSettings(int UserID)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM timetablesettings WHERE UserID = " + UserID);
            result.next();

            return new TimetableSettings(result.getString(2), result.getString(3));
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    //retuns a SubejctEntry object from a input SubjectID
    public SubjectEntry getSubject(int SubjectID)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM usersubjects WHERE SubjectID = " + SubjectID);
            result.next();

            return new SubjectEntry(Integer.valueOf(result.getString(1)), result.getString(2));
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    //Removes a subject using provided SubjectID
    public boolean removeSubject(int SubjectID)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();

            int result = stmt.executeUpdate("DELETE FROM usertimetables WHERE SubjectID = '" + SubjectID + "'");
            if (result >= 0)
            {
                result = stmt.executeUpdate("DELETE FROM usersubjects WHERE SubjectID = '" + SubjectID + "'");

                if (result >= 0)
                {
                    return true;
                }
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    //Adds a timetable entry
    public boolean addTimetableEntry(int UserID, String SubjectName, String StartTime, String EndTime, int day)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            int subject_ID = Integer.valueOf(SubjectName.split(" | ")[0]);
            int result = stmt.executeUpdate("INSERT INTO usertimetables VALUES ('" + UserID +"', " + subject_ID + ",'" + StartTime + "','" + EndTime + "'," + day + ")");

            if (result >= 0)
            {
                return true;
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    //removes a timetable entry
    public boolean removeTimetableEntry(String StartTime, String EndTime, int Day, int UserID)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            int result = stmt.executeUpdate("DELETE FROM usertimetables WHERE UserID = " + UserID + " AND StartTime = '" + StartTime + "' AND EndTime = '" + EndTime + "' AND Day = " + Day);

            if (result >= 0)
            {
                return true;
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    //updates user subject with new name
    public boolean updateUserSubject(int SubjectID, String SubjectName)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            int result = stmt.executeUpdate("UPDATE usersubjects SET SubjectName = '" + SubjectName + "'  WHERE SubjectID = " + SubjectID);

            if (result >= 0)
            {
                return true;
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    //checks if there are any timetable entries within existing timeframe to prevent any overlap when adding new entry
    public int entryInTimeFrame(int UserID, String start_time, String end_time, int day)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM usertimetables WHERE UserID = " + UserID + " AND StartTime >= '" + start_time + "' AND EndTime <= '" + end_time +"' AND Day = " + day);
            result.next();
            result.last();
            return result.getRow();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return -1;
    }

    //update usertime table with new start and end date
    public boolean updateUsertimeTable(int UserID, String starttime, String endtime)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            int result = stmt.executeUpdate("UPDATE timetablesettings SET StartTime = '" + starttime + "' AND EndTime = '" + endtime + "' WHERE UserID = " + UserID);

            if (result >= 0)
            {
                return true;
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    //return time table entry within specific timeframe and day
    public TimetableEntry getTimetableEntry(int UserID, String start_time, String end_time, int day)
    {
        try {
            Statement stmt = fDBConnection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM usertimetables INNER JOIN usersubjects ON usertimetables.SubjectID = usersubjects.SubjectID WHERE usertimetables.UserID = " +
                    UserID + " AND usertimetables.StartTime >= '" + start_time + "' AND usertimetables.EndTime <= '" + end_time + "' AND usertimetables.Day = " + day);
            result.next();
            return new TimetableEntry(result.getString(7), result.getString(3), result.getString(4), result.getInt(5));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    //updates a timetable entry, wth provided input
    public boolean updateTimetableEntry(int UserID, String SubjectName, String previousStartTime, String previousEndTime, String StartTime, String EndTime, int day)
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            int subject_ID = Integer.valueOf(SubjectName.split(" | ")[0]);

            int result = stmt.executeUpdate("UPDATE usertimetables SET SubjectID = " + subject_ID + ", StartTime = '" + StartTime + "', EndTime = '" + EndTime + "', Day = " + day + " WHERE UserID = " + UserID + " AND Day = " + day +
                    " AND StartTime = '" + previousStartTime + "' AND EndTime = '" + previousEndTime + "'");

            if (result >= 0)
            {
                return true;
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return false;
    }
}