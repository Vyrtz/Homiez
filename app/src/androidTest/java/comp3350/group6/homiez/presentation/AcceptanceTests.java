package comp3350.group6.homiez.presentation;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AcceptanceTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Acceptance tests");
        //suite.addTestSuite(DataAccessHSQLDBTest.class);
        return suite;

    }
}
