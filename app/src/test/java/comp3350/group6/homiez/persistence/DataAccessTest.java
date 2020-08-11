package comp3350.group6.homiez.persistence;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;

import junit.framework.TestCase;

import java.util.ArrayList;

public class DataAccessTest extends TestCase {


    private DataAccess dataAccess;

    public void setUp() {
        dataAccess = new DataAccessStub("dataAccess1");
        dataAccess.open("dataAccess1");
//        dataAccess = new DataAccessObject(Main.dbName);
//        dataAccess.open(Main.getDBPathName());

    }
    //Check that dataAccess exists
    public void testDataAccessdataAccessExists() {
        System.out.println("\nStarting testDataAccessdataAccessExists");

        assertNotNull(dataAccess);

        System.out.println("Finished testDataAccessdataAccessExists");
    }

    //Check contents of user list
    public void testExistingUsers() {
        System.out.println("\nStarting testExistingUsers");

        User u = dataAccess.getUser(new User("0"));
        assertNotNull(u);
        assertEquals("Abhi",u.getName());

        u = dataAccess.getUser(new User("1"));
        assertNotNull(u);
        assertEquals("Jordan",u.getName());

        System.out.println("Finished testExistingUsers");
    }

    public void testNonExistingUsers() {
        System.out.println("\nStarting testExistingUsers");

        User u = dataAccess.getUser(new User("99"));
        assertNull(u);

        u = dataAccess.getUser(new User("100"));
        assertNull(u);
        System.out.println("Finished testExistingUsers");
    }

    public void testInvalidUsers() {
        System.out.println("\nStarting testInvalidUsers");

        User u = dataAccess.getUser(null);
        assertNull(u);

        System.out.println("Finished testInvalidUsers");
    }

    //Check inserting and updating users
    public void testInsertValidUsers() {
        System.out.println("\nStarting testInsertValidUsers");

        User u = new User("6", "AAA", 24, "m", 100, "test");

        assertNull(dataAccess.getUser(u));
        dataAccess.insertUser(new User("6", "John", 18, "m", 100, "test"), "test");
        assertNotNull(dataAccess.getUser(u));

        assertEquals("John", dataAccess.getUser(u).getName());
        dataAccess.updateUser(u);

        assertEquals("AAA", dataAccess.getUser(u).getName());

        System.out.println("Finished testInsertValidUsers");
    }

    public void testInsertValidInterests() {
        System.out.println("\nStarting testInsertValidInterests");

        User u = new User("9", "AAA", 24, "m", 100, "test");
        u.addUniqueInterest(new Interest("testing1"));
        u.addUniqueInterest(new Interest("testing2"));
        dataAccess.insertUser(u, "test");
        assertNotNull(dataAccess.getUser(u));

        u = dataAccess.getUser(u);
        assertEquals("AAA", u.getName());
        assertEquals(new Interest("testing1"), u.getInterests().get(0));
        assertEquals(new Interest("testing2"), u.getInterests().get(1));

        try {
            u.getInterests().get(2);
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            // passed
        }
        System.out.println("Finished testInsertValidInterests");
    }

    public void testInsertInvalidUsers() {
        System.out.println("\nStarting testInsertInvalidUsers");

        User uDNE = new User("99", "AAA", 29, "f", 100, "test");

        assertNull(dataAccess.updateUser(uDNE));
        assertNull(dataAccess.getUser(uDNE));


        System.out.println("Finished testInsertInvalidUsers");
    }

    public void testExistingPosting() {
        System.out.println("\nStarting testExistingPosting");

        Posting p = dataAccess.getPosting(new Posting("0"));
        assertNotNull(p);
        assertEquals("Abhi", p.getUser().getName());
        ArrayList<Posting> ps = new ArrayList<>();
        assertNotNull(dataAccess.getPostingsByUser(ps, new User("0")));
        assertEquals(3, ps.size());
        ps.clear();

        assertNotNull(dataAccess.getAllDisplayPostings(ps, new User("0")));
        assertEquals(2, ps.size());
        ps.clear();


        System.out.println("Finished testExistingPosting");
    }

    public void testNonExistingPostings() {
        System.out.println("\nStarting testNonExistingPostings");

        Posting p = dataAccess.getPosting(new Posting("-11"));
        assertNull(p);

        p = dataAccess.getPosting(new Posting("7"));
        assertNull(p);

        System.out.println("Finished testNonExistingPostings");
    }

