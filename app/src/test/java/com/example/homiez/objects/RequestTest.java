package com.example.homiez.objects;

import junit.framework.TestCase;

public class RequestTest extends TestCase {

    public RequestTest(String arg0) { super(arg0); }

    public void testRequest(){

        System.out.println("\nStarting testRequest");

        Request request = new Request("111", "999");
        Request matchEqual = new Request("111", "999");


        assertNotNull(request);
        assertTrue("111".equals(request.getUserId()));
        assertTrue("999".equals(request.getPostingId()));
        assertNotSame(request, matchEqual);
        assertTrue(request.equals(matchEqual));

        System.out.println("Finished testMatch");
    }
}
