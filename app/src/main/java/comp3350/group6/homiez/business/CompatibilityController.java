package comp3350.group6.homiez.business;

import comp3350.group6.homiez.objects.Posting;
import comp3350.group6.homiez.objects.User;

public class CompatibilityController implements Compatibility {

    private Compatibility age;
    private Compatibility interests;
    private double ageCompatibilityWeight = 0.25;
    private double interestsCompatibilityWeight = 0.75;

    public CompatibilityController() {
        age = new AgeCompatibility();
        interests = new InterestCompatibility();
    }

    public CompatibilityController(double ageCompatibilityWeight, double interestsCompatibilityWeight) {
        age = new AgeCompatibility();
        interests = new InterestCompatibility();
        if (ageCompatibilityWeight + interestsCompatibilityWeight <= 1 && ageCompatibilityWeight + interestsCompatibilityWeight > 0) {
            this.ageCompatibilityWeight = ageCompatibilityWeight;
            this.interestsCompatibilityWeight = interestsCompatibilityWeight;
        }
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
            return ageCompatibilityWeight * agePercent + interestsCompatibilityWeight * interestPercent;
        }
        return -1.0;
    }

    public double getAgeCompatibilityWeight() {
        return ageCompatibilityWeight;
    }

    public double getInterestsCompatibilityWeight() {
        return interestsCompatibilityWeight;
    }
}