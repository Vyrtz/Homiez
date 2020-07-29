package comp3350.group6.homiez.business;

import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public interface Compatibility {

    double calculateCompatibility(User user, Posting posting);

    double calculateCompatibility(User userOne, User userTwo);
}
