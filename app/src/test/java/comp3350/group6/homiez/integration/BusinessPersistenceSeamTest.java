package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.business.AccessMatches;
import comp3350.group6.homiez.business.AccessRequests;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;

public class BusinessPersistenceSeamTest extends TestCase {

    final private String SUCCESS = "Success";

    public BusinessPersistenceSeamTest(String arg0) { super(arg0); }

    public void setUp() {
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);
    }

    public void testAccessMatches() {

        System.out.println("\nStarting integration test of AccessMatches to persistence");

        //getMatchesforUser

        //getMatchesForPosting

        //insertMatch

        //deleteMatch

        System.out.println("Finished integration test of AccessMatches to persistence");

    }

    public void testAccessPostings() {

        System.out.println("\nStarting integration test of AccessPostings to persistence");

        //getPostings

        //getPostingbyId

        //getPostingsbyUserID

        //insert

        //update

        //delete

        System.out.println("Finished integration test of AccessPostings to persistence");

    }

    public void testAccessRequests() {

        AccessRequests ar;
        Request request;
        String result;
        String postingID;

        System.out.println("\nStarting integration test of AccessRequests to persistence");

        ar = new AccessRequests();

        //Setup

        postingID = java.util.UUID.randomUUID().toString(); //Get a unique ID for the posting
        request = new Request("John115", postingID);

        //Insert
        result = ar.insertRequest(request);
        System.out.println("result");

        //Get

        //delete


        System.out.println("Finished integration test of AccessRequests to persistence");

    }

    public void testAccessUser() {

        AccessUser au;
        User user;
        String result;

        System.out.println("\nStarting integration test of AccessUser to persistence");

        au = new AccessUser();

        user = new User("John115", "John Smith", 30, "Male", 500.0, "I like long walks on the beach");

        //insertUser
        result = au.insertUser(user, "password123");
        assertEquals(result, SUCCESS);

        //login
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
