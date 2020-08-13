package comp3350.group6.homiez.business;

import comp3350.group6.homiez.application.Constants.QueryResult;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.persistence.DataAccessStub;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AccessMatchesTest extends TestCase {

    private AccessMatches aMatches;
    private ArrayList<Match> matches;
    public void setUp() {
        Services.createDataAccess(new DataAccessStub("test"));
        aMatches = new AccessMatches();
        matches = new ArrayList<Match>();
    }

    public void testAccessMatchesValidConstruction() {
        System.out.println("\nStarting testAccessMatchesConstruction");

        aMatches.getMatchesForUser(matches, "0");
        assertEquals(0, matches.size(), 0.01);

        System.out.println("Finished testAccessMatchesConstruction");
    }

    public void testNullValues() {
        System.out.println("\nStarting testNullValues");

        aMatches.getMatchesForPosting(matches, null);
        assertEquals(0, matches.size(), 0.01);
        matches.clear();

        aMatches.getMatchesForUser(matches, null);
        assertEquals(0, matches.size(), 0.01);
        matches.clear();

        assertEquals(QueryResult.FAILURE, aMatches.insertMatch( null));
        assertEquals(QueryResult.FAILURE, aMatches.deleteMatch( null));

        System.out.println("Finished testNullValues");
    }
    public void testBadValues() {
        System.out.println("\nStarting testBadValues");

        aMatches.getMatchesForPosting(matches, "-10");
        assertEquals(0, matches.size(), 0.01);
        matches.clear();

        aMatches.getMatchesForUser(matches, "-10");
        assertEquals(0, matches.size(), 0.01);
        matches.clear();

        assertEquals(QueryResult.FAILURE, aMatches.deleteMatch( new Match("09", "100")));
        System.out.println("Finished testBadValues");
    }

    public void testNonExistingMatches() {
        System.out.println("\nStarting testNonExistingMatches");

        Match m = new Match("0", "4");
        aMatches.getMatchesForPosting(matches, "4");
        assertEquals(0, matches.size(), 0.01);

        assertEquals(QueryResult.FAILURE, aMatches.deleteMatch( new Match("09", "100")));

        aMatches.getMatchesForUser(matches, "0");
        assertEquals(0, matches.size(), 0.01);
        matches.clear();

        aMatches.insertMatch(m);
        aMatches.getMatchesForUser(matches, "0");
        assertEquals(1, matches.size(), 0.01);
        matches.clear();

        aMatches.deleteMatch(m);
        aMatches.getMatchesForUser(matches, "0");
        assertEquals(0, matches.size(), 0.01);

        System.out.println("Finished testNonExistingMatches");

    }
    public void testExistingMatches() {
        System.out.println("\nStarting testExistingMatches");

        Match m = new Match("0", "4");

        aMatches.getMatchesForUser(matches, "3");
        assertEquals(2, matches.size(), 0.01);
        m = matches.get(0);
        matches.clear();

        aMatches.deleteMatch(m);
        aMatches.getMatchesForUser(matches, "3");
        assertEquals(1, matches.size(), 0.01);


        System.out.println("Finished testExistingMatches");

    }
}
