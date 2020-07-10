package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AccessPostingsTest extends TestCase {

    public void testAccessPostings1(){
        System.out.println("\nStarting testAccessPostings1");

        Main.startUp();
        User u = new User("0", "Abhi", 20, "m");
        AccessPostings aPostings = new AccessPostings();
        ArrayList<Posting> postings = new ArrayList<Posting>();
        Posting newPost = new Posting("10", "TEST TITLE", u, 2500, "TEST LOC", "TEST TYPE", "TEST DESC");
        Posting updatePost = new Posting("10", "TEST UPDATE", u, 2500, "TEST LOC", "TEST TYPE", "TEST DESC");

        aPostings.getPostings(postings, "0");
        assertTrue("3".equals(postings.get(0).getPostingId()));
        assertTrue("4".equals(postings.get(1).getPostingId()));
        postings.removeAll(postings);

        assertNotNull(aPostings.getPostingById("0"));
        assertNull(aPostings.getPostingById("11"));

        aPostings.getPostingsByUserId(postings, "0");
        assertTrue("0".equals(postings.get(0).getPostingId()));
        assertTrue("1".equals(postings.get(1).getPostingId()));
        postings.removeAll(postings);

        aPostings.insertPosting(newPost);
        assertNotNull(aPostings.getPostingById("10"));

        aPostings.updatePosting(updatePost);
        assertTrue("TEST UPDATE".equals(aPostings.getPostingById("10").getTitle()));

        aPostings.deletePosting(updatePost);
        assertNull(aPostings.getPostingById("10"));


        System.out.println("Finished testAccessPostings1");
    }
}
