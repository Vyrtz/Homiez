package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.group6.homiez.application.Shared.QueryResult;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.business.AccessMatches;
import comp3350.group6.homiez.objects.Match;

public class AccessMatchesSeamTest extends TestCase {

    private AccessMatches accessMatches;

    public void setUp() {
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);

        accessMatches = new AccessMatches();
    }

    public void testValidGetMatchesForUser() {
        System.out.println("Starting testValidGetMatchesForUser");
        ArrayList<Match> matches = new ArrayList<>();
        assertEquals(QueryResult.SUCCESS, accessMatches.getMatchesForUser(matches, "3"));
        assertEquals(2, matches.size()); //2 Default matches for user 3 at the default state of the db

        System.out.println("Finished testValidGetMatchesForUser");
    }

    public void testInvalidGetMatchesForUser() {
        System.out.println("Starting testInvalidGetMatchesForUser");
        ArrayList<Match> matches = new ArrayList<>();
        accessMatches.getMatchesForUser(matches, "DNE");
        assertEquals(0, matches.size()); //Should have no matches when we pass a user that doesn't exist
        System.out.println("Finished testInvalidGetMatchesForUser");
    }

    public void testValidGetMatchesForPosting() {
        System.out.println("Starting testValidGetMatchesForPosting");
        ArrayList<Match> matches = new ArrayList<>();
        assertEquals(QueryResult.SUCCESS, accessMatches.getMatchesForPosting(matches, "3"));
        assertEquals(1, matches.size()); //Only 1 match for postingId3
        System.out.println("Finished testValidGetMatchesForPosting");
    }

    public void testInvalidGetMatchesForPosting() {
        System.out.println("Starting testInvalidGetMatchesForPosting");
        ArrayList<Match> matches = new ArrayList<>();
        accessMatches.getMatchesForPosting(matches, "DNE");
        assertEquals(0, matches.size()); //No postings should be grabbed because the postingID was invalid
        System.out.println("Finished testInvalidGetMatchesForPosting");
    }

    public void testValidInsertMatch() {
        System.out.println("Starting testValidInsertMatch");
        Match match = new Match("0", "3"); //User 0 to PostingID 3 (Owned by user 1)
        assertEquals(QueryResult.SUCCESS, accessMatches.insertMatch(match));
        accessMatches.deleteMatch(match);
        System.out.println("Finished testInvalidInsertMatch");
    }

    public void testInvalidInsertMatch() {
        System.out.println("Starting testInvalidInsertMatch");
        Match match = new Match("0", "DNE");
        assertEquals(QueryResult.FAILURE, accessMatches.insertMatch(match));
        System.out.println("Finished testInvalidInsertMatch");
    }

    public void testValidDeleteMatch() {
        System.out.println("Starting testValidDeleteMatch");
        ArrayList<Match> matches = new ArrayList<>();
        accessMatches.getMatchesForUser(matches, "3");
        Match match = matches.get(0);

        assertEquals(QueryResult.SUCCESS, accessMatches.deleteMatch(match));
        accessMatches.insertMatch(match);

        System.out.println("Finished testValidDeleteMatch");
    }

    public void testInvalidDeleteMatch() {
        System.out.println("Starting testInvalidDeleteMatch");
        Match match = new Match("0", "DNE");
        assertEquals(QueryResult.WARNING, accessMatches.deleteMatch(match));
        System.out.println("Finished testInvalidDeleteMatch");
    }


}
