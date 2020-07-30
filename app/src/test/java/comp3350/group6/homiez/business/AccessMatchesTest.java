package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.DataAccessStub;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AccessMatchesTest extends TestCase {

    public void testAccessMatches1() {
        System.out.println("\nStarting testAccessMatches1");

        Services.createDataAccess(new DataAccessStub("test"));

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
}
