package comp3350.group6.homiez.business;

import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class CompatibilityController implements Compatibility {

    private Compatibility age;
    private Compatibility interests;

    public CompatibilityController() {
        age = new AgeCompatibility();
        interests = new InterestCompatibility();
    }

    public double calculateCompatibility(User user, Posting posting) {

        double total = -1.0;

        if (user != null && posting != null) {
            double agePercent = age.calculateCompatibility(user, posting);
            double interestPercent = interests.calculateCompatibility(user, posting);
            total = totalCalc(agePercent, interestPercent);
        }
        return total;
    }

    public double calculateCompatibility(User userOne, User userTwo) {

        double val = -1.0;
        if (userOne != null && userTwo != null) {
            double agePercent = age.calculateCompatibility(userOne, userTwo);
            double interestPercent = interests.calculateCompatibility(userOne, userTwo);
            val = totalCalc(agePercent, interestPercent);
        }
        return val;
    }

    private double totalCalc(double agePercent, double interestPercent) {
        if (Math.round(agePercent) != -1 && Math.round(interestPercent) != -1) {
            return 0.25 * agePercent + 0.75 * interestPercent;
        }
        return -1.0;
    }
}