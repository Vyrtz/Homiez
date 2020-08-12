package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import java.sql.SQLException;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.business.AccessMatches;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.User;

public class BusinessPersistenceSeamTest extends TestCase {

    final private String SUCCESS = "Success";

    public BusinessPersistenceSeamTest(String arg0) { super(arg0); }

    public void testAccessMatches() {

    }

    public void testAccessPostings() {

    }

    public void testAccessRequests() {

    }

    public void testAccessUser() {

        AccessUser au;
        User user;
        String result;

        Services.closeDataAccess();

        System.out.println("\nStarting integration test of AccessUser to persistence");

        Services.createDataAccess(Main.dbName);

        au = new AccessUser();

        user = new User("John115", "John Smith", 30, "Male", 500.0, "I like long walks on the beach");

        //Test insertUser
        result = au.insertUser(user, "password123");
        assertEquals(result, SUCCESS);

        //Test login
        result = au.login(user, "password123");
        assertEquals(result, SUCCESS);

        //Update
        user.setName("Johnny Smith");
        result = au.updateUser(user);
        assertEquals(result, SUCCESS);

        //GetUser
        assertEquals(user, au.getUser("John115"));


        System.out.println("Finished integration test of AccessUser to persistence");

    }
}
