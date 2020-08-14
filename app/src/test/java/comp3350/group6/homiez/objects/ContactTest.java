package comp3350.group6.homiez.objects;

import junit.framework.TestCase;

public class ContactTest extends TestCase {

    Contact c;
    Contact matchEqual;

    public void setUp() {
        c = new Contact("PROGRAMMING");
        matchEqual = new Contact("programming");
    }

    public void testContactCreation() {
        System.out.println("\nStarting testContactCreation");
        assertNotNull(c);
        assertEquals("PROGRAMMING", c.getInfo());
        System.out.println("Finished testContactCreation");
    }

    public void testContactEquals() {
        System.out.println("\nStarting testContactEquals");

        assertNotSame(c, matchEqual);
        assertTrue(c.equals(matchEqual));

        System.out.println("Finished testContactEquals");
    }

    public void testContactNotEquals() {
        System.out.println("\nStarting testContactNotEquals");

        assertFalse(c.equals(new Contact("coding")));

        System.out.println("Finished testContactNotEquals");
    }

    public void testInterestEqualsNull() {
        System.out.println("\nStarting testContactEqualsNull");

        assertFalse(c.equals(null));

        System.out.println("Finished testContactEqualsNull");
    }
}
