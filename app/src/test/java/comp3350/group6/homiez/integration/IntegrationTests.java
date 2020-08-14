package comp3350.group6.homiez.integration;

import junit.framework.Test;
import junit.framework.TestSuite;

public class IntegrationTests {

    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Integration tests");

        suite.addTestSuite(AccessUserSeamTest.class);
        suite.addTestSuite(AccessRequestsSeamTest.class);
        suite.addTestSuite(AccessPostingsSeamTest.class);
        suite.addTestSuite(AccessMatchesSeamTest.class);
        suite.addTestSuite(MatchingSeamTest.class);
        suite.addTestSuite(DataAccessHSQLDBTest.class);
        return suite;

    }
}
