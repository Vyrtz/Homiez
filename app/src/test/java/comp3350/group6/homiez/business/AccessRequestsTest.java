package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.DataAccessStub;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AccessRequestsTest extends TestCase {

    public void testAccessRequests1() {
        System.out.println("\nStarting testAccessRequests1");


        Services.createDataAccess(new DataAccessStub("test"));
        AccessRequests aRequest = new AccessRequests();
        ArrayList<Request> requests = new ArrayList<Request>();
        Request r = new Request("0", "3");

        aRequest.getRequestsForPosting(requests, "0");
        assertEquals(1, requests.size());
        assertTrue("4".equals(requests.get(0).getUserId()));
        requests.removeAll(requests);

        aRequest.getRequestsForPosting(requests,"3");
        assertEquals(0, requests.size());
        aRequest.insertRequest(r);
        aRequest.getRequestsForPosting(requests,"3");
        assertEquals(1, requests.size());
        requests.removeAll(requests);

        aRequest.deleteRequest(r);
        aRequest.getRequestsForPosting(requests,"3");
        assertEquals(0, requests.size());


        System.out.println("Finished testAccessRequests1");
    }
}
