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

    public void setUp() {
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);
        au = new AccessUser();
        newUser = new User("John115", "John Smith", 30, "Male", 500.0, "I like long walks on the beach");
        existingUser = new User("5", "Linda", 30, "F", 3000, "DESC LINDA");
        password = "dev";
    }

    public void testValidLogin() {
        System.out.println("Starting testValidLogin");
        //Existing User
        assertEquals(QueryResult.SUCCESS, au.login(existingUser, password));

        //New User
        au.insertUser(newUser, password);
        assertEquals(QueryResult.SUCCESS, au.login(newUser, password));
        au.deleteUser(newUser);

        System.out.println("Finished testValidLogin");
    }

    public void testInvalidLogin() {
        System.out.println("Starting testInvalidLogin");
        //User that doesn't exist
        assertEquals(QueryResult.FAILURE, au.login(newUser, password));

        //Wrong password
        assertEquals(QueryResult.FAILURE, au.login(existingUser, "wrong password"));

        System.out.println("Finished testInvalidLogin");
    }

    public void testValidGet() {
        System.out.println("Starting testValidGet");
        //Existing User
        assertEquals(existingUser, au.getUser("5"));

        //New User
        au.insertUser(newUser, password);
        assertEquals(au.getUser("John115"), newUser);
        au.deleteUser(newUser);

        System.out.println("Finished testValidGet");
    }

    public void testInvalidGet() {
        System.out.println("Starting testInvalidGet");
        assertNull(au.getUser("DNE"));  //Getting a user that doesn't exist
        System.out.println("Finished testInvalidGet");
    }

    public void testValidInsert() {
        System.out.println("Starting testValidInsert");

        assertEquals(QueryResult.SUCCESS, au.insertUser(newUser, password));
        User user1 = au.getUser("John115");
        assertNotNull(user1);

        au.deleteUser(newUser);
        assertNull(au.getUser("John115"));

        System.out.println("Finished testValidInsert");
    }

    public void testInvalidInsert() {
        System.out.println("Starting testInvalidInsert");
        assertEquals(QueryResult.FAILURE, au.insertUser(existingUser, password)); //inserting a user that exists already
        System.out.println("Finished testInvalidInsert");
    }

    public void testValidUpdate() {
        System.out.println("Starting testValidUpdate");
        //Existing
        User temp = au.getUser("5");
        assertEquals(QueryResult.SUCCESS, au.updateUser(temp));

        //New User
        au.insertUser(newUser, password);
        assertEquals(QueryResult.SUCCESS, au.updateUser(newUser));
        au.deleteUser(newUser);

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
        User temp = au.getUser("5");
        assertEquals(QueryResult.SUCCESS, au.deleteUser(existingUser));
        au.insertUser(temp, password);

        //New User
        au.insertUser(newUser, password);
        assertEquals(QueryResult.SUCCESS, au.deleteUser(newUser));

        System.out.println("Finished testValidDelete");
    }

    public void testInvalidDelete() {
        System.out.println("Starting testInvalidDelete");
        assertEquals(QueryResult.WARNING ,au.deleteUser(newUser)); //Deleting a nonexistant user
        System.out.println("Finished testInvalidDelete");
    }
}
