package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AccessPostingsTest extends TestCase {

    User u;
    AccessPostings aPostings;
    ArrayList<Posting> postings;
    Posting newPost;
    Posting postExists;
    Posting updatePost;


    public AccessPostingsTest(String arg0){
        super(arg0);
    }

    public void init(){
        Main.startUp();
        u = new User ("3","Vinh", 18, "m");
        aPostings = new AccessPostings();
        postings = new ArrayList<Posting>();
        postings.removeAll(postings);
        newPost = new Posting("10", "TEST TITLE", u, 2500, "TEST LOC", "TEST TYPE", "TEST DESC");
        postExists = new Posting("0", "UPDATED", u, 1000,  "Pembina", "Condo", "A beautiful riverside condo in the heart of Pembina. Great view of the surrounding area.");
        updatePost = new Posting("10", "TEST UPDATE", u, 2500, "TEST LOC", "TEST TYPE", "TEST DESC");
    }



    //Make sure the instance exists
    public void testAccessPostings1(){
        System.out.println("\nStarting testAccessPostings1");
        init();

        assertNotNull(aPostings);

        System.out.println("Finished testAccessPostings1");
    }


    //test single-post operations with a post that is in the "database"
    public void testAccessPostingsExisting(){
        System.out.println("\nStarting testAccessPostingsExisting");
        init();

        //retrieve posting
        assertNotNull(aPostings.getPostingById("0"));

        //insert Posting
        assertTrue("Failure".equals(aPostings.insertPosting(postExists)));

        //update Posting
        aPostings.updatePosting(postExists);
        assertTrue("UPDATED".equals(aPostings.getPostingById("0").getTitle()));

        //deletePosting
        aPostings.deletePosting(postExists);
        assertNull(aPostings.getPostingById("0"));

        Main.shutDown();
        System.out.println("Finished testAccessPostingsExisting");
    }

    //test single-post operations with a post that is not in the "database"
    public void testAccessPostingsNotExisting(){
        System.out.println("\nStarting testAccessPostingsNotExisting");
        init();

        //retrieve posting
        assertNull(aPostings.getPostingById("10"));

        //update posting
        assertTrue("Failure".equals(aPostings.updatePosting(newPost)));

        //delete Posting
        assertTrue("Failure".equals(aPostings.deletePosting(newPost)));

        //insert Posting
        aPostings.insertPosting(newPost);
        assertNotNull(aPostings.getPostingById("10"));

        Main.shutDown();
        System.out.println("Finished testAccessPostingsExisting");
    }


    //test operations dependent on user id
    public void testUserPostings(){
        System.out.println("\nStarting testUserPostings");
        init();

        aPostings.getPostings(postings, "0");
        assertEquals(2, postings.size());
        postings.removeAll(postings);

        aPostings.getPostings(postings, "1");
        assertEquals(4, postings.size());
        postings.removeAll(postings);

        aPostings.insertPosting(newPost);
        aPostings.getPostings(postings, "5");
        assertEquals(6, postings.size());
        postings.removeAll(postings);


        aPostings.getPostingsByUserId(postings, "0");
        assertEquals(3, postings.size());
        postings.removeAll(postings);

        aPostings.getPostingsByUserId(postings, "10");
        assertEquals(0, postings.size());

        aPostings.insertPosting(newPost);
        aPostings.getPostingsByUserId(postings, "3");
        assertEquals(1, postings.size());

        Main.shutDown();
        System.out.println("Finished testUserPostings");
    }



}