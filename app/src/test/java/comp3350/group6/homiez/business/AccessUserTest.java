package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.objects.Contact;
import comp3350.group6.homiez.persistence.DataAccessStub;
import comp3350.group6.homiez.application.Services;
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
        Services.createDataAccess(new DataAccessStub("test"));
        aUser = new AccessUser();
        aUser.login(u, "test");
        u = new User("0", "Abhi", 20, "m", 100, "test");
        existingUpdated = new User("0", "John", 20, "m", 100, "test");
        newUser = new User("99", "testU1", 30, "f", 100, "test");
        updateUser = new User("99", "testChange", 30, "f", 100, "test");
        uDNE = new User("100", "testName", 50, "f", 100, "test");
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
//        assertNull(aUser.insertUser(u));

        //updateUser
        User uOrg = aUser.getUser("0");
        aUser.updateUser(existingUpdated);
        u = aUser.getUser("0");
        assertEquals("John",u.getName());
        aUser.updateUser(uOrg);

        System.out.println("Finished testAccessUserExistingUser");
    }


    //postinglist_text operations with a user that is not in the "database"
    public void testAccessUserNotExisting() {
        System.out.println("\nStarting testAccessUserNotExisting");

        //Retrieve user
        assertNull(aUser.getUser("99"));

        //insert user
        aUser.insertUser(newUser, "test");
        assertEquals(newUser, aUser.getUser("99"));

        //update user
        assertEquals(QueryResult.FAILURE, aUser.updateUser(uDNE));

        System.out.println("Finished testAccessUserNotExisting");
    }

    public void testAccessUserExistingContactInfo() {
        System.out.println("\nStarting testAccessUserExistingContactInfo");

        User uOrg = aUser.getUser("0");

        //get info
        Contact c = aUser.getContactInfoForUser(uOrg);
        assertEquals("Abhi contact", c.getInfo());

        //update info
        c = new Contact("Abhi contact updated");
        aUser.updateContactInfoForUser(uOrg, c);
        c = aUser.getContactInfoForUser(uOrg);
        assertEquals("Abhi contact updated", c.getInfo());

        System.out.println("Finished testAccessUserExistingContactInfo");
    }

    public void testAccessUserNotExistingContactInfo() {
        System.out.println("\nStarting testAccessUserNotExistingContactInfo");

        User uOrg = aUser.getUser("4");

        //get info
        Contact c = aUser.getContactInfoForUser(uOrg);
        assertNull(c);

        //update info
        c = new Contact("Ma info");
        aUser.updateContactInfoForUser(uOrg, c);
        c = aUser.getContactInfoForUser(uOrg);
        assertEquals("Ma info", c.getInfo());

        System.out.println("Finished testAccessUserNotExistingContactInfo");
    }

    public void testValidDelete() {
        System.out.println("\nStarting testValidDelete");
        aUser.insertUser(uDNE, "test");
        assertEquals(QueryResult.SUCCESS, aUser.deleteUser(uDNE));
        // warning because the user id might be correct but the delete was unsuccessful , as it does not exist in db
        assertNull(aUser.getUser(uDNE.getUserId()));
        System.out.println("Finished testValidDelete");

    }
    public void testInvalidDelete() {
        System.out.println("\nStarting testInValidDelete");
        assertEquals(QueryResult.FAILURE, aUser.deleteUser(null));
        // warning because the user id might be correct but the delete was unsuccessful , as it does not exist in db
        assertEquals(QueryResult.WARNING, aUser.deleteUser(new User("-1")));
        System.out.println("Finished testInValidDelete");

    }
}
