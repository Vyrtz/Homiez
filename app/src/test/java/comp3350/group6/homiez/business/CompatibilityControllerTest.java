package comp3350.group6.homiez.business;

import junit.framework.TestCase;

import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class CompatibilityControllerTest extends TestCase {

    Compatibility c;
    Posting p;
    User u;
    AccessPostings accessPostings;

    public void setUp() {
        Main.startUp();

        c = new CompatibilityController();
        u = new User("test", "test",1, "test");
        p = new Posting("test", "test", new User("test", "test",100, "test"), 2, "test", "test", "test");
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
        // attach one more user
        p.addAttachedUser(new User("test3","test",1, "test"));
        assertEquals(25, Math.round(c.calculateCompatibility(u, p)));
        System.out.println("Finished testUserWithNoInterest");
    }
    public void testUserWithBadValues() {
        System.out.println("\nStarting testUserWithBadValues");
        // posting already has the main user attached
        User us = new User("test3","test",-1, "test");
        us.addUniqueInterest(null);
        p.addAttachedUser(us);

        assertEquals(0.0, c.calculateCompatibility(u, p));

        System.out.println("Finished testUserWithBadValues");
    }

    public void testUserWithMissingInterest() {
        System.out.println("\nStarting testUserWithMissingInterest");

        User us = new User("test3","test",16, "test");
        us.addUniqueInterest(new Interest(""));
        p.addAttachedUser(us);

        assertEquals(0.0, c.calculateCompatibility(u, p));

        System.out.println("Finished testUserWithMissingInterest");
    }

    public void testValidInput() {
        System.out.println("\nStarting testValidInput");
        p.getUser().addUniqueInterest(new Interest("Ice skating"));
        User u2 = new User("test5", "test",100, "test");
        u2.addUniqueInterest(new Interest("iceskating"));


        assertEquals(100.00, c.calculateCompatibility(u2,p ));
        assertEquals(100.00, c.calculateCompatibility(u2,p.getUser() ));

        System.out.println("Finished testValidInput");
    }


    public void testMixedInputs() {

        System.out.println("\nStarting testMultipleInterests");
        p.getUser().addUniqueInterest(new Interest("Ice skating"));
        p.getUser().addUniqueInterest(null);
        p.getUser().addUniqueInterest(new Interest(""));

        User u2 = new User("test5", "test",92, "test");
        u2.addUniqueInterest(new Interest("iceskating"));
        u2.addUniqueInterest(new Interest("CODING"));

        assertEquals(49, Math.round(c.calculateCompatibility(u2,p )));
        assertEquals(49, Math.round(c.calculateCompatibility(u2,p.getUser() )));

        System.out.println("Finished testMultipleInterests");

    }

    public void testMultipleAttachedUsers() {

        System.out.println("\nStarting testMultipleAttachedUsers");
        p.getUser().addUniqueInterest(new Interest("Ice skating"));
        p.getUser().addUniqueInterest(new Interest("Anime"));

        User two = new User("two", "two", 10, "two");
        two.addUniqueInterest(new Interest("coding"));
        two.addUniqueInterest(new Interest(""));

        User three = new User("three", "three" , 2, "three");
        three.addUniqueInterest(null);
        three.addUniqueInterest(new Interest("not coding"));

        p.addAttachedUser(two);
        p.addAttachedUser(three);

        u.addUniqueInterest(new Interest("iceskating"));
        u.addUniqueInterest(new Interest("CODING"));

        assertEquals(44, Math.round(c.calculateCompatibility(u,p )));
        assertEquals(25, Math.round(c.calculateCompatibility(u,p.getUser() )));
        assertEquals(48, Math.round(c.calculateCompatibility(u,two )));
        assertEquals(23, Math.round(c.calculateCompatibility(u,three )));

        System.out.println("Finished testMultipleAttachedUsers");

    }

}
