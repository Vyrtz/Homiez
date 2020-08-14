package comp3350.group6.homiez.business;


import comp3350.group6.homiez.application.Shared;
import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.Contact;
import comp3350.group6.homiez.objects.User;
import comp3350.group6.homiez.persistence.DataAccess;

import static comp3350.group6.homiez.application.Shared.isNotNull;
import static comp3350.group6.homiez.application.Shared.isNotNullOrBlank;

public class AccessUser {

    private DataAccess dataAccess;

    public AccessUser()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
    }

    public QueryResult login(User u, String password) {
        if(isNotNullOrBlank(password) && isNotNull(u)) {
            return dataAccess.authenticateLogin(u, password);
        }
        return QueryResult.FAILURE;
    }

    public User getUser (String userId) {
        if(isNotNullOrBlank(userId)) {
            User u = new User(userId);
            return dataAccess.getUser(u);
        }
        return null;
    }

    public QueryResult insertUser(User currentUser, String password) {
        if(isNotNullOrBlank(password) && isNotNull(currentUser)) {
            return dataAccess.insertUser(currentUser, password);
        }
        return QueryResult.FAILURE;
    }

    public QueryResult updateUser(User currentUser) {
        if (isNotNull(currentUser)) {
            return dataAccess.updateUser(currentUser);
        }
        return QueryResult.FAILURE;
    }

    public QueryResult deleteUser(User currentUser) {
        if (isNotNull(currentUser)) {
            return dataAccess.deleteUser(currentUser);
        }
        return QueryResult.FAILURE;
    }

    public Contact getContactInfoForUser(User currentUser) {
        if (isNotNull(currentUser)) {
            return dataAccess.getContactInfo(currentUser);
        }
        return null;
    }

    public QueryResult updateContactInfoForUser(User currentUser, Contact info) {
        if (isNotNull(currentUser) && isNotNull(info)) {
            return dataAccess.updateContactInfo(currentUser, info);
        }
        return QueryResult.FAILURE;
    }
}
