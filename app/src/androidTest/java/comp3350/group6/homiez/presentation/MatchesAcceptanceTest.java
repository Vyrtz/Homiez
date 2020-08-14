package comp3350.group6.homiez.presentation;


import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;


import comp3350.group6.homiez.R;
import comp3350.group6.homiez.business.AccessMatches;
import comp3350.group6.homiez.business.AccessRequests;
import comp3350.group6.homiez.objects.Match;
import comp3350.group6.homiez.objects.Request;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.fail;


public class MatchesAcceptanceTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void viewMatchesTest() {
        //Login
        onView(withId(R.id.editUserID)).perform(clearText(), typeText("0"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());

        //Select the "Your rooms" tab
        onView(withContentDescription("Your Rooms")).perform(click());

        //Click matches, also confirm that the match button is there
        onView(withId(R.id.button_matches)).check(matches(isDisplayed()));
        onView(withId(R.id.button_matches)).perform(click());

        //Check that the two default matches are there
        onView(withText("Room at Pembina Riverside Condo")).check(matches(isDisplayed()));
        onView(withText("Room at Windsor Park House")).check(matches(isDisplayed()));

        //Click a match and check that theres contact information
        onView(withText("Room at Pembina Riverside Condo")).perform(click());

        //Check that we got to the correct page
        onView(withText("Vinh's Contact Info")).check(matches(isDisplayed()));
    }

}
