package com.example.homiez.business;

import com.example.homiez.application.Main;
import com.example.homiez.objects.Request;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AccessRequestsTest extends TestCase {

    public void testAccessRequests1(){
        System.out.println("\nStarting testAccessRequests1");


        Main.startUp();
        AccessRequests aRequest = new AccessRequests();
        ArrayList<Request> requests = new ArrayList<Request>();
        Request r = new Request("0", "3");

        aRequest.getRequestsForPosting(requests, "0");
        assertEquals(1, requests.size(), 0.01);
        assertTrue("4".equals(requests.get(0).getUserId()));
        requests.removeAll(requests);

        aRequest.getRequestsForPosting(requests,"3");
        assertEquals(0, requests.size(), 0.01);
        aRequest.insertRequest(r);
        aRequest.getRequestsForPosting(requests,"3");
        assertEquals(1, requests.size(), 0.01);
        requests.removeAll(requests);

        aRequest.deleteRequest(r);
        aRequest.getRequestsForPosting(requests,"3");
        assertEquals(0, requests.size(), 0.01);


        System.out.println("Finished testAccessRequests1");
    }
}
