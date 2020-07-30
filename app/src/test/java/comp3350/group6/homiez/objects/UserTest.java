package comp3350.group6.homiez.objects;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    User u;
    User matchEqual;
    public void setUp() {
        u = new User("1", "John McNamara", 23, "M", 100, "test");
        matchEqual = new User("1");

    }
    public void testUserCreation() {
        System.out.println("\nStarting testUserCreation");
        assertNotNull(u);
        assertEquals("1", u.getUserId());
        assertEquals("John McNamara", u.getName());
        assertEquals(23, u.getAge());
        assertEquals("M" , u.getGender());
        assertEquals(0, u.getInterests().size());

        u.addUniqueInterest(new Interest("boxing"));
        assertEquals(1, u.getInterests().size());
        System.out.println("Finished testUserCreation");
    }

    public void testUserEquals() {
        System.out.println("\nStarting testUserEquals");

        assertNotSame(u, matchEqual);
        assertTrue(u.equals(matchEqual));

        System.out.println("Finished testUserEquals");
    }

    public void testUserNotEquals() {
        System.out.println("\nStarting testUserNotEquals");

        assertFalse(u.equals(new User("2")));

        System.out.println("Finished testUserNotEquals");
    }

    public void testUserEqualsNull() {
        System.out.println("\nStarting testUserEqualsNull");

        assertFalse(u.equals(null));

        System.out.println("Finished testUserEqualsNull");
    }
}
