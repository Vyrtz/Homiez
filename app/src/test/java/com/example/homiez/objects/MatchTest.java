package com.example.homiez.objects;

import junit.framework.TestCase;

public class MatchTest extends TestCase {

    public MatchTest(String arg0) { super(arg0); }

    public void testMatch(){

        System.out.println("\nStarting testMatch");

        Match match = new Match("111", "999");
        Match matchEqual = new Match("111", "999");


        assertNotNull(match);
        assertTrue("111".equals(match.getUserId()));
        assertTrue("999".equals(match.getPostingId()));
        assertTrue("user: 111 post: 999".equals(match.toString()));
        assertNotSame(match, matchEqual);
        assertTrue(match.equals(matchEqual));

        System.out.println("Finished testMatch");
    }

}
