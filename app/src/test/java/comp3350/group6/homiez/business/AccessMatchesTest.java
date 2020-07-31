package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.persistence.DataAccessStub;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class AccessMatchesTest extends TestCase {

    private AccessMatches aMatches;
    public void setUp() {
        Services.createDataAccess(new DataAccessStub("test"));
        aMatches = new AccessMatches();
    }

    public void testAccessMatchesValidConstruction() {
        System.out.println("\nStarting testAccessMatchesConstruction");
        ArrayList<Match> matches = new ArrayList<Match>();
        aMatches.getMatchesForUser(matches, "0");
        assertEquals(0, matches.size(), 0.01);
        System.out.println("Finished testAccessMatchesConstruction");
    }
    public void testAccessMatchesInvalidConstruction() {
        Services.closeDataAccess();
        System.out.println("\nStarting testAccessMatchesInvalidConstruction");
        aMatches = new AccessMatches();
        System.out.println("Finished testAccessMatchesInvalidConstruction");
    }

    public void testNullValues() {
        AccessMatches aMatches = new AccessMatches();
        ArrayList<Match> matches = new ArrayList<Match>();
        Match m = new Match("0", "4");

        aMatches.getMatchesForPosting(matches, "0");
        assertEquals(1, matches.size(), 0.01);
        matches.removeAll(matches);
        aMatches.getMatchesForPosting(matches, "4");
        assertEquals(0, matches.size(), 0.01);

        aMatches.getMatchesForUser(matches, "3");
        assertEquals(2, matches.size(), 0.01);
        matches.removeAll(matches);
        aMatches.getMatchesForUser(matches, "0");
        assertEquals(0, matches.size(), 0.01);

        aMatches.insertMatch(m);
        aMatches.getMatchesForUser(matches, "0");
        assertEquals(1, matches.size(), 0.01);
        matches.removeAll(matches);

        aMatches.deleteMatch(m);
        aMatches.getMatchesForUser(matches, "0");
        assertEquals(0, matches.size(), 0.01);


        System.out.println("Finished testAccessMatches1");

    }
//
//    public String getMatchesForUser(List<Match> matches, String userId) {
//        return dataAccess.getMatchesForUser(matches, userId);
//    }
//
//    public String getMatchesForPosting(List<Match> matches, String postingId) {
//        return dataAccess.getMatchesForPosting(matches, postingId);
//    }
//    public String insertMatch(Match currentMatch)
//    {
//        return dataAccess.insertMatch(currentMatch);
//    }
//
//    public String deleteMatch(Match currentMatch)
//    {
//        return dataAccess.deleteMatch(currentMatch);
//    }
}
