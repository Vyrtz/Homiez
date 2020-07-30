package comp3350.group6.homiez.business;

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
        newPost = new Posting("10", "TEST TITLE", u, 2500, "TEST LOC", "TEST TYPE", "TEST DESC");
        postExists = new Posting("11", "UPDATED", u, 1000,  "Pembina", "Condo", "A beautiful riverside condo in the heart of Pembina. Great view of the surrounding area.");
        updatePost = new Posting("10", "TEST UPDATE", u, 2500, "TEST LOC", "TEST TYPE", "TEST DESC");
        aPostings.insertPosting(postExists);
        aPostings.deletePosting(newPost); //remove old test post from the db if exists
    }

    //Make sure the instance exists
    public void testAccessPostings1() {
        System.out.println("\nStarting testAccessPostings1");

        assertNotNull(aPostings);

        System.out.println("Finished testAccessPostings1");
    }


    //test single-post operations with a post that is in the "database"
    public void testAccessPostingsExisting() {
        System.out.println("\nStarting testAccessPostingsExisting");

        //retrieve posting
        assertEquals(postExists, aPostings.getPostingById("11"));

        //update Posting
        aPostings.updatePosting(postExists);
        assertTrue("UPDATED".equals(aPostings.getPostingById("11").getTitle()));

        //deletePosting
        aPostings.deletePosting(postExists);
        assertNull(aPostings.getPostingById("11"));

        System.out.println("Finished testAccessPostingsExisting");
    }

    //test single-post operations with a post that is not in the "database"
    public void testAccessPostingsNotExisting() {
        System.out.println("\nStarting testAccessPostingsNotExisting");

        //retrieve posting
        assertNull(aPostings.getPostingById("10"));

        //update posting
        assertNull(aPostings.updatePosting(newPost));

        //delete Posting
        assertNull(aPostings.deletePosting(newPost));

        //insert Posting
        aPostings.insertPosting(newPost);
        assertNotNull(aPostings.getPostingById("10"));

        System.out.println("Finished testAccessPostingsExisting");
    }


    //test operations dependent on user id
    public void testUserPostings() {
        System.out.println("\nStarting testUserPostings");

        aPostings.getPostingsByUserId(postings, "0");
        assertEquals(3, postings.size());
        postings.clear();

        aPostings.getPostingsByUserId(postings, "1");
        assertEquals(1, postings.size());
        postings.clear();

        aPostings.insertPosting(newPost);
        aPostings.getPostingsByUserId(postings, "5");
        assertEquals(0, postings.size());
        postings.clear();


        aPostings.getPostingsByUserId(postings, "0");
        assertEquals(3, postings.size());
        postings.clear();

        aPostings.getPostingsByUserId(postings, "10");
        assertEquals(0, postings.size());

        aPostings.insertPosting(newPost);
        aPostings.getPostingsByUserId(postings, "3");
        assertEquals(2, postings.size());

        System.out.println("Finished testUserPostings");
    }

    public void tearDown () {
        Main.shutDown();
    }
}
