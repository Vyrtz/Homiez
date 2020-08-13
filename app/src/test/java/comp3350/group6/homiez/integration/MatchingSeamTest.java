package comp3350.group6.homiez.integration;

import junit.framework.TestCase;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.application.Services;
import comp3350.group6.homiez.business.AccessMatches;
import comp3350.group6.homiez.business.AccessRequests;
import comp3350.group6.homiez.business.Matching;

public class MatchingSeamTest extends TestCase {

    private Matching matching;
    private AccessRequests accessRequests;
    private AccessMatches accessMatches;

    public void setUp() {
        Services.closeDataAccess();
        Services.createDataAccess(Main.dbName);

        matching = new Matching();
        accessRequests = new AccessRequests();
        accessMatches = new AccessMatches();
    }

    public void testValidAcceptRequest() {

    }

    public void testInvalidAcceptRequest() {

    }

    public void testValidDeclineRequest() {

    }

    public void testInvalidDeclineRequest() {

    }

    public void testValidSendRequest() {

    }

    public void testInvalidSendRequest() {

    }


}
