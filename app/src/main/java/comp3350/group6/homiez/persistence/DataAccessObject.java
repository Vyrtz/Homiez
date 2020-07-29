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

public class DataAccessObject {
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
        }//try-catch
        System.out.println("Opened" + dbType + " database " + dbPath);
    }//open

    public void close() {
        try {
            commandString = "shutdown compact";
            rs2 = statement1.executeQuery(commandString);
        } catch(Exception e) {
            e.printStackTrace();
        }//try-catch
    }//close

}//CLASS
