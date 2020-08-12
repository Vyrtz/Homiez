package comp3350.group6.homiez.business;

import java.sql.SQLException;

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

    public String login(User u, String password) {
        return dataAccess.authenticateLogin(u, password);
    }

    public User getUser (String userId) {
        User u = new User(userId);
        return dataAccess.getUser(u);
    }

    public String insertUser(User currentUser, String password)
    {
        return dataAccess.insertUser(currentUser, password);
    }

    public String updateUser(User currentUser)
    {
        return dataAccess.updateUser(currentUser);
    }

    public String getContactInfoForUser(User currentUser, Contact info) { return dataAccess.getContactInfo(currentUser, info); }

    public String updateContactInfoForUser(User currentUser, Contact info) { return dataAccess.updateContactInfo(currentUser, info); }
}
