package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class DBActions
{
    private static Connection fDBConnection;

    public DBActions()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            fDBConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studyplanner", "root", "");
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }

    public void DBExists()
    {
        try
        {
            Statement stmt = fDBConnection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Users (ID INT AUTO_INCREMENT PRIMARY KEY, Username VARCHAR(256) NOT NULL, Password VARCHAR(256) NOT NULL)");
            stmt.executeUpdate("CREATE TABLE UserTimeTables(UserID INT, SubjectID INT, StartTime VARCHAR(20), EndTime VARCHAR(20), Day INT);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TimeTableSettings(UserID INT, StartTime VARCHAR(20) NOT NULL, EndTime VARCHAR(20) NOT NULL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS UserSubjects(SubjectID INT AUTO_INCREMENT PRIMARY KEY, SubjectName VARCHAR(50) NOT NULL)");
            stmt.executeUpdate("ALTER TABLE TimeTableSettings ADD FOREIGN KEY (UserID) REFERENCES Users(ID)");
            stmt.executeUpdate("ALTER TABLE UserTimeTables ADD FOREIGN KEY (UserID) REFERENCES Users(ID)");
            stmt.executeUpdate("ALTER TABLE UserTimeTables ADD FOREIGN KEY (SubjectID) REFERENCES UserSubjects(SubjectID)");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

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
                return true;
            }

            return false;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    public int GetUserIDByUsername(String username)
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
    
}