package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;
import comp3350.group6.homiez.persistence.DataAccessStub;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AccessPostingsTest extends TestCase {

    User u;
    AccessPostings aPostings;
    ArrayList<Posting> postings;
    Posting newPost;
    Posting postExists;
    Posting postDNE;
    Posting updatePost;


    public AccessPostingsTest(String arg0) {
        super(arg0);
    }

    public void setUp() {
        Services.createDataAccess(new DataAccessStub("test"));
        u = new User ("3","Vinh", 18, "m",200,"test");
        aPostings = new AccessPostings();
        postings = new ArrayList<Posting>();
        postings.removeAll(postings);
        postExists = new Posting("0", "Room at Pembina Riverside Condo", u, 1000,  "Pembina", "Condo", "A beautiful riverside condo in the heart of Pembina. Great view of the surrounding area.");
        postDNE = new Posting("10", "TEST TITLE", u, 2500, "TEST LOC", "TEST TYPE", "TEST DESC");
    }

    //Make sure the instance exists
    public void testAccessPostingsExists() {
        System.out.println("\nStarting testAccessPostingsExists");

        assertNotNull(aPostings.getPostingById("0"));

        System.out.println("Finished testAccessPostingsExists");
    }

    public void testNullValues() {
        System.out.println("\nStarting testNullValues");
        Posting p;

        aPostings.getPostings(postings, null);
        assertEquals(5, postings.size());
        postings.clear();

        assertNull(aPostings.getPostingById("1000"));

        assertNull(aPostings.getPostingsByUserId(postings, null));

        assertEquals(QueryResult.FAILURE, aPostings.insertPosting(null));

        assertEquals(QueryResult.FAILURE, aPostings.deletePosting(null));

        System.out.println("Finished testNullValues");
    }

    //Test invalid data - ex) inserting an already-existing post, or updating a non-existent one
    public void testBadValues() {
        System.out.println("\nStarting testBadValues");

        aPostings.getPostings(postings, "-1");
        assertEquals(5, postings.size());
        postings.clear();

        assertNull(aPostings.getPostingById("-1"));

        aPostings.getPostingsByUserId(postings, "-1");
        assertEquals(0, postings.size());
        postings.clear();

        assertEquals(QueryResult.FAILURE, aPostings.insertPosting(postExists));

        assertEquals(QueryResult.FAILURE, aPostings.updatePosting(postDNE));

        assertEquals(QueryResult.FAILURE, aPostings.deletePosting(postDNE));

        System.out.println("Finished testBadValues");
    }

    public void testExistingPosts() {
        System.out.println("\nStarting testExistingPostings");
        Posting pOld;
        Posting pNew;
        String updated = "TEST UPDATE";

        aPostings.getPostings(postings, "0");
        assertEquals(2, postings.size());
        pOld = postings.get(0);
        postings.clear();

        assertNotNull(aPostings.getPostingById("0"));

        aPostings.getPostingsByUserId(postings, "0");
        assertEquals(3, postings.size());

        assertEquals(QueryResult.SUCCESS , aPostings.insertPosting(postDNE));
        assertNotNull(aPostings.getPostingById(postDNE.getPostingId()));

        pNew = new Posting(pOld.getPostingId(), pOld.getTitle(), pOld.getUser(), pOld.getPrice(), pOld.getLocation(), pOld.getType(), updated);
        assertEquals(QueryResult.SUCCESS , aPostings.updatePosting(pNew));
        assertEquals(updated, aPostings.getPostingById(pOld.getPostingId()).getDescription());
        aPostings.updatePosting(pOld);

        assertEquals(QueryResult.SUCCESS , aPostings.deletePosting(postDNE));
        assertNull( aPostings.getPostingById(postDNE.getPostingId()));

        System.out.println("Finished testExistingPostings");
    }

    public void tearDown () {
        Main.shutDown();
    }
}
