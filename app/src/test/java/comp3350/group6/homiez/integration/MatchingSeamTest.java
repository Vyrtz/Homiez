package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.business.AccessMatches;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.business.AccessRequests;
import comp3350.group6.homiez.business.Matching;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Request;

public class MatchingSeamTest extends TestCase {

    private Matching matching;
    private AccessRequests accessRequests;
    private AccessMatches accessMatches;
    private AccessPostings accessPostings;
    private ArrayList<Request> requests;
    private ArrayList<Match> matches;

    public void setUp() {
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);

        matching = new Matching();
        accessRequests = new AccessRequests();
        accessMatches = new AccessMatches();
        accessPostings = new AccessPostings();
        requests = new ArrayList<>();
        matches = new ArrayList<>();
    }

    public void testValidAcceptRequest() {
        System.out.println("Starting testValidAcceptRequest");
        accessRequests.getRequestsForPosting(requests, "0");
        Request request = requests.get(0); //Grab the 1 request that postingId 0 has for cleanup

        assertEquals(QueryResult.SUCCESS, Matching.AcceptRequest(accessRequests, accessMatches, "4", "0"));
        //System.out.println(Matching.AcceptRequest(accessRequests, accessMatches, "4", "0"));
        accessMatches.getMatchesForPosting(matches, "0");

        //Delete the new match
        for(Match m : matches) {
            if(m.getUserId().equals("4")){
                System.out.println(accessMatches.deleteMatch(m));
            }
        }

        //Insert the original request back in
        System.out.println(accessRequests.insertRequest(request));

        System.out.println("Finished testValidAcceptRequest");
    }

    public void testInvalidAcceptRequest() {
        System.out.println("Starting testInvalidAcceptRequest");
        //Invalid user
        assertEquals(QueryResult.FAILURE, Matching.AcceptRequest(accessRequests, accessMatches, "DNE", "0"));
        //Invalid postingID
        assertEquals(QueryResult.FAILURE, Matching.AcceptRequest(accessRequests, accessMatches, "4", "DNE"));
        //Non-existent request
        assertEquals(QueryResult.FAILURE, Matching.AcceptRequest(accessRequests, accessMatches, "4", "3"));
        System.out.println("Finished testInvalidAcceptRequest");
    }

    public void testValidDeclineRequest() {
        System.out.println("Starting testValidDeclineRequest");
        accessRequests.getRequestsForPosting(requests, "0");
        Request request = requests.get(0); //Grab the one default request we have so we can cleanup

        assertEquals(QueryResult.SUCCESS, Matching.DeclineRequest(accessRequests,"4", "0"));

        accessRequests.insertRequest(request); //Cleanup
        System.out.println("Finished testInvalidAcceptRequest");
    }

    public void testInvalidDeclineRequest() {
        System.out.println("Starting testInvalidDeclineRequest");
        //Invalid User
        assertEquals(QueryResult.FAILURE, Matching.DeclineRequest(accessRequests, "DNE", "0"));
        //Invalid PostingID
        assertEquals(QueryResult.FAILURE, Matching.DeclineRequest(accessRequests, "4", "DNE"));
        //Non-existent request
        assertEquals(QueryResult.FAILURE, Matching.DeclineRequest(accessRequests, "4", "3"));

        System.out.println("Finished testInvalidDeclineRequest");
    }

    public void testValidSendRequest() {
        System.out.println("Starting testValidSendRequest");
        assertEquals(QueryResult.SUCCESS, Matching.SendRequest(accessRequests, accessPostings, accessMatches, "3", "3"));

        //Cleanup
        ArrayList<Request> requests = new ArrayList<>();
        accessRequests.getRequestsForPosting(requests, "3");
        Request request = requests.get(0);
        accessRequests.deleteRequest(request); //Cleanup
        System.out.println("Finished testValidSendRequest");
    }

    public void testInvalidSendRequest() {
        System.out.println("Starting testInvalidSendRequest");
        //Invalid user
        assertEquals(QueryResult.FAILURE, Matching.SendRequest(accessRequests, accessPostings, accessMatches, "DNE", "3"));
        //Invalid postID
        assertEquals(QueryResult.FAILURE, Matching.SendRequest(accessRequests, accessPostings, accessMatches, "4", "DNE"));
        System.out.println("Finished testInvalidSendRequest");
    }


}
