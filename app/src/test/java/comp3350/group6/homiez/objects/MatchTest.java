package comp3350.group6.homiez.objects;

import junit.framework.TestCase;

public class MatchTest extends TestCase {

    Match match;
    Match matchEqual;

    public void setUp() {
        match = new Match("222", "999");
        matchEqual = new Match("222", "999");
    }

    public void testMatchCreation() {

        System.out.println("\nStarting testMatchCreation");

        assertNotNull(match);
        assertTrue("222".equals(match.getUserId()));
        assertTrue("999".equals(match.getPostingId()));
        assertTrue("user: 222 post: 999".equals(match.toString()));

        System.out.println("Finished testMatchCreation");
    }
    public void testMatchEquals() {
        System.out.println("\nStarting testMatchEquals");

        assertNotSame(match, matchEqual);
        assertTrue(match.equals(matchEqual));

        System.out.println("Finished testMatchEquals");
    }

    public void testMatchNotEquals() {
        System.out.println("\nStarting testMatchNotEquals");

        assertFalse(match.equals(new Match("1", "2")));
        assertFalse(match.equals(new Match("222", "2")));
        assertFalse(match.equals(new Match("1", "999")));

        System.out.println("Finished testMatchNotEquals");
    }

    public void testMatchEqualsNull() {
        System.out.println("\nStarting testMatchEqualsNull");

        assertFalse(match.equals(null));

        System.out.println("Finished testMatchEqualsNull");
    }

}
