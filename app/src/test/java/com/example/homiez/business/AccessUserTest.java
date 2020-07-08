package com.example.homiez.business;

import com.example.homiez.application.Main;
import com.example.homiez.objects.User;

import junit.framework.TestCase;

public class AccessUserTest extends TestCase {

    public void testAccessUser1(){
        System.out.println("\nStarting testAccessUser1");

        Main.startUp();
        User u = new User("0", "Abhi", 20, "m");
        User newUser = new User("99", "testU1", 30, "f");
        User updateUser = new User("99", "testChange", 30, "f");
        AccessUser aUser = new AccessUser();
        aUser.login(u);

        assertNotNull(aUser);
        assertNotNull(aUser.getUser("0"));

        aUser.insertUser(newUser);
        assertNotNull(aUser.getUser("99"));

        aUser.updateUser(updateUser);
        assertTrue("testChange".equals(aUser.getUser("99").getName()));


        System.out.println("Finished testAccessUser1");
    }

}
