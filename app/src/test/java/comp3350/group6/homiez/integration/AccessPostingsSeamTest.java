package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.objects.Posting;

public class AccessPostingsSeamTest extends TestCase {

    private AccessPostings accessPostings;

    public void setUp() {
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);

        accessPostings = new AccessPostings();

    }

    public void testValidGetPostings() {
        System.out.println("Starting testValidGetPostings");

        //Fetch existing posts
        ArrayList<Posting> postings = new ArrayList<>();
        assertEquals(QueryResult.SUCCESS, accessPostings.getPostings(postings, "0"));
        assertTrue(postings.size() == 2); //Default 2 postings that aren't owned by user 0


        System.out.println("Finished testValidGetPostings");
    }

    public void testInvalidGetPostings() {
        System.out.println("Starting testInvalidGetPostings");
        ArrayList<Posting> postings = new ArrayList<>();
        accessPostings.getPostings(postings, "DNE");
        assertTrue(postings.size() == 0);

        System.out.println("Finished testInvalidGetPostings");
    }

    public void testValidGetPostingById() {
        System.out.println("Starting testValidGetPostingById");

        System.out.println("Finished testValidGetPostingById");
    }

    public void testInvalidGetPostingById() {
        System.out.println("Starting testInvalidGetPostingById");

        System.out.println("Finished testInvalidGetPostingById");
    }

    public void testValidGetPostingsByUserId() {
        System.out.println("Starting testValidGetPostingsByUserId");

        System.out.println("Finished testValidGetPostingsByUserId");
    }

    public void testInvalidGetPostingsByUserId() {
        System.out.println("Starting testInvalidGetPostingsByUserId");

        System.out.println("Finished testInvalidGetPostingsByUserId");
    }

    public void testValidInsertPosting() {
        System.out.println("Starting testValidInsertPosting");

        System.out.println("Finished testValidInsertPosting");
    }

    public void testInvalidInsertPosting() {
        System.out.println("Starting testInvalidInsertPosting");

        System.out.println("Finished testInvalidInsertPosting");
    }

    public void testValidUpdatePosting() {
        System.out.println("Starting testValidUpdatePosting");

        System.out.println("Finished testValidUpdatePosting");
    }

    public void testInvalidUpdatePosting() {
        System.out.println("Starting testInvalidUpdatePosting");

        System.out.println("Finished testInvalidUpdatePosting");
    }

    public void testValidDeletePosting() {
        System.out.println("Starting testValidDeletePosting");

        System.out.println("Finished testValidDeletePosting");
    }

    public void testInvalidDeletePosting() {
        System.out.println("Starting testInvalidDeletePosting");

        System.out.println("Finished testInvalidDeletePosting");
    }
}