    public void testInvalidPostings() {
        System.out.println("\nStarting testInvalidPostings");

        Posting p = dataAccess.getPosting(null);
        assertNull(p);

        System.out.println("Finished testInvalidPostings");
    }

    //Check inserting and updating users
    public void testInsertValidPostings() {
        System.out.println("\nStarting testInvalidPostings");

        ArrayList<Posting> postings = new ArrayList<Posting>();
        User u = new User("2", "Matt", 20, "m", 100, "test");
        Posting pDNE = new Posting("8", "AAA", u, 1500, "Pembina", "Apartment", "TestDesc1");

        assertNull(dataAccess.getPosting(pDNE));
        dataAccess.insertPosting(pDNE);
        Posting p = dataAccess.getPosting(pDNE);
        assertNotNull(p);

        dataAccess.deletePosting(pDNE);
        assertNull(dataAccess.getPosting(pDNE));

        System.out.println("Finished testInvalidPostings");
    }

    public void testDeleteNonExistingPostings() {
        System.out.println("\nStarting testDeleteNonExistingPostings");

        ArrayList<Posting> postings = new ArrayList<Posting>();
        User u = new User("2", "Matt", 20, "m", 100, "test");
        Posting pExists = new Posting("2", "AAA", u, 1500, "Pembina", "Apartment", "TestDesc1");
        Posting pDNE = new Posting("8", "AAA", u, 1500, "Pembina", "Apartment", "TestDesc1");
        Posting pUpdate = new Posting("8", "nameChange", u, 1500, "Pembina", "Apartment", "TestDesc1");

        assertNull(dataAccess.insertPosting(pExists));
        assertNull(dataAccess.deletePosting(pDNE));

        assertNull(dataAccess.getPosting(pDNE));

        dataAccess.deletePosting(pUpdate);
        assertNull(dataAccess.getPosting(pUpdate));

        System.out.println("Finished testDeleteNonExistingPostings");

    }
    public void testInsertDeleteNullPostings() {
        System.out.println("\nStarting testInsertDeleteNullPostings");

        assertNull(dataAccess.insertPosting(null));
        assertNull(dataAccess.deletePosting(null));

        System.out.println("Finished testInsertDeleteNullPostings");

    }

    //check posting updates, insertions and deletions
    public void testPostingAllValidChanges() {
        System.out.println("\nStarting testPostingAllValidChanges");

        ArrayList<Posting> postings = new ArrayList<Posting>();
        User u = new User("2", "Matt", 20, "m", 100, "test");
        Posting pExists = new Posting("2", "AAA", u, 1500, "Pembina", "Apartment", "TestDesc1");
        Posting pDNE = new Posting("8", "AAA", u, 1500, "Pembina", "Apartment", "TestDesc1");
        Posting pUpdate = new Posting("8", "nameChange", u, 1500, "Pembina", "Apartment", "TestDesc1");

        assertNull(dataAccess.insertPosting(pExists));
        assertNull(dataAccess.deletePosting(pDNE));
        assertNull(dataAccess.updatePosting(pDNE));

        assertNull(dataAccess.getPosting(pDNE));
        dataAccess.insertPosting(pDNE);
        assertNotNull(dataAccess.getPosting(pDNE));

        dataAccess.updatePosting(pUpdate);
        assertEquals("nameChange" , dataAccess.getPosting(pDNE).getTitle());

        dataAccess.deletePosting(pUpdate);
        assertNull(dataAccess.getPosting(pUpdate));

        System.out.println("Finished testPostingAllValidChanges");
    }


    public void testExistingRequestsContents() {
        System.out.println("\nStarting testExistingRequestsContents");


        ArrayList<Request> requests = new ArrayList<Request>();

        dataAccess.getRequests(requests, "0");
        assertEquals(1, requests.size());
        dataAccess.getRequests(requests, "1");
        assertEquals(2, requests.size());
        dataAccess.getRequests(requests, "2");
        assertEquals(3, requests.size());
        dataAccess.getRequests(requests, "3");
        assertEquals(3, requests.size());
        dataAccess.getRequests(requests, "4");
        assertEquals(3, requests.size());
        dataAccess.getRequests(requests, "5");
        assertEquals(3, requests.size());

        System.out.println("Finished testExistingRequestsContents");
    }
    public void testNonExistingRequestsContents() {
        System.out.println("\nStarting testNonExistingRequestsContents");


        ArrayList<Request> requests = new ArrayList<Request>();

        dataAccess.getRequests(requests, "99");
        assertEquals(0, requests.size());

        System.out.println("Finished testNonExistingRequestsContents");
    }

