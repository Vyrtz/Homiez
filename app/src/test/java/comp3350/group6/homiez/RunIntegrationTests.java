package comp3350.group6.homiez;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.group6.homiez.integration.IntegrationTests;

public class RunIntegrationTests {

    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("Integration tests");
        suite.addTest(IntegrationTests.suite());
        return suite;
    }
}
