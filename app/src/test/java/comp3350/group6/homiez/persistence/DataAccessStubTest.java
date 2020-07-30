package comp3350.group6.homiez.persistence;

import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;

import junit.framework.TestCase;

import java.util.ArrayList;

public class DataAccessStubTest extends TestCase {



    //Check that stub exists
    public void testDataAccessStubExists() {
        System.out.println("\nStarting testDataAccessStubExists");

        DataAccessStub stub = new DataAccessStub("stub1");
        assertNotNull(stub);

        System.out.println("Finished testDataAccessStubExists");
    }


    //Check contents of user list
    public void testUsersContents() {
        System.out.println("\nStarting testUsersContents");

        DataAccessStub stub = new DataAccessStub("stub1");
        stub.open("stub1");
        User u = new User("2", "AAA", 20, "m", 100, "test");

        assertNotNull(stub.getUser(new User("0", "AAA", 20, "m", 100, "test")));
        assertNotNull(stub.getUser(new User("1", "AAA", 20, "m", 100, "test")));
        assertNotNull(stub.getUser(new User("2", "AAA", 20, "m", 100, "test")));
        assertNotNull(stub.getUser(new User("3", "AAA", 20, "m", 100, "test")));
        assertNotNull(stub.getUser(new User("4", "AAA", 20, "m", 100, "test")));
        assertNull(stub.getUser(new User("5", "Abhi", 20, "m", 100, "test")));

        assertTrue("Matt".equals(stub.getUser(u).getName()));

        System.out.println("Finished testDataUsersContents");
    }


    //Check inserting and updating users
    public void testUsersChanges() {
        System.out.println("\nStarting testUsersChanges");

        DataAccessStub stub = new DataAccessStub("stub1");
        stub.open("stub1");
        User u = new User("5", "AAA", 24, "m", 100, "test");
        User uExists = new User("2", "Matt", 20, "m", 100, "test");
        User uDNE = new User("99", "AAA", 29, "f", 100, "test");


        assertNull(stub.getUser(u));
        stub.insertUser(new User("5", "John", 18, "m", 100, "test"));
        assertNotNull(stub.getUser(u));
        assertNull(stub.insertUser(uExists));


        assertFalse("AAA".equals(stub.getUser(u).getName()));
        stub.updateUser(u);
        assertTrue("AAA".equals(stub.getUser(u).getName()));
        assertNull(stub.updateUser(uDNE));


        System.out.println("Finished testUsersChanges");
    }


    //Check contents of postings list

    //check posting updates, insertions and deletions
    public void testPostingChanges() {
        System.out.println("\nStarting testPostingChanges");


        DataAccessStub stub = new DataAccessStub("stub1");
        stub.open("stub1");
        ArrayList<Posting> postings = new ArrayList<Posting>();
        User u = new User("2", "Matt", 20, "m", 100, "test");
        Posting pExists = new Posting("2", "AAA", u, 1500, "Pembina", "Apartment", "TestDesc1");
        Posting pDNE = new Posting("8", "AAA", u, 1500, "Pembina", "Apartment", "TestDesc1");
        Posting pUpdate = new Posting("8", "nameChange", u, 1500, "Pembina", "Apartment", "TestDesc1");

        assertNull(stub.insertPosting(pExists));
        assertNull(stub.deletePosting(pDNE));
        assertNull(stub.updatePosting(pDNE));

        assertNull(stub.getPosting(pDNE));
        stub.insertPosting(pDNE);
        assertNotNull(stub.getPosting(pDNE));

        stub.updatePosting(pUpdate);
        assertTrue("nameChange".equals(stub.getPosting(pDNE).getTitle()));

        stub.deletePosting(pUpdate);
        assertNull(stub.getPosting(pUpdate));


        System.out.println("Finished testPostingChanges");
    }


