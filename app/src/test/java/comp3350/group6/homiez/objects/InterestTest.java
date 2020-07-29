package comp3350.group6.homiez.objects;

import junit.framework.TestCase;

public class InterestTest extends TestCase {

    Interest i;
    Interest matchEqual;
    public void setUp() {
        i = new Interest("PROGRAMMING");
        matchEqual = new Interest("programming ");

    }
    public void testInterestCreation() {
        System.out.println("\nStarting testInterestCreation");
        assertNotNull(i);
        assertEquals("PROGRAMMING", i.getInterest());
        System.out.println("Finished testInterestCreation");
    }

    public void testInterestEquals() {
        System.out.println("\nStarting testInterestEquals");

        assertNotSame(i, matchEqual);
        assertTrue(i.equals(matchEqual));

        System.out.println("Finished testInterestEquals");
    }

    public void testInterestNotEquals() {
        System.out.println("\nStarting testInterestNotEquals");

        assertFalse(i.equals(new Interest("coding")));

        System.out.println("Finished testInterestNotEquals");
    }

    public void testInterestEqualsNull() {
        System.out.println("\nStarting testInterestEqualsNull");

        assertFalse(i.equals(null));

        System.out.println("Finished testInterestEqualsNull");
    }
}
