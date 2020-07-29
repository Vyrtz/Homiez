package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;

import junit.framework.TestCase;

import java.util.ArrayList;

public class MatchingTest extends TestCase {
    private User u;
    private AccessRequests accessRequests;
    private AccessPostings accessPostings;
    private AccessUser aUser;
    private AccessMatches accessMatches;
    private Posting p;

    public void setUp() {
        Main.startUp();
        u = new User("0", "Abhi", 20, "m");
        accessRequests = new AccessRequests();
        aUser = new AccessUser();
        accessPostings = new AccessPostings();
        accessMatches = new AccessMatches();
        p = new Posting("test", "test", new User("test"), 2, "test", "test", "test");
        accessPostings.insertPosting(p);
    }


    //test operations when null parameters are passed
    public void testNullCasesOnSendRequest() {
        System.out.println("\nStarting testNullCasesOnSendRequest");

        assertNull(Matching.SendRequest(accessRequests, accessPostings, accessMatches, u.getUserId(), null));
        assertNull(Matching.SendRequest(accessRequests, accessPostings, accessMatches, null, "3"));
        assertNull(Matching.SendRequest(accessRequests, null, accessMatches, u.getUserId(), "3"));
        assertNull(Matching.SendRequest(null, accessPostings, accessMatches, u.getUserId(), "3"));

        System.out.println("Finished testNullCasesOnSendRequest");
    }
    //test operations when null parameters are passed
    public void testNullCasesOnAcceptRequest() {
        System.out.println("\nStarting testNullCasesOnAcceptRequest");

        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, null, "3"));
        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, u.getUserId(), null));
        assertNull(Matching.AcceptRequest(null, accessMatches, u.getUserId(), "3"));
        assertNull(Matching.AcceptRequest(accessRequests, null, u.getUserId(), "3"));

        System.out.println("Finished testNullCasesOnAcceptRequest");
    }

    //test operations when null parameters are passed
    public void testNullCasesOnDeclineRequest() {
        System.out.println("\nStarting testNullCasesOnDeclineRequest");

        assertNull(Matching.DeclineRequest(accessRequests, null, "3"));
        assertNull(Matching.DeclineRequest(accessRequests, u.getUserId(), null));
        assertNull(Matching.DeclineRequest(null, u.getUserId(), "3"));

        System.out.println("Finished testNullCasesOnDeclineRequest");
    }


    //test operations when no result will be found
    public void testBadValuesForSendRequest() {
        System.out.println("\nStarting testBadValuesForSendRequest");

        assertNull(Matching.SendRequest(accessRequests, accessPostings, accessMatches, u.getUserId(), "100000"));

        System.out.println("Finished testBadValuesForSendRequest");
    }

    public void testBadValuesForAcceptRequest() {
        System.out.println("\nStarting testBadValuesForAcceptRequest");

        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, "100000", "3"));
        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, u.getUserId(), "100000"));

        System.out.println("Finished testBadValuesForAcceptRequest");
    }

    public void testBadValuesForDeclineRequest() {
        System.out.println("\nStarting testBadValuesForDeclineRequest");

        assertNull(Matching.DeclineRequest(accessRequests, "10000", "-15"));
        assertNull(Matching.DeclineRequest(accessRequests, u.getUserId(), "1000000"));

        System.out.println("Finished testBadValuesForDeclineRequest");
    }


    public void testSendRequestOnOwnPosting() {
        System.out.println("\nStarting testSendRequestOnOwnPosting");

        Posting p2 = new Posting("test2", "test",u,2,"test","test","test");
        accessPostings.insertPosting(p2);

        assertNull(Matching.SendRequest(accessRequests,accessPostings, accessMatches, u.getUserId(), p2.getPostingId()));

        System.out.println("Finished testSendRequestOnOwnPosting");
    }

    public void testSendRequestOnValidPosting() {
        System.out.println("\nStarting testSendRequestOnValidPosting");

        assertEquals("Success", Matching.SendRequest(accessRequests,accessPostings, accessMatches, u.getUserId(), p.getPostingId()));

        ArrayList<Request> requests = new ArrayList<>();
        accessRequests.getRequestsForPosting(requests, p.getPostingId());
        assertEquals(1, requests.size());
        requests.clear();

        System.out.println("Finished testSendRequestOnValidPosting");
    }

    public void testSendRequestOnMatchedPosting() {
        System.out.println("\nStarting testSendRequestOnMatchedPosting");

        assertEquals("Success", Matching.SendRequest(accessRequests,accessPostings, accessMatches, u.getUserId(), p.getPostingId()));
        assertEquals("Success", Matching.AcceptRequest(accessRequests, accessMatches, u.getUserId(), p.getPostingId()));

        assertNull(Matching.SendRequest(accessRequests,accessPostings, accessMatches, u.getUserId(), p.getPostingId()));

        System.out.println("Finished testSendRequestOnValidPosting");
    }

    public void testAcceptRequestValid() {
        System.out.println("\nStarting testAcceptRequestValid");
        Matching.SendRequest(accessRequests,accessPostings, accessMatches, u.getUserId(), p.getPostingId());

        assertEquals("Success", Matching.AcceptRequest(accessRequests, accessMatches, u.getUserId(), p.getPostingId()));

        ArrayList<Match> matches = new ArrayList<>();
        accessMatches.getMatchesForPosting(matches, p.getPostingId());
        assertEquals(1, matches.size());
        matches.clear();

        System.out.println("Finished testAcceptRequestValid");
    }

    public void testAcceptRequestTwiceIsInvalid() {
        System.out.println("\nStarting testAcceptRequestTwiceIsInvalid");

        assertEquals("Success", Matching.SendRequest(accessRequests,accessPostings, accessMatches, u.getUserId(), p.getPostingId()));
        assertEquals("Success", Matching.AcceptRequest(accessRequests, accessMatches, u.getUserId(), p.getPostingId()));

        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, u.getUserId(), p.getPostingId()));

        //see if the match is sent
        ArrayList<Match> matches = new ArrayList<>();
        accessMatches.getMatchesForPosting(matches, p.getPostingId());
        assertEquals(1, matches.size());
        matches.clear();

        System.out.println("Finished testAcceptRequestTwiceIsInvalid");
    }

    public void testDeclineRequestValid() {
        System.out.println("\nStarting testDeclineRequestValid");

        Matching.SendRequest(accessRequests, accessPostings, accessMatches, u.getUserId(), p.getPostingId());

        assertEquals("Success", Matching.DeclineRequest(accessRequests, u.getUserId(), p.getPostingId()));

        ArrayList<Match> matches = new ArrayList<>();
        accessMatches.getMatchesForPosting(matches, p.getPostingId());
        assertEquals(0, matches.size());
        matches.clear();

        System.out.println("Finished testDeclineRequestValid");
    }

    public void testDeclineRequestInvalid() {

        Matching.SendRequest(accessRequests, accessPostings, accessMatches, u.getUserId(), p.getPostingId());

        assertNotNull("Success", Matching.AcceptRequest(accessRequests,accessMatches, u.getUserId(), p.getPostingId()));
        //cannot decline again as the request should be deleted
        assertNull(Matching.DeclineRequest(accessRequests, u.getUserId(), p.getPostingId()));
    }

    public void tearDown() {
        Main.shutDown();
    }
}
