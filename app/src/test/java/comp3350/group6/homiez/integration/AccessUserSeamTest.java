package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.business.AccessUser;
import comp3350.group6.homiez.objects.User;

public class AccessUserSeamTest extends TestCase {

    private AccessUser au;
    private User newUser;
    private User existingUser;
    private String password;

    final private String SUCCESS = "Success";

    public void setUp() {
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);
        au = new AccessUser();
        newUser = new User("John115", "John Smith", 30, "Male", 500.0, "I like long walks on the beach");
        existingUser = new User("0", "Abhi", 20, "M", 1500, "DESC Abhi");
        password = "dev";
    }

    public void testValidLogin() {
        System.out.println("Starting testValidLogin");
        //Existing User
        assertEquals(QueryResult.SUCCESS, au.login(existingUser, password));

        //New User
        au.insertUser(newUser, password);
        assertEquals(QueryResult.SUCCESS, au.login(newUser, password));
        //TODO: Remove the user after we insert it

        System.out.println("Finished testValidLogin");
    }

    public void testInvalidLogin() {
        System.out.println("Starting testInvalidLogin");
        //User that doesn't exist
        assertEquals(au.login(newUser, password), QueryResult.FAILURE);

        //Wrong password
        assertEquals(au.login(existingUser, "wrong password"), QueryResult.FAILURE);

        System.out.println("Finished testInvalidLogin");
    }

    public void testValidGet() {
        System.out.println("Starting testValidGet");
        //Existing User
        assertEquals(au.getUser("0"), existingUser);

        //New User
        au.insertUser(newUser, password);
        assertEquals(au.getUser("0"), newUser);
        //TODO: Remove the user that was inserted

        System.out.println("Finished testValidGet");
    }

    public void testInvalidGet() {
        System.out.println("Starting testInvalidGet");
        assertEquals(au.getUser("DNE"), QueryResult.FAILURE);  //Getting a user that doesn't exist
        System.out.println("Finished testInvalidGet");
    }

    public void testValidInsert() {
        System.out.println("Starting testValidInsert");
        assertEquals(QueryResult.SUCCESS, au.insertUser(newUser, password));

        //TODO: Cleanup and remove the inserted user
        System.out.println("Finished testValidInsert");
    }

    public void testInvalidInsert() {
        System.out.println("Starting testInvalidInsert");
        assertEquals(au.insertUser(existingUser, password), QueryResult.FAILURE); //inserting a user that exists already
        System.out.println("Finished testInvalidInsert");
    }

    public void testValidUpdate() {
        System.out.println("Starting testValidUpdate");
        //Existing
        assertEquals(QueryResult.SUCCESS, au.updateUser(existingUser));

        //New User
        au.insertUser(newUser, password);
        assertEquals(QueryResult.SUCCESS, au.updateUser(newUser));
        //TODO: Remove the inserted user

        System.out.println("Finished testValidUpdate");
    }

    public void testInvalidUpdate() {
        System.out.println("Starting testInvalidUpdate");
        assertEquals(QueryResult.FAILURE, au.updateUser(newUser)); //Updating a nonexistant user
        System.out.println("Finished testInvalidUpdate");
    }

    public void testValidDelete() {
        System.out.println("Starting testValidDelete");
        //Existing user
        assertEquals(QueryResult.SUCCESS, au.deleteUser(existingUser));
        assertNull( au.getUser("0"));
        //New User
        //au.insertUser(newUser, password);
        //assertEquals(QueryResult.SUCCESS, au.deleteUser(newUser));

        System.out.println("Finished testValidDelete");
    }

    public void testInvalidDelete() {
        System.out.println("Starting testInvalidDelete");
        //assertNull(au.deleteUser(newUser)); //Deleting a nonexistant user
        System.out.println("Finished testInvalidDelete");
    }
}