    //check contents of match request list
    public void testMatchRequestsContents() {
        System.out.println("\nStarting testMatchRequestsContents");


        DataAccessStub stub = new DataAccessStub("stub1");
        stub.open("stub1");
        ArrayList<Request> requests = new ArrayList<Request>();

        stub.getRequests(requests, "0");
        assertEquals(1, requests.size(), 0.01);
        stub.getRequests(requests, "1");
        assertEquals(2, requests.size(), 0.01);
        stub.getRequests(requests, "2");
        assertEquals(3, requests.size(), 0.01);
        stub.getRequests(requests, "3");
        assertEquals(3, requests.size(), 0.01);
        stub.getRequests(requests, "4");
        assertEquals(3, requests.size(), 0.01);
        stub.getRequests(requests, "5");
        assertEquals(3, requests.size(), 0.01);

        assertTrue("0".equals(requests.get(0).getPostingId()));
        assertTrue("1".equals(requests.get(1).getPostingId()));
        assertTrue("2".equals(requests.get(2).getPostingId()));


        System.out.println("Finished testMatchRequestsContents");
    }


    //check insertions and deletions of match requests
    public void testMatchRequestChanges() {
        System.out.println("\nStarting testMatchRequestsChanges");

        DataAccessStub stub = new DataAccessStub("stub1");
        stub.open("stub1");
        ArrayList<Request> requests = new ArrayList<Request>();
        Request rExists = new Request("4", "2");
        Request rDNE = new Request("0", "3");


        assertNull(stub.insertRequest(rExists));
        assertNull(stub.deleteRequest(rDNE));

        stub.insertRequest(rDNE);
        stub.getRequests(requests, "3");
        assertTrue(rDNE.equals(requests.get(0)));
        requests.removeAll(requests);

        stub.deleteRequest(rExists);
        stub.getRequests(requests, "2");
        assertEquals(0, requests.size(), 0.01);


        System.out.println("Finished testMatchRequestsChanges");
    }


    //check contents of matches list
    public void testMatchesContents() {
        System.out.println("\nStarting testMatchesContents");


        DataAccessStub stub = new DataAccessStub("stub1");
        stub.open("stub1");
        ArrayList<Match> matches = new ArrayList<Match>();

        stub.getMatchesForUser(matches, "0");
        assertEquals(0, matches.size(), 0.01);
        stub.getMatchesForUser(matches, "1");
        assertEquals(0, matches.size(), 0.01);
        stub.getMatchesForUser(matches, "2");
        assertEquals(0, matches.size(), 0.01);
        stub.getMatchesForUser(matches, "3");
        assertEquals(2, matches.size(), 0.01);
        stub.getMatchesForUser(matches, "4");
        assertEquals(3, matches.size(), 0.01);
        matches.removeAll(matches);

        stub.getMatchesForPosting(matches, "0");
        assertEquals(1, matches.size(), 0.01);
        stub.getMatchesForPosting(matches, "1");
        assertEquals(2, matches.size(), 0.01);
        stub.getMatchesForPosting(matches, "2");
        assertEquals(2, matches.size(), 0.01);
        stub.getMatchesForPosting(matches, "3");
        assertEquals(3, matches.size(), 0.01);
        stub.getMatchesForPosting(matches, "4");
        assertEquals(3, matches.size(), 0.01);

        assertTrue("3".equals(matches.get(0).getUserId()));
        assertTrue("3".equals(matches.get(2).getPostingId()));


        System.out.println("Finished testMatchesContents");
    }


    //check match insertions and deletions
    public void testMatchesChanges() {
        System.out.println("\nStarting testMatchesChanges");

        DataAccessStub stub = new DataAccessStub("stub1");
        stub.open("stub1");
        ArrayList<Match> matches = new ArrayList<Match>();
        Match mExists = new Match("4", "3");
        Match mDNE = new Match("0", "0");


        assertNull(stub.deleteMatch(mDNE));
        assertNull(stub.insertMatch(mExists));


        stub.insertMatch(mDNE);
        stub.getMatchesForUser(matches, "0");
        assertTrue(mDNE.equals(matches.get(0)));
        matches.removeAll(matches);

        stub.deleteMatch(mExists);
        stub.getMatchesForUser(matches, "4");
        assertEquals(0, matches.size(), 0.01);


        System.out.println("\nFinished testMatchesChanges");
    }

}
