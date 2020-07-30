package comp3350.group6.homiez.persistence;

import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;
import java.sql.Connection;
import java.sql.DriverManager;
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

        }
        catch(Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
        System.out.println("Opened" + dbType + " database " + dbPath);
    }

    public void close() {
        try {
            commandString = "shutdown compact";
            rs2 = statement1.executeQuery(commandString);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(User user) {
        User u = null;
        String uid;
        int age;
        String gender;
        double budget;
        String bio;
        String name;

        try {
            commandString = "Select * from USERS where USERID='" + user.getUserId() +"'";
            rs2 = statement1.executeQuery(commandString);
            while(rs2.next()) {
                name = rs2.getString("NAME");
                budget = rs2.getDouble("BUDGET");
                gender = rs2.getString("GENDER");
                age = rs2.getInt("AGE");
                bio =  rs2.getString("BIOGRAPHY");
                uid = rs2.getString("USERID");
                u = new User(uid, name, age, gender,budget,bio);
                getInterests(u);
            }
            rs2.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    public String insertUser(User user) {
        result = null;
        String values;

        try {

            values = "'" + user.getUserId()
                    +"', '" + user.getName()
                    +"', '" + user.getAge()
                    +"', '" + user.getGender()
                    +"', '" + user.getBudget()
                    +"', '" + user.getBiography() + "'";

            commandString = "INSERT INTO USERS VALUES(" + values + ")";
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);

        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String updateUser(User user) {
        result = null;
        String values;
        String where;
        User userOld = getUser(user);

        if(userOld == null)
            return result;

        if(user.getName() == null)
            user.setName(userOld.getName());
        if(user.getAge() == 0)
            user.setAge(userOld.getAge());
        if(user.getGender() == null)
            user.setGender(userOld.getGender());
        if(user.getBudget() == 0)
            user.setBudget(userOld.getBudget());
        if(user.getBiography() == null)
            user.setBiography(userOld.getBiography());
        if(user.getInterests() == null)
            user.setInterests(userOld.getInterests());

        try {

            values = "USERID='" + userOld.getUserId()
                    +"', NAME='" + user.getName()
                    +"', AGE='" + user.getAge()
                    +"', GENDER='" + user.getGender()
                    +"', BUDGET='" + user.getBudget()
                    +"', BIOGRAPHY='" + user.getBiography() + "'";
            where = "WHERE USERID='" + user.getUserId()+"'";
            commandString = "UPDATE USERS " + " SET " + values + " " + where;
            updateInterests( user);
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    //POSTING STUFF
    public String getAllDisplayPostings(List<Posting> postingsList, User user) {
        Posting p;
        result = null;
        try {
            commandString = "Select * from POSTINGS where USERID!='"+user.getUserId()+"'";
            rs3 = statement2.executeQuery(commandString);

            while(rs3.next()) {
                p = constructPosting(rs3);
                postingsList.add(p);
            }
            rs3.close();
            result = "Success";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Posting getPosting(Posting posting) {
        Posting p = null;
        try {
            commandString = "Select * from POSTINGS where POSTINGID='"+posting.getPostingId()+"'";
            rs3 = statement2.executeQuery(commandString);

            while(rs3.next()) {
                p = constructPosting(rs3);
            }
            rs3.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public String getPostingsByUser(List<Posting> postingsList, User user) {
        Posting p;
        result = null;
        try {
            commandString = "Select * from POSTINGS where USERID='"+user.getUserId()+"'";
            rs3 = statement2.executeQuery(commandString);

            while(rs3.next()) {
                p = constructPosting(rs3);
                postingsList.add(p);
            }
            rs3.close();
            result = "Success";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Posting constructPosting(ResultSet rs) throws SQLException {
        String title = rs.getString("TITLE");
        double price = rs.getDouble("PRICE");
        String location = rs.getString("LOCATION");
        String type = rs.getString("TYPE");
        String description = rs.getString("DESCRIPTION");
        String uid = rs.getString("USERID");
        String pid = rs.getString("POSTINGID");

        User u = new User(uid);
        u = getUser(u);
        Posting p = new Posting(pid,title,u,price,location,type,description);

        return p;
    }

    public String insertPosting(Posting posting) {
        String values;
        result = null;

        try {
            values = "'" + posting.getPostingId()
                    +"', '" + posting.getUser().getUserId()
                    +"', '" + posting.getTitle()
                    +"', '" + posting.getPrice()
                    +"', '" + posting.getLocation()
                    +"', '" + posting.getType()
                    +"', '" + posting.getDescription() + "'";

            commandString = "INSERT INTO POSTINGS VALUES(" + values + ")";
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public String deletePosting(Posting posting) {
        String values;
        result = null;

        try {
            values = posting.getPostingId();
            commandString = "DELETE FROM REQUESTS WHERE POSTINGID='" + values +"'";
            statement1.executeUpdate(commandString);
            commandString = "DELETE FROM MATCHES WHERE POSTINGID='" + values +"'";
            statement1.executeUpdate(commandString);
            commandString = "DELETE FROM POSTINGS WHERE POSTINGID='" + values +"'";
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String updatePosting(Posting p) {
        String values;
        String where;
        result = null;

        try {
            values = "POSTINGID='" + p.getPostingId()
                    +"', TITLE='" + p.getTitle()
                    +"', USERID='" + p.getUser().getUserId()
                    +"', PRICE='" + p.getPrice()
                    +"', LOCATION='" + p.getLocation()
                    +"', TYPE='" + p.getType()
                    +"', DESCRIPTION='" + p.getDescription() + "'";

            where = "WHERE POSTINGID=" + p.getPostingId();

            commandString = "UPDATE POSTINGS " + " SET " + values + " " + where;
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //MATCH STUFF
    public String getMatchesForUser(List<Match> matchList, String userId) {
        Match match;
        String postingId;
        result = null;
        try {
            commandString = "Select * from MATCHES where USERID='" +userId +"'";
            rs5 = statement3.executeQuery(commandString);
            // ResultSetMetaData md5 = rs5.getMetaData();
            while (rs5.next()) {
                postingId = rs5.getString("POSTINGID");
                match = new Match(userId, postingId);
                matchList.add(match);
            }
            rs5.close();
            result = "Success";
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public String getMatchesForPosting(List<Match> matchList, String postingId) {
        Match match;
        String userId;
        result = null;
        try {
            commandString = "Select * from MATCHES where POSTINGID='" +postingId +"'";
            rs5 = statement3.executeQuery(commandString);
            while (rs5.next()) {
                userId = rs5.getString("USERID");
                match = new Match(userId, postingId);
                matchList.add(match);
            }
            rs5.close();
            result = "Success";
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public String insertMatch(Match m) {
        String values;
        result = null;

        try {
            values = "'" + m.getUserId()
                    +"', '" + m.getPostingId() + "'";
            commandString = "INSERT INTO MATCHES VALUES(" + values + ")";
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public String deleteMatch(Match m) {
        String uId;
        String pId;
        result = null;

        try {
            uId = m.getUserId();
            pId = m.getPostingId();
            commandString = "DELETE FROM MATCHES WHERE USERID=" + uId + " AND POSTINGID=" + pId;
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //REQUEST STUFF
    public String getRequests(List<Request> requests, String postingId) {
        Request request;
        String userId;
        result = null;
        try {
            commandString = "Select * from REQUESTS where POSTINGID='" +postingId +"'";
            rs5 = statement3.executeQuery(commandString);
            while (rs5.next()) {
                userId = rs5.getString("USERID");
                request = new Request(userId, postingId);
                requests.add(request);
            }
            rs5.close();
            result = "Success";
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public String insertRequest(Request request) {
        String values;
        result = null;

        try {
            values = "'" + request.getUserId()
                    +"', '" + request.getPostingId() + "'";
            commandString = "INSERT INTO REQUESTS VALUES(" + values + ")";
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String deleteRequest(Request request) {
        String uId;
        String pId;
        result = null;

        try {
            uId = request.getUserId();
            pId = request.getPostingId();
            commandString = "DELETE FROM REQUESTS WHERE USERID='" + uId + "' AND POSTINGID='" + pId +"'";
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getInterests( User u ) {
        Interest i;
        String s;
        result = null;

        try {
            commandString = "Select * from INTERESTS where USERID='" + u.getUserId() +"'";
            rs4 = statement3.executeQuery(commandString);
            while (rs4.next()) {
                s = rs4.getString("INTEREST");
                i = new Interest(s);
                u.addUniqueInterest(i);
            }
            rs4.close();
            result =  "Success";
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    private String updateInterests( User u ) {
        result = null;
        ArrayList<Interest> newInterests = u.getInterests();
        String values;

        try {
            values = u.getUserId();
            commandString = "Delete from INTERESTS where USERID='" +values +"'";

            updateCount = statement3.executeUpdate(commandString);

            result = checkWarnings(statement1, updateCount);

            commandString = "";
            for (Interest i : newInterests) {
                values = "'" + u.getUserId()
                        +"', '" + i.getInterest()
                        +"'";
                commandString += "Insert into INTERESTS " +" Values(" +values +") ";
            }
            System.out.println(commandString);
            updateCount = statement3.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public String checkWarnings(Statement currentStatement, int count) {
        String res = "Success";
        SQLWarning warning;

        try {
            if((warning = currentStatement.getWarnings()) != null) {
                res = warning.getMessage();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if(count != 1) {
            res = null;
        }
        return res;
    }
}//CLASS
