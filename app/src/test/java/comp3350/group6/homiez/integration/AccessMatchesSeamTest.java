package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import java.util.ArrayList;

import comp3350.group6.homiez.application.Constants.QueryResult;
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
        System.out.println(matches.size());

        System.out.println("Finished testValidGetMatchesForUser");
    }

    public void testInvalidGetMatchesForUser() {
        System.out.println("Starting testInvalidGetMatchesForUser");

        System.out.println("Finished testInvalidGetMatchesForUser");
    }

    public void testValidGetMatchesForPosting() {
        System.out.println("Starting testValidGetMatchesForPosting");

        System.out.println("Finished testValidGetMatchesForPosting");
    }

    public void testInvalidGetMatchesForPosting() {
        System.out.println("Starting testInvalidGetMatchesForPosting");

        System.out.println("Finished testInvalidGetMatchesForPosting");
    }

    public void testValidInsertMatch() {
        System.out.println("Starting testValidInsertMatch");

        System.out.println("Finished testInvalidInsertMatch");
    }

    public void testInvalidInsertMatch() {
        System.out.println("Starting testInvalidInsertMatch");

        System.out.println("Finished testInvalidInsertMatch");
    }

    public void testValidDeleteMatch() {
        System.out.println("Starting testValidDeleteMatch");

        System.out.println("Finished testValidDeleteMatch");
    }

    public void testInvalidDeleteMatch() {
        System.out.println("Starting testInvalidDeleteMatch");

        System.out.println("Finished testInvalidDeleteMatch");
    }


}
