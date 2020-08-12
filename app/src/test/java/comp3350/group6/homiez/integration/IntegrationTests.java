package comp3350.group6.homiez.integration;

import junit.framework.Test;
import junit.framework.TestSuite;

public class IntegrationTests {

    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Integration tests");

        //TODO: Modify the addTest suite calls once the tests are written
        suite.addTestSuite(BusinessPersistenceSeamTest.class);
        //suite.addTestSuite();
        return suite;

    }
}
