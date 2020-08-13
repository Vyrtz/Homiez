package comp3350.group6.homiez.business;


import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.Contact;
import comp3350.group6.homiez.objects.User;
import comp3350.group6.homiez.persistence.DataAccess;

public class AccessUser {

    private DataAccess dataAccess;

    public AccessUser()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    public QueryResult login(User u, String password) {
        return dataAccess.authenticateLogin(u, password);
    }

    public User getUser (String userId) {
        User u = new User(userId);
        return dataAccess.getUser(u);
    }

    public QueryResult insertUser(User currentUser, String password)
    {
        return dataAccess.insertUser(currentUser, password);
    }

    public QueryResult updateUser(User currentUser)
    {
        return dataAccess.updateUser(currentUser);
    }

    public QueryResult deleteUser(User currentUser)
    {
        return dataAccess.deleteUser(currentUser);
    }

    public Contact getContactInfoForUser(User currentUser) { return dataAccess.getContactInfo(currentUser); }

    public QueryResult updateContactInfoForUser(User currentUser, Contact info) { return dataAccess.updateContactInfo(currentUser, info); }
}
