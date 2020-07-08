package com.example.homiez.business;

import com.example.homiez.application.Main;
import com.example.homiez.objects.Match;
import com.example.homiez.objects.Posting;
import com.example.homiez.objects.Request;
import com.example.homiez.objects.User;

import junit.framework.TestCase;

import java.util.ArrayList;

public class MatchingTest extends TestCase {
    public void setUp()
    {
        Main.startUp();
    }
    public void testSendRequest()
    {
        System.out.println("\nStarting testSendRequest");


        User u = new User("0", "Abhi", 20, "m");
        AccessRequests accessRequests = new AccessRequests();
        AccessPostings accessPostings = new AccessPostings();
        assertNotNull(Matching.SendRequest(accessRequests,accessPostings, u.getUserId(), "3"));

        //cannot send request to your own posting
        assertNull(Matching.SendRequest(accessRequests,accessPostings, u.getUserId(), "0"));

        //see if the request is sent
        ArrayList<Request> requests = new ArrayList<>();
        accessRequests.getRequestsForPosting(requests, "3");
        assertEquals(1, requests.size());
        requests.clear();
        System.out.println("Finished testSendRequest");
    }

    public void testAcceptRequest()
    {
        System.out.println("\nStarting testAcceptRequest");

        User u = new User("0", "Abhi", 20, "m");
        AccessUser aUser = new AccessUser();
        AccessRequests accessRequests = new AccessRequests();
        aUser.login(u);
        AccessPostings accessPostings = new AccessPostings();
        Matching.SendRequest(accessRequests,accessPostings, u.getUserId(), "3");

        AccessMatches accessMatches = new AccessMatches();
        assertNotNull(Matching.AcceptRequest(accessRequests,accessMatches, u.getUserId(), "3"));

        //cannot accept again as the request should be deleted
        assertNull(Matching.AcceptRequest(accessRequests,accessMatches, u.getUserId(), "3"));

        //already a match present in db
        assertNull(Matching.AcceptRequest(accessRequests,accessMatches, "4", "3"));
        //see if the match is sent
        ArrayList<Match> matches = new ArrayList<>();
        accessMatches.getMatchesForPosting(matches, "3");
        assertEquals(2, matches.size());
        matches.clear();
        System.out.println("Finished testAcceptRequest");
    }
    public void testDeclineRequest()
    {
        System.out.println("\nStarting testDeclineRequest");

        User u = new User("0", "Abhi", 20, "m");
        AccessUser aUser = new AccessUser();
        AccessRequests accessRequests = new AccessRequests();
        aUser.login(u);
        AccessPostings accessPostings = new AccessPostings();
        Matching.SendRequest(accessRequests,accessPostings, u.getUserId(), "3");

        assertEquals("Success", Matching.DeclineRequest(accessRequests, u.getUserId(), "3"));

        AccessMatches accessMatches = new AccessMatches();
        //cannot accept again as the request should be deleted
        assertNull(Matching.AcceptRequest(accessRequests,accessMatches, u.getUserId(), "3"));
        //cannot decline again as the request should be deleted
        assertNull(Matching.DeclineRequest(accessRequests, u.getUserId(), "3"));

        //see if the match is not made
        ArrayList<Match> matches = new ArrayList<>();
        accessMatches.getMatchesForPosting(matches, "3");
        assertEquals(1, matches.size());
        matches.clear();
        System.out.println("Finished testAcceptRequest");
    }
    public void tearDown()
    {
        Main.shutDown();
    }
}
