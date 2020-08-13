package comp3350.group6.homiez.persistence;

import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.objects.Contact;
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
    private QueryResult result;

    private Statement statement1, statement2, statement3, statement4;
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
            statement4 = connection.createStatement();
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

    private QueryResult insertUser(User user) {
        result = QueryResult.FAILURE;
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
            insertInterests(user);

            
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public QueryResult insertUser(User u, String password) {
        String values;
        result = QueryResult.FAILURE;

        try {
            result = insertUser(u);
            if (result == QueryResult.SUCCESS) {
                values = "'" + u.getUserId()
                        + "','" + password + "'";
                commandString = "INSERT INTO LOGININFO VALUES(" + values + ")";
                updateCount = statement1.executeUpdate(commandString);
                result = checkWarnings(statement1, updateCount);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public QueryResult authenticateLogin(User u, String password) {
        result = QueryResult.FAILURE;

        try {
            commandString = "Select * from LOGININFO where USERID='"+ u.getUserId() +"' and PASSWORD ='" + password+ "'";
            rs3 = statement2.executeQuery(commandString);

            while(rs3.next()) {
                result = QueryResult.SUCCESS;
            }
            rs3.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public QueryResult updateUser(User user) {
        result = QueryResult.FAILURE;
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
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);

            updateInterests( user);

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    //POSTING STUFF
    public QueryResult getAllDisplayPostings(List<Posting> postingsList, User user) {
        Posting p;
        result = QueryResult.FAILURE;
        try {
            commandString = "Select * from POSTINGS where USERID!='"+user.getUserId()+"'";
            rs3 = statement2.executeQuery(commandString);

            while(rs3.next()) {
                p = constructPosting(rs3);
                postingsList.add(p);
            }
            rs3.close();
            result = QueryResult.SUCCESS;
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

    public QueryResult getPostingsByUser(List<Posting> postingsList, User user) {
        Posting p;
        result = QueryResult.FAILURE;
        try {
            commandString = "Select * from POSTINGS where USERID='"+user.getUserId()+"'";
            rs3 = statement2.executeQuery(commandString);

            while(rs3.next()) {
                p = constructPosting(rs3);
                postingsList.add(p);
            }
            rs3.close();
            result = QueryResult.SUCCESS;
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
        getAttachedUsers(p);
        return p;
    }

    public QueryResult insertPosting(Posting posting) {
        String values;
        result = QueryResult.FAILURE;

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
            insertAttachedUsers(posting);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public QueryResult deletePosting(Posting posting) {
        String values;
        result = QueryResult.FAILURE;

        try {
            values = posting.getPostingId();
            commandString = "DELETE FROM REQUESTS WHERE POSTINGID='" + values +"'";
            statement1.executeUpdate(commandString);
            commandString = "DELETE FROM MATCHES WHERE POSTINGID='" + values +"'";
            statement1.executeUpdate(commandString);
            deleteAttachedUsers(posting);
            commandString = "DELETE FROM POSTINGS WHERE POSTINGID='" + values +"'";
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public QueryResult deleteUser(User u) {
        String values;
        result = QueryResult.FAILURE;

        try {
            values = u.getUserId();
            deleteContactInfo(u);
            commandString = "DELETE FROM INTERESTS WHERE USERID='" + values +"'";
            statement1.executeUpdate(commandString);
            commandString = "DELETE FROM LOGININFO WHERE USERID='" + values +"'";
            statement1.executeUpdate(commandString);
            commandString = "DELETE FROM REQUESTS WHERE USERID='" + values +"'";
            statement1.executeUpdate(commandString);
            commandString = "DELETE FROM MATCHES WHERE USERID='" + values +"'";
            statement1.executeUpdate(commandString);
            commandString = "DELETE FROM CONTACTS WHERE USERID='" + values +"'";
            statement1.executeUpdate(commandString);
            List<Posting> ps = new ArrayList<>();
            getPostingsByUser(ps, u);
            for (Posting p : ps) {
                deletePosting(p);
            }
            commandString = "DELETE FROM USERS WHERE USERID='" + values +"'";
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
            result = QueryResult.FAILURE;
        }

        return result;
    }

    public QueryResult updatePosting(Posting p) {
        String values;
        String where;
        result = QueryResult.FAILURE;

        try {
            values = "TITLE='" + p.getTitle()
                    +"', USERID='" + p.getUser().getUserId()
                    +"', PRICE='" + p.getPrice()
                    +"', LOCATION='" + p.getLocation()
                    +"', TYPE='" + p.getType()
                    +"', DESCRIPTION='" + p.getDescription() + "'";

            where = "WHERE POSTINGID='" + p.getPostingId() + "'";

            commandString = "UPDATE POSTINGS " + " SET " + values + " " + where;
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
            updateAttachedUsers(p);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //MATCH STUFF
    public QueryResult getMatchesForUser(List<Match> matchList, String userId) {
        Match match;
        String postingId;
        result = QueryResult.FAILURE;
        try {
            checkNullValues(matchList, userId);
            commandString = "Select * from MATCHES where USERID='" +userId +"'";
            rs5 = statement3.executeQuery(commandString);
            // ResultSetMetaData md5 = rs5.getMetaData();
            while (rs5.next()) {
                postingId = rs5.getString("POSTINGID");
                match = new Match(userId, postingId);
                matchList.add(match);
            }
            rs5.close();
            result = QueryResult.SUCCESS;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public QueryResult getMatchesForPosting(List<Match> matchList, String postingId) {
        Match match;
        String userId;
        result = QueryResult.FAILURE;
        try {
            checkNullValues(matchList, postingId);
            commandString = "Select * from MATCHES where POSTINGID='" +postingId +"'";
            rs5 = statement3.executeQuery(commandString);
            while (rs5.next()) {
                userId = rs5.getString("USERID");
                match = new Match(userId, postingId);
                matchList.add(match);
            }
            rs5.close();
            result = QueryResult.SUCCESS;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public QueryResult insertMatch(Match m) {
        String values;
        result = QueryResult.FAILURE;

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
    public QueryResult deleteMatch(Match m) {
        String uId;
        String pId;
        result = QueryResult.FAILURE;

        try {
            uId = m.getUserId();
            pId = m.getPostingId();
            commandString = "DELETE FROM MATCHES WHERE USERID='" + uId + "' AND POSTINGID='" + pId +"'";
            updateCount = statement1.executeUpdate(commandString);
            result = checkWarnings(statement1, updateCount);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //REQUEST STUFF
    public QueryResult getRequests(List<Request> requests, String postingId) {
        Request request;
        String userId;
        result = QueryResult.FAILURE;
        try {
            checkNullValues(requests, postingId);
            commandString = "Select * from REQUESTS where POSTINGID='" +postingId +"'";
            rs5 = statement3.executeQuery(commandString);
            while (rs5.next()) {
                userId = rs5.getString("USERID");
                request = new Request(userId, postingId);
                requests.add(request);
            }
            rs5.close();
            result = QueryResult.SUCCESS;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    public QueryResult insertRequest(Request request) {
        String values;
        result = QueryResult.FAILURE;

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

    public QueryResult deleteRequest(Request request) {
        String uId;
        String pId;
        result = QueryResult.FAILURE;

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

    private QueryResult getInterests(User u ) {
        Interest i;
        String s;
        QueryResult result = QueryResult.FAILURE;

        try {
            commandString = "Select * from INTERESTS where USERID='" + u.getUserId() +"'";
            rs4 = statement3.executeQuery(commandString);
            while (rs4.next()) {
                s = rs4.getString("INTEREST");
                i = new Interest(s);
                u.addUniqueInterest(i);
            }
            rs4.close();
            result =  QueryResult.SUCCESS;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    private QueryResult updateInterests(User u ) throws SQLException {
        QueryResult result;
        String values = u.getUserId();
        
        commandString = "Delete from INTERESTS where USERID='" +values +"'";

        updateCount = statement3.executeUpdate(commandString);

        result = checkWarnings(statement3, updateCount);

        insertInterests(u);

        return result;
    }

    private QueryResult insertInterests(User u ) throws SQLException {
        String values;
        QueryResult result;
        commandString = "";

        for (Interest i : u.getInterests()) {
            values = "'" + u.getUserId()
                    +"', '" + i.getInterest()
                    +"'";
            commandString += "Insert into INTERESTS " +"Values(" +values +") ";
        }
        updateCount = statement3.executeUpdate(commandString);
        result = checkWarnings(statement3, updateCount);
        return result;
    }

    private QueryResult insertAttachedUsers(Posting p ) throws SQLException {
        QueryResult result;
        String values;
        commandString = "";
        for (User u : p.getAttachedUsers()) {
            values = "'" + p.getPostingId()
                    +"', '" + u.getUserId()
                    +"'";
            commandString = "Insert into ATTACHEDUSERS " +"Values(" +values +") ";
            updateCount = statement4.executeUpdate(commandString);
        }
        result = checkWarnings(statement4, updateCount);
        return result;
    }

    private QueryResult updateAttachedUsers(Posting p ) throws SQLException {
        QueryResult result;
        result = deleteAttachedUsers(p);
        if (result != QueryResult.FAILURE) {
            result = insertAttachedUsers(p);
        }
        return result;
    }
    private QueryResult getAttachedUsers(Posting p ) throws SQLException {
        QueryResult result;;
        String uid;
        commandString = "Select * from ATTACHEDUSERS where POSTINGID='" + p.getPostingId() +"' and USERID != '" +p.getUser().getUserId()+"'";
        rs5 = statement4.executeQuery(commandString);
        while (rs5.next()) {
            uid = rs5.getString("USERID");
            User u = getUser(new User(uid));
            p.addAttachedUser(u);
        }
        rs5.close();
        result =  QueryResult.SUCCESS;

        return result;
    }

    private QueryResult deleteAttachedUsers(Posting p) throws SQLException{
        commandString = "DELETE FROM ATTACHEDUSERS WHERE POSTINGID='" + p.getPostingId() + "'";
        updateCount = statement3.executeUpdate(commandString);
        QueryResult result = checkWarnings(statement3, updateCount);
        return result;
    }

    public QueryResult checkWarnings(Statement currentStatement, int count) {
        QueryResult res = QueryResult.SUCCESS;
        SQLWarning warning;

        try {
            if((warning = currentStatement.getWarnings()) != null) {
                System.out.println(warning.getMessage());
                res = QueryResult.WARNING;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            res = QueryResult.FAILURE;
        }

        if(count == 0) {
            res = QueryResult.WARNING;
        }

        return res;
    }

    private void checkNullValues (List something, String id) throws NullPointerException{
        if (something == null || id == null ) {
            throw new NullPointerException();
        }
    }

    public Contact getContactInfo(User u ) {
        Contact c = null;
        String s;

        try {
            commandString = "Select * from CONTACTS where USERID='" + u.getUserId() +"'";
            rs4 = statement3.executeQuery(commandString);
            while (rs4.next()) {
                s = rs4.getString("INFO");
                c = new Contact(s);
            }
            rs4.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return c;
    }

    public QueryResult updateContactInfo(User u, Contact c ) {
        QueryResult result = QueryResult.FAILURE;

        try {
            deleteContactInfo(u);
            result = insertContactInfo(u, c);
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

    private QueryResult insertContactInfo(User u, Contact c ) throws SQLException {
        QueryResult result;

        String values = "'" + u.getUserId() + "', '" + c.getInfo() + "'";

        commandString = "Insert into CONTACTS " + "Values(" + values + ") ";

        updateCount = statement3.executeUpdate(commandString);

        result = checkWarnings(statement3, updateCount);

        return result;
    }

    private QueryResult deleteContactInfo(User u ) throws SQLException {
        QueryResult result;

        String values = u.getUserId();

        commandString = "Delete from CONTACTS where USERID='" + values +"'";

        updateCount = statement1.executeUpdate(commandString);

        result = checkWarnings(statement1, updateCount);

        return result;
    }

}//CLASS
