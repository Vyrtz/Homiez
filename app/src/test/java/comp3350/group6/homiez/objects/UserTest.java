package comp3350.group6.homiez.objects;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    public void testUser(){
        User u = new User("0", "John McNamara", 23, "M");

        assertNotNull(u);
    }

}
