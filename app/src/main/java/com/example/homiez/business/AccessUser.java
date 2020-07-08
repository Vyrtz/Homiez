package com.example.homiez.business;

import com.example.homiez.application.Services;
import com.example.homiez.application.Main;
import com.example.homiez.objects.User;
import com.example.homiez.persistence.DataAccessStub;


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
