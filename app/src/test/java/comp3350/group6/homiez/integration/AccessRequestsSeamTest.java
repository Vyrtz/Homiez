package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.business.AccessRequests;
import comp3350.group6.homiez.objects.Request;

public class AccessRequestsSeamTest extends TestCase {

    private AccessRequests accessRequests;

    public void setUp() {
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);

        accessRequests = new AccessRequests();
    }

    public void testValidInsert() {
        System.out.println("Starting testValidInsert");
        Request newRequest = new Request("1", "0"); //New valid request
        assertEquals(QueryResult.SUCCESS, accessRequests.insertRequest(newRequest));
        accessRequests.deleteRequest(newRequest);
        System.out.println("Finished testValidInsert");
    }

    public void testInvalidInsert() {
        System.out.println("Starting testInvalidInsert");
        //Invalid PostingID
        assertEquals(QueryResult.FAILURE, accessRequests.insertRequest(new Request("1", "DNE")));

        //Invalid User
        assertEquals(QueryResult.FAILURE, accessRequests.insertRequest(new Request("DNE", "0")));

        System.out.println("Finished testInvalidInsert");
    }

    public void testValidDelete() {
        System.out.println("Starting testValidDelete");
        ArrayList<Request> requests = new ArrayList<>();

        //Fetch an existing request to test
        accessRequests.getRequestsForPosting(requests, "0");
        Request request = requests.get(0);

        assertEquals(QueryResult.SUCCESS, accessRequests.deleteRequest(request);
        accessRequests.insertRequest(request); //Insert it back to preserve the database

        //New Request
        Request newRequest = new Request("1", "0");
        accessRequests.insertRequest(newRequest);
        assertEquals(QueryResult.SUCCESS, accessRequests.deleteRequest(newRequest));

        System.out.println("Finished testValidDelete");
    }

    public void testInvalidDelete() {
        System.out.println("Starting testInvalidDelete");
        assertEquals(QueryResult.WARNING, accessRequests.deleteRequest(new Request("1", "DNE"))); //Invalid posting ID
        assertEquals(QueryResult.WARNING, accessRequests.deleteRequest(new Request("DNE", "0"))); //Invalid userID
        System.out.println("Finished testInvalidDelete");
    }

    public void testValidGet() {
        System.out.println("Starting testValidGet");
        ArrayList<Request> requests = new ArrayList<>();
        assertEquals(QueryResult.SUCCESS, accessRequests.getRequestsForPosting(requests, "0"));

        System.out.println("Finished testValidGet");
    }

    public void testInvalidGet() {
        System.out.println("Starting testInvalidGet");
        ArrayList<Request> requests = new ArrayList<>();
        accessRequests.getRequestsForPosting(requests, "DNE"); //Posting that doesn't exist
        assertTrue(requests.size() == 0);
        System.out.println("Finished testInvalidGet");
    }
}
