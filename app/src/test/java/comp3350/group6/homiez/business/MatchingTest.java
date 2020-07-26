package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;

import junit.framework.TestCase;

import java.util.ArrayList;

public class MatchingTest extends TestCase {
    private Matching m;
    private User u;
    private AccessRequests accessRequests;
    private AccessPostings accessPostings;
    private AccessUser aUser;
    private AccessMatches accessMatches;

    public void setUp() {
        Main.startUp();
        m = new Matching();
        u = new User("0", "Abhi", 20, "m");
        accessRequests = new AccessRequests();
        aUser = new AccessUser();
        accessPostings = new AccessPostings();
    }


    //test operations when null parameters are passed
    public void testNullCases() {
        System.out.println("\nStarting testNullCases");
        setUp();

        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, null, "3"));
        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, u.getUserId(), null));

        assertNull(Matching.DeclineRequest(accessRequests, null, "3"));
        assertNull(Matching.DeclineRequest(accessRequests, u.getUserId(), null));

        assertNull(Matching.SendRequest(accessRequests, accessPostings, u.getUserId(), null));
        assertNull(Matching.SendRequest(accessRequests, accessPostings, null, "3"));

        Main.shutDown();
        System.out.println("Finished testNullCases");
    }

    //test operations when no result will be found
    public void testBadValues() {
        System.out.println("\nStarting testBadValues");
        setUp();

        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, "5", "3"));
        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, u.getUserId(), "9"));

        assertNull(Matching.DeclineRequest(accessRequests, "5", "3"));
        assertNull(Matching.DeclineRequest(accessRequests, u.getUserId(), "9"));

        assertNull(Matching.SendRequest(accessRequests, accessPostings, u.getUserId(), "9"));

        Main.shutDown();
        System.out.println("Finished testBadValues");
    }


    public void testSendRequest() {
        System.out.println("\nStarting testSendRequest");
        setUp();


        assertNotNull(Matching.SendRequest(accessRequests,accessPostings, u.getUserId(), "3"));

        //cannot send request to your own posting
        assertNull(Matching.SendRequest(accessRequests,accessPostings, u.getUserId(), "0"));

        //see if the request is sent
        ArrayList<Request> requests = new ArrayList<>();
        accessRequests.getRequestsForPosting(requests, "3");
        assertEquals(1, requests.size());
        requests.clear();

        Main.shutDown();
        System.out.println("Finished testSendRequest");
    }



    public void testAcceptRequest() {
        System.out.println("\nStarting testAcceptRequest");
        setUp();

        aUser.login(u);

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

        Main.shutDown();
        System.out.println("Finished testAcceptRequest");
    }
    public void testDeclineRequest() {
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
