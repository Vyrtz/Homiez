package comp3350.group6.homiez.objects;

import junit.framework.TestCase;

public class PostingTest extends TestCase {
    Posting p;
    Posting matchEqual;

    public void setUp() {
        p = new Posting("0", "Pembina Room", new User("1"), 300.00, "Pembina", "Condo", "New condo at pembina");
        matchEqual = new Posting("0");
    }
    public void testPostingCreation() {
        System.out.println("\nStarting testPostingCreation");
        assertNotNull(p);
        assertEquals("0", p.getPostingId());
        assertEquals("Pembina Room", p.getTitle());
        assertEquals( "New condo at pembina", p.getDescription() );
        assertEquals("Pembina" , p.getLocation());
        assertEquals("Condo" , p.getType());
        assertEquals(300.00 , p.getPrice());

        System.out.println("Finished testPostingCreation");
    }

    public void testPostingEquals() {
        System.out.println("\nStarting testPostingEquals");

        assertNotSame(p, matchEqual);
        assertTrue(p.equals(matchEqual));

        System.out.println("Finished testPostingEquals");
    }

    public void testPostingNotEquals() {
        System.out.println("\nStarting testPostingNotEquals");

        assertFalse(p.equals(new Posting("1")));

        System.out.println("Finished testPostingNotEquals");
    }

    public void testPostingEqualsNull() {
        System.out.println("\nStarting testPostingEqualsNull");

        assertFalse(p.equals(null));

        System.out.println("Finished testPostingEqualsNull");
    }
}
