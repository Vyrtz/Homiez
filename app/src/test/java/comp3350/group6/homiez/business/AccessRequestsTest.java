package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.persistence.DataAccessStub;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AccessRequestsTest extends TestCase {

    private AccessRequests  accessRequests;
    private ArrayList<Request> requests;

    public void setUp() {
        Services.createDataAccess(new DataAccessStub("test"));
        accessRequests = new AccessRequests();
        requests = new ArrayList<Request>();
    }

    public void testAccessRequestsConstruction() {
        System.out.println("\nStarting testAccessRequestsConstruction");

        accessRequests.getRequestsForPosting(requests, "6");
        assertEquals(0, requests.size());

        System.out.println("Finished testAccessRequestsConstruction");
    }

    public void testNullValues() {
        System.out.println("\nStarting testNullValues");

        accessRequests.getRequestsForPosting(requests, null);
        assertEquals(0, requests.size());
        requests.clear();

        accessRequests.getRequestsForPosting(requests, null);
        assertEquals(0, requests.size());
        requests.clear();

        assertNull(accessRequests.insertRequest( null));
        assertNull(accessRequests.deleteRequest( null));

        System.out.println("Finished testNullValues");
    }


    public void testBadValues() {
        System.out.println("\nStarting testBadValues");

        accessRequests.getRequestsForPosting(requests, "-1");
        assertEquals(0, requests.size());
        requests.clear();

        accessRequests.getRequestsForPosting(requests, "-1");
        assertEquals(0, requests.size());
        requests.clear();

        assertNull(accessRequests.deleteRequest( new Request("111", "999")));

        System.out.println("Finished testBadValues");
    }

    public void testNonExistingMatches() {
        System.out.println("\nStarting testNonExistentRequests");

        Request r = new Request("0", "3");
        accessRequests.getRequestsForPosting(requests, "3");
        assertEquals(0, requests.size());

        assertNull(accessRequests.deleteRequest( new Request("100", "100")));

        accessRequests.insertRequest(r);
        accessRequests.getRequestsForPosting(requests, "3");
        assertEquals(1, requests.size());
        requests.clear();

        accessRequests.deleteRequest(r);
        accessRequests.getRequestsForPosting(requests, "3");
        assertEquals(0, requests.size());

        System.out.println("Finished testNonExistingRequests");
    }

    public void testExistingMatches() {
        System.out.println("\nStarting testExistingMatches");

        Request r;

        accessRequests.getRequestsForPosting(requests, "0");
        assertEquals(1, requests.size());
        r = requests.get(0);
        requests.clear();

        accessRequests.deleteRequest(r);
        accessRequests.getRequestsForPosting(requests, "0");
        assertEquals(0, requests.size());


        System.out.println("Finished testExistingMatches");
    }
}
