package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;
import comp3350.group6.homiez.persistence.DataAccessStub;

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
        Services.createDataAccess(new DataAccessStub("test"));
        u = new User("0", "Abhi", 20, "m", 100, "test");
        accessRequests = new AccessRequests();
        aUser = new AccessUser();
        accessPostings = new AccessPostings();
        accessMatches = new AccessMatches();
        p = new Posting("test", "test", new User("5"), 2, "test", "test", "test");
        accessPostings.insertPosting(p);
    }


    //postinglist_text operations when null parameters are passed
    public void testNullCasesOnSendRequest() {
        System.out.println("\nStarting testNullCasesOnSendRequest");

        assertNull(Matching.SendRequest(accessRequests, accessPostings, accessMatches, u.getUserId(), null));
        assertNull(Matching.SendRequest(accessRequests, accessPostings, accessMatches, null, "3"));
        assertNull(Matching.SendRequest(accessRequests, null, accessMatches, u.getUserId(), "3"));
        assertNull(Matching.SendRequest(null, accessPostings, accessMatches, u.getUserId(), "3"));

        System.out.println("Finished testNullCasesOnSendRequest");
    }
    //postinglist_text operations when null parameters are passed
    public void testNullCasesOnAcceptRequest() {
        System.out.println("\nStarting testNullCasesOnAcceptRequest");

        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, null, "3"));
        assertNull(Matching.AcceptRequest(accessRequests, accessMatches, u.getUserId(), null));
        assertNull(Matching.AcceptRequest(null, accessMatches, u.getUserId(), "3"));
        assertNull(Matching.AcceptRequest(accessRequests, null, u.getUserId(), "3"));

        System.out.println("Finished testNullCasesOnAcceptRequest");
    }

    //postinglist_text operations when null parameters are passed
    public void testNullCasesOnDeclineRequest() {
        System.out.println("\nStarting testNullCasesOnDeclineRequest");

        assertNull(Matching.DeclineRequest(accessRequests, null, "3"));
        assertNull(Matching.DeclineRequest(accessRequests, u.getUserId(), null));
        assertNull(Matching.DeclineRequest(null, u.getUserId(), "3"));

        System.out.println("Finished testNullCasesOnDeclineRequest");
    }


    //postinglist_text operations when no result will be found
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

        Posting p2 = new Posting("test2", "postinglist_text",u,2,"postinglist_text","postinglist_text","postinglist_text");
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
        System.out.println("\nStarting testDeclineRequestInvalid");
        Matching.SendRequest(accessRequests, accessPostings, accessMatches, u.getUserId(), p.getPostingId());

        assertEquals("Success", Matching.AcceptRequest(accessRequests,accessMatches, u.getUserId(), p.getPostingId()));
        //cannot decline again as the request should be deleted
        String result = Matching.DeclineRequest(accessRequests, u.getUserId(), p.getPostingId());
        assertNull(result);
        System.out.println("Finished testDeclineRequestInvalid");
    }
    public void testContainsMatchAlready() {
        System.out.println("\nStarting testContainsMatchAlready");
        Matching.SendRequest(accessRequests, accessPostings, accessMatches, u.getUserId(), p.getPostingId());
        Matching.AcceptRequest(accessRequests, accessMatches, u.getUserId(), p.getPostingId());
        String result = Matching.SendRequest(accessRequests, accessPostings, accessMatches, u.getUserId(), p.getPostingId());;
        assertNull(result);
        System.out.println("Finished testContainsMatchAlready");
    }
    public void testInvalidUser() {
        System.out.println("\nStarting testInvalidUser");
        String result = Matching.SendRequest(accessRequests, accessPostings, accessMatches, "99", p.getPostingId());
        ArrayList<Request> reqs = new ArrayList<>();
        accessRequests.getRequestsForPosting(reqs, p.getPostingId());
        Request toMatch = new Request("99", p.getPostingId());
        if (reqs.contains(toMatch)) {
            fail();
        }
        System.out.println("Finished testInvalidUser");
    }
    public void testInvalidPosting() {
        System.out.println("\nStarting testInvalidPosting");
        String result = Matching.SendRequest(accessRequests, accessPostings, accessMatches, "0", "99");
        ArrayList<Request> reqs = new ArrayList<>();
        accessRequests.getRequestsForPosting(reqs, "99");
        Request toMatch = new Request("0", "99");
        if (reqs.contains(toMatch)) {
            fail();
        }
        System.out.println("Finished testInvalidPosting");
    }
    public void tearDown() {
        Main.shutDown();
    }
}