    //check contents of match request list
    public void testMatchRequestsNullValues() {
        System.out.println("\nStarting testMatchRequestsContents");


        ArrayList<Request> requests = new ArrayList<Request>();


        assertNull(dataAccess.getRequests(requests, null));
        assertNull(dataAccess.getRequests(null, null));

        assertNull(dataAccess.insertRequest( null));
        assertNull(dataAccess.deleteRequest( null));


        System.out.println("Finished testMatchRequestsContents");
    }


    //check insertions and deletions of match requests
    public void testMatchRequestChanges() {
        System.out.println("\nStarting testMatchRequestsChanges");

        ArrayList<Request> requests = new ArrayList<Request>();
        Request rExists = new Request("4", "2");
        Request rDNE = new Request("0", "3");


        assertNull(dataAccess.insertRequest(rExists));

        dataAccess.insertRequest(rDNE);
        dataAccess.getRequests(requests, "3");
        assertTrue(rDNE.equals(requests.get(0)));
        requests.clear();

        dataAccess.deleteRequest(rExists);
        dataAccess.getRequests(requests, "2");
        assertEquals(0, requests.size());


        System.out.println("Finished testMatchRequestsChanges");
    }

    public void testExistingMatchesContents() {
        System.out.println("\nStarting testExistingMatchesContents");

        ArrayList<Match> matches = new ArrayList<Match>();

        dataAccess.getMatchesForUser(matches, "0");
        assertEquals(0, matches.size());
        dataAccess.getMatchesForUser(matches, "1");
        assertEquals(0, matches.size());
        dataAccess.getMatchesForUser(matches, "2");
        assertEquals(0, matches.size());
        dataAccess.getMatchesForUser(matches, "3");
        assertEquals(2, matches.size());
        dataAccess.getMatchesForUser(matches, "4");
        assertEquals(3, matches.size());
        matches.clear();

        dataAccess.getMatchesForPosting(matches, "0");
        assertEquals(1, matches.size());
        dataAccess.getMatchesForPosting(matches, "1");
        assertEquals(2, matches.size());
        dataAccess.getMatchesForPosting(matches, "2");
        assertEquals(2, matches.size());
        dataAccess.getMatchesForPosting(matches, "3");
        assertEquals(3, matches.size());
        dataAccess.getMatchesForPosting(matches, "4");
        assertEquals(3, matches.size());

        assertEquals("3", matches.get(0).getUserId());
        assertEquals("3", matches.get(2).getPostingId());

        System.out.println("Finished testExistingMatchesContents");
    }
    public void testNonExistingMatchesContents() {
        System.out.println("\nStarting testNonExistingMatchesContents");


        ArrayList<Match> matches = new ArrayList<Match>();

        dataAccess.getMatchesForPosting(matches, "99");
        assertEquals(0, matches.size());


        dataAccess.getMatchesForUser(matches, "100");
        assertEquals(0, matches.size());

        System.out.println("Finished testNonExistingMatchesContents");
    }

    //check contents of match request list
    public void testMatchesNullValues() {
        System.out.println("\nStarting testMatchesNullValues");


        ArrayList<Match> matches = new ArrayList<Match>();


        assertNull(dataAccess.getMatchesForPosting(matches, null));
        assertNull(dataAccess.getMatchesForPosting(null, "1"));

        assertNull(dataAccess.getMatchesForUser(matches, null));
        assertNull(dataAccess.getMatchesForUser(null, "1"));

        assertNull(dataAccess.insertMatch( null));
        assertNull(dataAccess.deleteMatch( null));


        System.out.println("Finished testMatchesNullValues");
    }


    //check insertions and deletions of match requests
    public void testMatchesChanges() {
        System.out.println("\nStarting testMatchesChanges");

        ArrayList<Match> matches = new ArrayList<Match>();
        Match rExists = new Match("4", "3");
        Match rDNE = new Match("3", "5");


        assertNull(dataAccess.insertMatch(rExists));
        assertNull(dataAccess.deleteMatch(rDNE));

        dataAccess.insertMatch(rDNE);
        dataAccess.getMatchesForPosting(matches, "5");
        assertTrue(rDNE.equals(matches.get(0)));
        matches.clear();

        dataAccess.deleteMatch(rExists);
        dataAccess.getMatchesForPosting(matches, "2");
        assertEquals(0, matches.size());


        System.out.println("Finished testMatchesChanges");
    }
}
