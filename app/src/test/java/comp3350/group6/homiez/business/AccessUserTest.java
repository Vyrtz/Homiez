package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.User;

import junit.framework.TestCase;

public class AccessUserTest extends TestCase {

    private AccessUser aUser;
    private User u;
    private User existingUpdated;
    private User newUser;
    private User updateUser;
    private User uDNE;



    public AccessUserTest(String arg0) {
        super(arg0);

    }

    public void setUp() {
        Main.startUp();
        aUser = new AccessUser();
        aUser.login(u);
        u = new User("0", "Abhi", 20, "m");
        existingUpdated = new User("0", "John", 20, "m");
        newUser = new User("99", "testU1", 30, "f");
        updateUser = new User("99", "testChange", 30, "f");
        uDNE = new User("100", "testName", 50, "f");
    }

    //Make sure the instance exists
    public void testAccessUser1() {
        System.out.println("\nStarting testAccessUser1");

        assertNotNull(aUser);

        System.out.println("Finished testAccessUser1");
    }


    //postinglist_text operations with a user that is already in the "database"
    public void testAccessUserExistingUser() {
        System.out.println("\nStarting testAccessUserExistingUser");

        //Retrieve user
        assertEquals(u, aUser.getUser("0"));

        //insert user
        assertNull(aUser.insertUser(u));

        //updateUser
        aUser.updateUser(existingUpdated);
        assertTrue("John".equals(aUser.getUser("0").getName()));

        System.out.println("Finished testAccessUserExistingUser");
    }


    //postinglist_text operations with a user that is not in the "database"
    public void testAccessUserNotExisting() {
        System.out.println("\nStarting testAccessUserNotExisting");

        //Retrieve user
        assertNull(aUser.getUser("99"));

        //insert user
        aUser.insertUser(newUser);
        assertEquals(newUser, aUser.getUser("99"));

        //update user
        assertNull(aUser.updateUser(uDNE));

        System.out.println("Finished testAccessUserNotExisting");
    }
}
