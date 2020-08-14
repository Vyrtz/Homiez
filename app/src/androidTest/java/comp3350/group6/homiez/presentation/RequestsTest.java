package comp3350.group6.homiez.presentation;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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

public class RequestsTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void sendRequestTest() {
        Request r = new Request("0", "3");
        AccessRequests aRequests = new AccessRequests();

        //Login
        onView(withId(R.id.editUserID)).perform(clearText(), typeText("0"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());


        onView(withText("Room at East St. Paul House")).perform(click());
        onView(withText("Send Request")).perform(click());

        ViewInteraction v = onView(withText("Match request sent"));
        v.check(matches(isDisplayed()));

        aRequests.deleteRequest(r);
    }

    @Test
    public void viewRequests() {
        //Login
        onView(withId(R.id.editUserID)).perform(clearText(), typeText("0"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());

        onView(withContentDescription("Your Rooms")).perform(click());
        onView(withText("Requests")).perform(click());

        ViewInteraction v = onView(withText("Room at Pembina Riverside Condo"));
        v.check(matches(isDisplayed()));
        v = onView(withText("Room at Windsor Park House"));
        v.check(matches(isDisplayed()));
        v = onView(withText("Room at North Kildonan Condo"));
        v.check(matches(isDisplayed()));
    }

    @Test
    public void denyRequestTest() {
        //Login
        onView(withId(R.id.editUserID)).perform(clearText(), typeText("0"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());

        //Select the "Your rooms" tab
        onView(withContentDescription("Your Rooms")).perform(click());

        onView(withId(R.id.button_requests)).check(matches(isDisplayed()));
        onView(withId(R.id.button_requests)).perform(click());

        onView(withText("Room at Pembina Riverside Condo")).perform(click());
        onView(withText("Request")).check(matches(isDisplayed()));

        onView(withId(R.id.denyButton)).perform(click());

        onView(withText("Room at Pembina Riverside Condo")).check(doesNotExist());

        //Cleanup
        AccessRequests ar = new AccessRequests();
        Request request = new Request("4", "0");
        ar.insertRequest(request);

    }

    @Test
    public void acceptRequestTest() {
        //Login
        onView(withId(R.id.editUserID)).perform(clearText(), typeText("0"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());

        //Select the "Your rooms" tab
        onView(withContentDescription("Your Rooms")).perform(click());

        //Open requests
        onView(withId(R.id.button_requests)).check(matches(isDisplayed()));
        onView(withId(R.id.button_requests)).perform(click());

        //Open a single request
        onView(withText("Room at Pembina Riverside Condo")).perform(click());
        onView(withText("Request")).check(matches(isDisplayed()));

        //Accept the request
        onView(withId(R.id.loginButton)).perform(click());
        onView(withText("Room at Pembina Riverside Condo")).check(doesNotExist());

        //Open up matches
        Espresso.pressBack();
        onView(withId(R.id.button_matches)).check(matches(isDisplayed()));
        onView(withId(R.id.button_matches)).perform(click());

        //Check that the match showed up
        onView(withText("Ma")).check(matches(isDisplayed()));

        //Cleanup
        //Delete the match
        AccessMatches am = new AccessMatches();
        Match match = new Match("4", "0");
        am.deleteMatch(match);

        //Re-insert the request
        AccessRequests ar = new AccessRequests();
        Request request = new Request("4", "0");
        ar.insertRequest(request);
    }

}
