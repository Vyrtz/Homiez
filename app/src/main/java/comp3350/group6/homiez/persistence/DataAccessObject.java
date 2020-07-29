package comp3350.group6.homiez.persistence;

import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLWarning;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObject implements DataAccess {
    private String dbName;
    private String dbType;

    private String commandString;
    private int updateCount;
    private String result;
    private static String EOF = "  ";

    private Statement statement1, statement2, statement3;
    private Connection connection;
    private ResultSet rs2, rs3, rs4, rs5;

    private ArrayList<User> users;
    private ArrayList<Posting> postings;
    private ArrayList<Match> matches;
    private ArrayList<Request> requests;

    public DataAccessObject(String dbName) { this.dbName = dbName; }

    public void open(String dbPath) {
        String url;

        try {
            dbType = "HSQL";
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath;
            connection = DriverManager.getConnection(url, "SA", "");
            statement1 = connection.createStatement();
            statement2 = connection.createStatement();
            statement3 = connection.createStatement();

        } catch(Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        System.out.println("Opened" + dbType + " database " + dbPath);
    }

    public void close() {
        try {
            commandString = "shutdown compact";
            rs2 = statement1.executeQuery(commandString);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(User user) {
        User u = null;
        String uid;
        int age;
        String gender;
        double budget;
        String description;
        String name;

        result = null;

        try {
            commandString = "Select * from USERS where USERID='" + user.getUserId() +"'";
            rs2 = statement1.executeQuery(commandString);
            while(rs2.next()) {
                name = rs2.getString("NAME");
                budget = rs2.getDouble("BUDGET");
                gender = rs2.getString("GENDER");
                age = rs2.getInt("AGE");
                description =  rs2.getString("DESCRIPTION");
                uid = rs2.getString("USERID");
                u = new User(uid, name, age, gender);
                u.setBudget(budget);
                u.setDescription(description);

            }
            rs2.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    public String insertUser(User user) {
        return null;
    }

    public String updateUser(User user) {
        return null;
    }


    //POSTING STUFF
    public String getAllPostings(List<Posting> postingList) {
        return null;
    }

    public Posting getPosting(Posting posting) {
        return null;
    }
    public String getPostingsByUser(List<Posting> postingsList, User user) {
        return null;
    }
    public String insertPosting(Posting posting) {
        return null;
    }
    public String deletePosting(Posting posting) {
        return null;
    }
    public String updatePosting(Posting p) {
        return null;
    }

    //MATCH STUFF
    public String getMatchesForUser(List<Match> matchList, String userId) {
        Match match;
        String postingId;
        try
        {
            commandString = "Select * from MATCHES where USERID='" +userId +"'";
            rs5 = statement2.executeQuery(commandString);
            // ResultSetMetaData md5 = rs5.getMetaData();
            while (rs5.next())
            {
                postingId = rs5.getString("POSTINGID");
                match = new Match(userId, postingId);
                matchList.add(match);
            }
            rs5.close();
            return "Success";
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
    public String getMatchesForPosting(List<Match> matchList, String postingId) {
        Match match;
        String userId;
        try
        {
            commandString = "Select * from MATCHES where POSTINGID='" +postingId +"'";
            rs5 = statement2.executeQuery(commandString);
            // ResultSetMetaData md5 = rs5.getMetaData();
            while (rs5.next())
            {
                userId = rs5.getString("USERID");
                match = new Match(userId, postingId);
                matchList.add(match);
            }
            rs5.close();
            return "Success";
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public String insertMatch(Match m) {
        return null;
    }
    public String deleteMatch(Match m) {
        return null;
    }

    //REQUEST STUFF
    public String getRequests(List<Request> requests, String postingId) {
        Request request;
        String userId;
        try
        {
            commandString = "Select * from REQUESTS where POSTINGID='" +postingId +"'";
            rs5 = statement2.executeQuery(commandString);
            // ResultSetMetaData md5 = rs5.getMetaData();
            while (rs5.next())
            {
                userId = rs5.getString("USERID");
                request = new Request(userId, postingId);
                requests.add(request);
            }
            rs5.close();
            return "Success";
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
    public String insertRequest(Request request) {
        return null;
    }
    public String deleteRequest(Request request) {
        return null;
    }
}//CLASS
