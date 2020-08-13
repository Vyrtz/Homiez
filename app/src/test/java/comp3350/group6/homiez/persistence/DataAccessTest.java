package comp3350.group6.homiez.persistence;

import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.Request;
import comp3350.group6.homiez.objects.User;

import junit.framework.TestCase;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class DataAccessTest extends TestCase {

    private DataAccess dataAccess;

    public void setUp() {
        //dataAccess = new DataAccessStub("dataAccess1");
        //dataAccess.open("dataAccess1");
         dataAccess = new DataAccessObject(Main.dbName);
         dataAccess.open(Main.getDBPathName());

    }

    public static void dataAccessTest(DataAccess dataAccess) {
        DataAccessTest dataAccessTest = new DataAccessTest();
        dataAccessTest.dataAccess = dataAccess;
        Method[] methods = DataAccessTest.class.getMethods();
        try {
            for (Method method : methods) {
                if (method.getName().startsWith("test")) {
                    method.invoke(dataAccessTest);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

        User u = new User("7", "AAA", 24, "m", 100, "test");

        assertNull(dataAccess.getUser(u));

        assertEquals( QueryResult.SUCCESS, dataAccess.insertUser(new User("7", "John", 18, "m", 100, "test"), "test"));
        assertNotNull(dataAccess.getUser(u));

        assertEquals("John", dataAccess.getUser(u).getName());
        assertEquals(QueryResult.SUCCESS, dataAccess.updateUser(u));

        assertEquals("AAA", dataAccess.getUser(u).getName());

        //cleanup
        assertEquals(QueryResult.SUCCESS, dataAccess.deleteUser(u));

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
        //cleanup
        assertEquals(QueryResult.SUCCESS, dataAccess.deleteUser(u));
        System.out.println("Finished testInsertValidInterests");
    }

    public void testInsertInvalidUsers() {
        System.out.println("\nStarting testInsertInvalidUsers");

        User uDNE = new User("99", "AAA", 29, "f", 100, "test");

        assertEquals(QueryResult.FAILURE, dataAccess.updateUser(uDNE));
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
        assertEquals(QueryResult.SUCCESS, dataAccess.insertPosting(pDNE));
        Posting p = dataAccess.getPosting(pDNE);
        assertNotNull(p);

        assertEquals(QueryResult.SUCCESS,dataAccess.deletePosting(pDNE));
        assertNull(dataAccess.getPosting(pDNE));

        System.out.println("Finished testInvalidPostings");
    }

    public void testDeleteNonExistingPostings() {
        System.out.println("\nStarting testDeleteNonExistingPostings");

        ArrayList<Posting> postings = new ArrayList<Posting>();
        User u = new User("2", "Matt", 20, "m", 100, "test");
        Posting pExists = new Posting("4", "AAA", u, 1500, "Pembina", "Apartment", "TestDesc1");
        Posting pDNE = new Posting("8", "AAA", u, 1500, "Pembina", "Apartment", "TestDesc1");

        assertEquals( QueryResult.FAILURE, dataAccess.insertPosting(pExists));
        assertEquals(QueryResult.WARNING, dataAccess.deletePosting(pDNE));

        assertNull(dataAccess.getPosting(pDNE));

        System.out.println("Finished testDeleteNonExistingPostings");

    }
    public void testInsertDeleteNullPostings() {
        System.out.println("\nStarting testInsertDeleteNullPostings");

        assertEquals(QueryResult.FAILURE,  dataAccess.insertPosting(null));
        assertEquals(QueryResult.FAILURE,  dataAccess.deletePosting(null));

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

        assertEquals(QueryResult.FAILURE,  dataAccess.insertPosting(pExists));
        assertEquals(QueryResult.WARNING,  dataAccess.deletePosting(pDNE));
        assertEquals(QueryResult.WARNING,  dataAccess.updatePosting(pDNE));

        assertNull(dataAccess.getPosting(pDNE));
        assertEquals(QueryResult.SUCCESS, dataAccess.insertPosting(pDNE));
        assertNotNull(dataAccess.getPosting(pDNE));

        assertEquals(QueryResult.SUCCESS, dataAccess.updatePosting(pUpdate));
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

        assertEquals(QueryResult.SUCCESS, dataAccess.getRequests(requests, "99"));
        assertEquals(0, requests.size());

        System.out.println("Finished testNonExistingRequestsContents");
    }

    //check contents of match request list
    public void testMatchRequestsNullValues() {
        System.out.println("\nStarting testMatchRequestsContents");

        ArrayList<Request> requests = new ArrayList<Request>();

        assertEquals(QueryResult.FAILURE,  dataAccess.getRequests(requests, null));
        assertEquals(QueryResult.FAILURE,  dataAccess.getRequests(null, null));

        assertEquals(QueryResult.FAILURE,  dataAccess.insertRequest( null));
        assertEquals(QueryResult.FAILURE,  dataAccess.deleteRequest( null));


        System.out.println("Finished testMatchRequestsContents");
    }


    //check insertions and deletions of match requests
    public void testMatchRequestChanges() {
        System.out.println("\nStarting testMatchRequestsChanges");

        ArrayList<Request> requests = new ArrayList<Request>();
        Request rExists = new Request("4", "2");
        Request rDNE = new Request("0", "3");


        assertEquals(QueryResult.FAILURE,  dataAccess.insertRequest(rExists));

        assertEquals(QueryResult.SUCCESS, dataAccess.insertRequest(rDNE));
        assertEquals(QueryResult.SUCCESS, dataAccess.getRequests(requests, "3"));
        assertTrue(rDNE.equals(requests.get(0)));
        requests.clear();

        assertEquals(QueryResult.SUCCESS, dataAccess.deleteRequest(rExists));
        assertEquals(QueryResult.SUCCESS, dataAccess.getRequests(requests, "2"));
        assertEquals(0, requests.size());

        assertEquals(QueryResult.SUCCESS, dataAccess.insertRequest(rExists));
        assertEquals(QueryResult.SUCCESS, dataAccess.deleteRequest(rDNE));

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

        assertEquals(QueryResult.SUCCESS, dataAccess.getMatchesForPosting(matches, "99"));
        assertEquals(0, matches.size());


        assertEquals(QueryResult.SUCCESS, dataAccess.getMatchesForUser(matches, "100"));
        assertEquals(0, matches.size());

        System.out.println("Finished testNonExistingMatchesContents");
    }

    //check contents of match request list
    public void testMatchesNullValues() {
        System.out.println("\nStarting testMatchesNullValues");


        ArrayList<Match> matches = new ArrayList<Match>();


        assertEquals(QueryResult.FAILURE,  dataAccess.getMatchesForPosting(matches, null));
        assertEquals(QueryResult.FAILURE,  dataAccess.getMatchesForPosting(null, "1"));

        assertEquals(QueryResult.FAILURE,  dataAccess.getMatchesForUser(matches, null));
        assertEquals(QueryResult.FAILURE,  dataAccess.getMatchesForUser(null, "1"));

        assertEquals(QueryResult.FAILURE,  dataAccess.insertMatch( null));
        assertEquals(QueryResult.FAILURE,  dataAccess.deleteMatch( null));


        System.out.println("Finished testMatchesNullValues");
    }


    //check insertions and deletions of match requests
    public void testMatchesChanges() {
        System.out.println("\nStarting testMatchesChanges");

        ArrayList<Match> matches = new ArrayList<Match>();
        Match rExists = new Match("4", "3");
        Match rDNE = new Match("3", "5");
        assertEquals(QueryResult.FAILURE,  dataAccess.insertMatch(rExists));
        assertEquals(QueryResult.WARNING,  dataAccess.deleteMatch(rDNE));

        assertEquals(QueryResult.FAILURE, dataAccess.insertMatch(rDNE));
        rDNE = new Match("4", "4");
        assertEquals(QueryResult.SUCCESS, dataAccess.insertMatch(rDNE));

        assertEquals(QueryResult.SUCCESS, dataAccess.getMatchesForPosting(matches, "4"));
        assertTrue(rDNE.equals(matches.get(0)));
        matches.clear();

        assertEquals(QueryResult.SUCCESS, dataAccess.deleteMatch(rDNE));

        assertEquals(QueryResult.SUCCESS, dataAccess.deleteMatch(rExists));
        assertEquals(QueryResult.SUCCESS, dataAccess.getMatchesForPosting(matches, "2"));
        assertEquals(0, matches.size());

        assertEquals(QueryResult.SUCCESS, dataAccess.insertMatch(rExists));

        System.out.println("Finished testMatchesChanges");
    }

    public void testDeleteUserEntireDb() {
        System.out.println("\nStarting testDeleteUserEntireDb");

        System.out.println("\nStarting testDeletePostingEntireDb");

        ArrayList<Posting> postings = new ArrayList<Posting>();
        User u = new User("test", "test", 20, "m", 100, "test");

        assertEquals(QueryResult.SUCCESS, dataAccess.insertUser(u, "test"));

        assertEquals(QueryResult.SUCCESS, dataAccess.insertRequest(new Request("test", "3")));
        assertEquals(QueryResult.SUCCESS,  dataAccess.insertRequest(new Request("test", "2")));

        assertEquals(QueryResult.SUCCESS, dataAccess.insertMatch(new Match("test", "4")));

        User dbU = dataAccess.getUser(u);

        assertEquals(QueryResult.SUCCESS, dataAccess.deleteUser(dbU));
        ArrayList<Match> matches = new ArrayList();
        assertEquals( QueryResult.SUCCESS, dataAccess.getMatchesForUser(matches, dbU.getUserId()));
        assertEquals(0, matches.size());

        ArrayList<Request> requests = new ArrayList();
        assertEquals( QueryResult.SUCCESS, dataAccess.getRequests(requests, "2"));
        assertEquals(1, requests.size());
        requests.clear();

        assertEquals( QueryResult.SUCCESS, dataAccess.getRequests(requests, "3"));
        assertEquals(0, requests.size());

        System.out.println("Finished testDeletePostingEntireDb");
    }

    public void testDeletePostingEntireDb() {
        System.out.println("\nStarting testDeletePostingEntireDb");

        ArrayList<Posting> postings = new ArrayList<Posting>();
        User u = new User("2", "Matt", 20, "m", 100, "test");
        Posting pExists = new Posting("5", "AAA", u, 1500, "Pembina", "Apartment", "TestDesc1");
        pExists.addAttachedUser(new User("1"));
        pExists.addAttachedUser(new User("2"));

        assertEquals(QueryResult.SUCCESS, dataAccess.insertPosting(pExists));

        assertEquals(QueryResult.SUCCESS, dataAccess.insertRequest(new Request("0", "5")));
        assertEquals(QueryResult.SUCCESS, dataAccess.insertRequest(new Request("3", "5")));

        assertEquals(QueryResult.SUCCESS, dataAccess.insertMatch(new Match("4", "5")));

        Posting dbP = dataAccess.getPosting(pExists);

        assertEquals(QueryResult.SUCCESS, dataAccess.deletePosting(pExists));
        ArrayList<Match> matches = new ArrayList();
        assertEquals( QueryResult.SUCCESS, dataAccess.getMatchesForPosting(matches, pExists.getPostingId()));
        assertEquals(0, matches.size());

        ArrayList<Request> requests = new ArrayList();
        assertEquals( QueryResult.SUCCESS, dataAccess.getRequests(requests, pExists.getPostingId()));
        assertEquals(0, requests.size());

        System.out.println("Finished testDeletePostingEntireDb");
    }

    public void tearDown() {
        dataAccess.close();
    }
}
