package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.User;
import comp3350.group6.homiez.persistence.DataAccessStub;


public class AccessUser {

    private DataAccessStub dataAccess;
    private User loggedInUser;

    public AccessUser()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    public void login(User u){
        loggedInUser = u;
    }

    public User getUser (String userId)
    {
        User u = new User(userId);
        return dataAccess.getUser(u);
    }

    public String insertUser(User currentUser)
    {
        return dataAccess.insertUser(currentUser);
    }

    public String updateUser(User currentUser)
    {
        return dataAccess.updateUser(currentUser);
    }
}
