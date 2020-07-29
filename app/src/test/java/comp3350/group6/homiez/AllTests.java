package comp3350.group6.homiez;

import junit.framework.Test;
import junit.framework.TestSuite;

import comp3350.group6.homiez.business.AccessMatchesTest;
import comp3350.group6.homiez.business.AccessPostingsTest;
import comp3350.group6.homiez.business.AccessRequestsTest;
import comp3350.group6.homiez.business.AccessUserTest;
import comp3350.group6.homiez.business.AgeCompatibilityTest;
import comp3350.group6.homiez.business.CompatibilityController;
import comp3350.group6.homiez.business.CompatibilityControllerTest;
import comp3350.group6.homiez.business.InterestCompatibilityTest;
import comp3350.group6.homiez.business.MatchingTest;
import comp3350.group6.homiez.objects.MatchTest;
import comp3350.group6.homiez.objects.PostingTest;
import comp3350.group6.homiez.objects.RequestTest;
import comp3350.group6.homiez.objects.UserTest;
import comp3350.group6.homiez.persistence.DataAccessStub;
import comp3350.group6.homiez.persistence.DataAccessStubTest;

import static org.junit.Assert.*;

public class AllTests {
    public static TestSuite suite;

    public static Test suite() {
        suite = new TestSuite("All tests");
        testObjects();
        testBusiness();
        testPersistence();
        return suite;
    }

    private static void testObjects() {
        suite.addTestSuite(UserTest.class);
        suite.addTestSuite(MatchTest.class);
        suite.addTestSuite(RequestTest.class);
        suite.addTestSuite(PostingTest.class);
    }

    private static void testBusiness() {
        suite.addTestSuite(AccessUserTest.class);
        suite.addTestSuite(AccessPostingsTest.class);
        suite.addTestSuite(AccessRequestsTest.class);
        suite.addTestSuite(AccessMatchesTest.class);
        suite.addTestSuite(MatchingTest.class);
        suite.addTestSuite(InterestCompatibilityTest.class);
        suite.addTestSuite(AgeCompatibilityTest.class);
        suite.addTestSuite(CompatibilityControllerTest.class);

    }
    private static void testPersistence()
    {
        suite.addTestSuite(DataAccessStubTest.class);
    }

}
