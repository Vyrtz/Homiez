package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.business.AccessPostings;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

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
        assertTrue(postings.size() == 5); //5 default postings - passed user is no one so all posts are returned

        System.out.println("Finished testInvalidGetPostings");
    }

    public void testValidGetPostingById() {
        System.out.println("Starting testValidGetPostingById");
        Posting posting = accessPostings.getPostingById("0");
        assertNotNull(posting); //Check that we pulled it correctly
        assertEquals("0", posting.getUser().getUserId()); //Check that the owner is User 0

        System.out.println("Finished testValidGetPostingById");
    }

    public void testInvalidGetPostingById() {
        System.out.println("Starting testInvalidGetPostingById");
        assertNull(accessPostings.getPostingById("DNE")); //Test an invalid postingID
        System.out.println("Finished testInvalidGetPostingById");
    }

    public void testValidGetPostingsByUserId() {
        System.out.println("Starting testValidGetPostingsByUserId");
        ArrayList<Posting> postings = new ArrayList<>();
        assertEquals(QueryResult.SUCCESS, accessPostings.getPostingsByUserId(postings, "0"));
        assertTrue(postings.size() == 3); //User 0 has 3 posts, check that we retrieved them all
        System.out.println("Finished testValidGetPostingsByUserId");
    }

    public void testInvalidGetPostingsByUserId() {
        System.out.println("Starting testInvalidGetPostingsByUserId");
        ArrayList<Posting> postings = new ArrayList<>();
        accessPostings.getPostingsByUserId(postings, "DNE"); //Retrieve a list of postings from a non existent user
        assertTrue(postings.size() == 0); //the posting list should have nothing added to it
        System.out.println("Finished testInvalidGetPostingsByUserId");
    }

    public void testValidInsertPosting() {
        System.out.println("Starting testValidInsertPosting");
        Posting post = createNewPost("UniqueID", "0"); //New valid posting
        assertEquals(QueryResult.SUCCESS, accessPostings.insertPosting(post));
        accessPostings.deletePosting(post);
        System.out.println("Finished testValidInsertPosting");
    }

    public void testInvalidInsertPosting() {
        System.out.println("Starting testInvalidInsertPosting");
        Posting post = createNewPost("uniqueID", "DNE"); //Create a post with a non-existent user
        assertEquals(QueryResult.FAILURE, accessPostings.insertPosting(post));
        System.out.println("Finished testInvalidInsertPosting");
    }

    public void testValidUpdatePosting() {
        System.out.println("Starting testValidUpdatePosting");
        Posting post = accessPostings.getPostingById("0"); //Grab an existing post
        assertEquals(QueryResult.SUCCESS, accessPostings.updatePosting(post)); //Update the post with a new version of itself
        System.out.println("Finished testValidUpdatePosting");
    }

    public void testInvalidUpdatePosting() {
        System.out.println("Starting testInvalidUpdatePosting");
        Posting post = createNewPost("DNE", "0");
        assertEquals(QueryResult.FAILURE, accessPostings.updatePosting(post)); //Attempt to update a nonexistent post
        System.out.println("Finished testInvalidUpdatePosting");
    }

    public void testValidDeletePosting() {
        System.out.println("Starting testValidDeletePosting");
        Posting post = accessPostings.getPostingById("2");
        assertEquals(QueryResult.SUCCESS, accessPostings.deletePosting(post));
        assertNull(accessPostings.getPostingById("2")); //Confirm that its gone
        accessPostings.insertPosting(post); //Cleanup
        System.out.println("Finished testValidDeletePosting");
    }

    public void testInvalidDeletePosting() {
        System.out.println("Starting testInvalidDeletePosting");
        Posting post = createNewPost("DNE", "0");
        assertEquals(QueryResult.WARNING, accessPostings.deletePosting(post));
        System.out.println("Finished testInvalidDeletePosting");
    }

    private Posting createNewPost(String postingID, String userID){
        User existingUser = new User(userID);
        Posting newPost = new Posting(postingID, "title", existingUser, 500.0, "location", "type", "description");
        return newPost;
    }
}
