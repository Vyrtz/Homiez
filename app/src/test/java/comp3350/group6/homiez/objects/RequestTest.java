package comp3350.group6.homiez.objects;

import junit.framework.TestCase;

public class RequestTest extends TestCase {

    Request request;
    Request matchEqual;

    public void setUp() {
        request = new Request("111", "999");
        matchEqual = new Request("111", "999");
    }
    public void testRequestCreation() {

        System.out.println("\nStarting testRequest");

        assertNotNull(request);

        assertTrue("111".equals(request.getUserId()));
        assertTrue("999".equals(request.getPostingId()));

        System.out.println("Finished testMatch");
    }

    public void testRequestEquals() {
        System.out.println("\nStarting testRequestEquals");

        assertNotSame(request, matchEqual);
        assertTrue(request.equals(matchEqual));

        System.out.println("Finished testRequestEquals");
    }

    public void testRequestNotEquals() {
        System.out.println("\nStarting testRequestNotEquals");

        assertFalse(request.equals(new Request("1", "2")));
        assertFalse(request.equals(new Request("111", "2")));
        assertFalse(request.equals(new Request("1", "999")));

        System.out.println("Finished testRequestNotEquals");
    }

    public void testRequestEqualsNull() {
        System.out.println("\nStarting testRequestEqualsNull");

        assertFalse(request.equals(null));

        System.out.println("Finished testRequestEqualsNull");
    }
}
