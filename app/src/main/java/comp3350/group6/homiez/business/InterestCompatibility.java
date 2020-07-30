package comp3350.group6.homiez.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import comp3350.group6.homiez.objects.Interest;
import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class InterestCompatibility implements Compatibility {

    public double calculateCompatibility(User user, Posting posting) {

        double total = 0.0;

        if (user != null && posting != null) {
            ArrayList<User> users = posting.getAttachedUsers();
            ArrayList<Double> compatibilities = new ArrayList();

            if (users.size() > 0) {
                for (User u : users) {
                    compatibilities.add(calculateCompatibility(user, u));
                }
                for (Double d : compatibilities) {
                    if (d > 0) { //if it is 0 no need for an extra addition
                        total += d;
                    }
                }

                total =  total / compatibilities.size();
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

            ArrayList<Interest> userOneInterests = userOne.getInterests();
            ArrayList<Interest> userTwoInterests = userTwo.getInterests();

            if (userOneInterests != null && userTwoInterests != null) {
                Set<Interest> matchedInterests = new HashSet<>(userOneInterests);
                matchedInterests.retainAll(userTwoInterests);
                matchedInterests.remove(null); //remove null values if any
                matchedInterests.remove(new Interest("")); //a interest should not be empty

                Set<Interest> uniqueInterests = new HashSet<>(userOneInterests);
                uniqueInterests.addAll(userTwoInterests);
                uniqueInterests.remove(null);
                uniqueInterests.remove(new Interest(""));

                if (uniqueInterests.size() > 0) {
                    val =  ( (double) matchedInterests.size() / uniqueInterests.size() ) * 100 ;
                }
                else {
                    val = 0;
                }
            }
        }
        return val;
    }
}
