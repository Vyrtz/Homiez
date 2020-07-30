package comp3350.group6.homiez.business;

import junit.framework.TestCase;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class InterestCompatibilityTest extends TestCase {

    Compatibility c;
    Posting p;
    User u;
    AccessPostings accessPostings;

    public void setUp() {
        Main.startUp();

        c = new InterestCompatibility();
        u = new User("test", "test",1, "test", 100, "test");
        p = new Posting("test", "test", new User("test2"), 2, "test", "test", "test");
        accessPostings = new AccessPostings();
        accessPostings.insertPosting(p);
    }

    public void testNullValues() {
        System.out.println("\nStarting testNullValues");

        assertEquals(-1.0, c.calculateCompatibility(null, new Posting("p")));
        assertEquals(-1.0, c.calculateCompatibility(null, new User("u")));
        assertEquals(-1.0, c.calculateCompatibility(null, (Posting) null));
        assertEquals(-1.0, c.calculateCompatibility(null, (User) null));

        System.out.println("Finished testNullValues");

    }

    public void testEmptyAttachedUserList() {
        System.out.println("\nStarting testEmptyAttachedUserList");
        assertEquals(0.0, c.calculateCompatibility(u, p));
        System.out.println("Finished testEmptyAttachedUserList");
    }

    public void testNullAttachedUser() {
        System.out.println("\nStarting testNullAttachedUser");
        p.addAttachedUser(null);
        assertEquals(0.0, c.calculateCompatibility(u, p));
        System.out.println("Finished testNullAttachedUser");
    }

    public void testUserWithNoInterest() {
        System.out.println("\nStarting testUserWithNoInterest");
        // posting already has the main user attached
        assertEquals(0.0, c.calculateCompatibility(u, p));
        // attach one more user
        p.addAttachedUser(new User("test3"));
        assertEquals(0.0, c.calculateCompatibility(u, p));
        System.out.println("Finished testUserWithNoInterest");
    }
    public void testUserWithNullInterest() {
        System.out.println("\nStarting testUserWithNullInterest");
        // posting already has the main user attached
        p.getUser().addUniqueInterest(null);

        assertEquals(0.0, c.calculateCompatibility(u, p));
        assertEquals(0.0, c.calculateCompatibility(u,p.getUser()));

        User us = new User("test3");
        us.addUniqueInterest(null);
        p.addAttachedUser(us);

        assertEquals(0.0, c.calculateCompatibility(u, p));

        System.out.println("Finished testUserWithNullInterest");
    }

    public void testUserWithMissingInterest() {
        System.out.println("\nStarting testUserWithMissingInterest");
        // posting already has the main user attached
        p.getUser().addUniqueInterest(new Interest(""));

        assertEquals(0.0, c.calculateCompatibility(u, p));
        assertEquals(0.0, c.calculateCompatibility(u,p.getUser()));

        User us = new User("test3");
        us.addUniqueInterest(new Interest(""));
        p.addAttachedUser(us);

        assertEquals(0.0, c.calculateCompatibility(u, p));

        System.out.println("Finished testUserWithMissingInterest");
    }

    public void testValidInput() {
        System.out.println("\nStarting testValidInput");
        p.getUser().addUniqueInterest(new Interest("Ice skating"));
        u.addUniqueInterest(new Interest("iceskating"));

        assertEquals(100.00, c.calculateCompatibility(u,p ));
        assertEquals(100.00, c.calculateCompatibility(u,p.getUser() ));

        System.out.println("Finished testValidInput");
    }

    public void testMultipleInterests() {

        System.out.println("\nStarting testMultipleInterests");
        p.getUser().addUniqueInterest(new Interest("Ice skating"));
        p.getUser().addUniqueInterest(new Interest("boxing"));
        p.getUser().addUniqueInterest(new Interest("coding"));

        u.addUniqueInterest(new Interest("iceskating"));
        u.addUniqueInterest(new Interest("CODING"));

        assertEquals(67, Math.round(c.calculateCompatibility(u,p )));
        assertEquals(67, Math.round(c.calculateCompatibility(u,p.getUser() )));

        System.out.println("Finished testMultipleInterests");
    }

    public void testMixedInterests() {

        System.out.println("\nStarting testMultipleInterests");
        p.getUser().addUniqueInterest(new Interest("Ice skating"));
        p.getUser().addUniqueInterest(null);
        p.getUser().addUniqueInterest(new Interest(""));

        u.addUniqueInterest(new Interest("iceskating"));
        u.addUniqueInterest(new Interest("CODING"));

        assertEquals(50, Math.round(c.calculateCompatibility(u,p )));
        assertEquals(50, Math.round(c.calculateCompatibility(u,p.getUser() )));

        System.out.println("Finished testMultipleInterests");

    }

    public void testMultipleAttachedUsers() {

        System.out.println("\nStarting testMultipleAttachedUsers");
        p.getUser().addUniqueInterest(new Interest("Ice skating"));
        p.getUser().addUniqueInterest(new Interest("Anime"));

        User two = new User("two");
        two.addUniqueInterest(new Interest("coding"));
        two.addUniqueInterest(new Interest(""));

        User three = new User("three");
        three.addUniqueInterest(null);
        three.addUniqueInterest(new Interest("not coding"));

        p.addAttachedUser(two);
        p.addAttachedUser(three);

        u.addUniqueInterest(new Interest("iceskating"));
        u.addUniqueInterest(new Interest("CODING"));

        assertEquals(28, Math.round(c.calculateCompatibility(u,p )));
        assertEquals(33, Math.round(c.calculateCompatibility(u,p.getUser() )));
        assertEquals(50, Math.round(c.calculateCompatibility(u,two )));
        assertEquals(0, Math.round(c.calculateCompatibility(u,three )));

        System.out.println("Finished testMultipleAttachedUsers");

    }

    public void testHundredPercent() {
        System.out.println("\nStarting testHundredPercent");

        p.getUser().addUniqueInterest(new Interest("CoDinG "));

        User two = new User("two");
        two.addUniqueInterest(new Interest("   coding    "));

        User three = new User("three");
        three.addUniqueInterest(new Interest("Coding"));

        p.addAttachedUser(two);
        p.addAttachedUser(three);

        u.addUniqueInterest(new Interest("CODING"));

        assertEquals(100, Math.round(c.calculateCompatibility(u,p )));
        assertEquals(100, Math.round(c.calculateCompatibility(u,p.getUser() )));
        assertEquals(100, Math.round(c.calculateCompatibility(u,two )));
        assertEquals(100, Math.round(c.calculateCompatibility(u,three )));

        System.out.println("Finished testHundredPercent");
    }

    public void testZeroPercent () {
        System.out.println("\nStarting testZeroPercent");

        p.getUser().addUniqueInterest(new Interest("not CoDinG "));

        User two = new User("two");
        two.addUniqueInterest(new Interest("not coding"));

        User three = new User("three");
        three.addUniqueInterest(new Interest("not coding"));

        p.addAttachedUser(two);
        p.addAttachedUser(three);

        u.addUniqueInterest(new Interest("CODING"));

        assertEquals(0, Math.round(c.calculateCompatibility(u,p )));
        assertEquals(0, Math.round(c.calculateCompatibility(u,p.getUser() )));
        assertEquals(0, Math.round(c.calculateCompatibility(u,two )));
        assertEquals(0, Math.round(c.calculateCompatibility(u,three )));

        System.out.println("Finished testZeroPercent");
    }
}
