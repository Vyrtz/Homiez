package comp3350.group6.homiez.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class AgeCompatibility implements Compatibility {

    public double calculateCompatibility(User user, Posting posting) {

        double total = 0.0;

        if (user != null && posting != null) {
            ArrayList<User> users = posting.getAttachedUsers();
            ArrayList<Double> compatibilities = new ArrayList();

            if (users.size() > 0) {
                for (User u : users) {
                    compatibilities.add(calculateCompatibility(user, u));
                }
                total = Collections.max(compatibilities);
            }
            else {
                total =  0;
            }
        }
        else {
            total = -1.0;
        }
        return total;
    }

    public double calculateCompatibility(User userOne, User userTwo) {

        double val = -1.0;
        if (userOne != null && userTwo != null) {
            if(userOne.getAge() > 0 && userTwo.getAge() > 0) {
                int offset = userOne.getAge() - userTwo.getAge();
                offset = Math.abs(offset);
                // we say > 15 years is 0 % compatibility
                val = (15 - offset) * 100 / (double) 15;
                if (val < 0) {
                    val = 0;
                }
            }
        }
        return val;
    }
}
