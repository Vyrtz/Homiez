package comp3350.group6.homiez.presentation;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;
import org.junit.Test;

import comp3350.group6.homiez.R;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

public class CustomizeProfileAcceptanceTest {

    @Rule
    public ActivityTestRule<LoginActivity> LoginActivity = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void customizeProfileActivityTest() {
        //Login
        onView(withId(R.id.editUserID)).perform(clearText(), typeText("0"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());

        //Select Profile
        onView(withContentDescription("Profile")).perform(click());

        //Confirm the default information of the profile
        onView(withId(R.id.name)).check(matches(withText("Abhi")));
        onView(withId(R.id.age)).check(matches(withText("20")));
        onView(withId(R.id.gender)).check(matches(withText("M")));
        onView(withId(R.id.budget)).check(matches(withText("1500.0")));
        onView(withId(R.id.contact)).check(matches(withText("")));
        onView(withId(R.id.bio)).check(matches(withText("DESC Abhi")));

        onView(withId(R.id.submitButt)).perform(click());

        //Change some things in the profile
        onView(withId(R.id.age)).perform(clearText(), typeText("25"));
        onView(withId(R.id.gender)).perform(clearText(), typeText("Male"));
        onView(withId(R.id.budget)).perform(clearText(), typeText("2000.0"));
        onView(withId(R.id.contact)).perform(clearText(), typeText("Phone:555-5555"));
        Espresso.pressBack();
        onView(withId(R.id.bio)).perform(clearText(), typeText("A better description"));

        onView(withId(R.id.submitButton)).perform(click());
        onView(withText("Success!")).check(matches(isDisplayed()));

        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();

        //Confirm new changes
        onView(withId(R.id.name)).check(matches(withText("Abhi")));
        onView(withId(R.id.age)).check(matches(withText("25")));
        onView(withId(R.id.gender)).check(matches(withText("Male")));
        onView(withId(R.id.budget)).check(matches(withText("2000.0")));
        onView(withId(R.id.contact)).check(matches(withText("Phone:555-5555")));
        onView(withId(R.id.bio)).check(matches(withText("A better description")));

        //Cleanup

        onView(withId(R.id.submitButt)).perform(click());

        //Change some things in the profile
        onView(withId(R.id.age)).perform(clearText(), typeText("20"));
        onView(withId(R.id.gender)).perform(clearText(), typeText("M"));
        onView(withId(R.id.budget)).perform(clearText(), typeText("1500.0"));
        onView(withId(R.id.contact)).perform(clearText(), typeText(""));
        Espresso.pressBack();
        onView(withId(R.id.bio)).perform(clearText(), typeText("DESC Abhi"));

        onView(withId(R.id.submitButton)).perform(click());
        onView(withText("Success!")).check(matches(isDisplayed()));

    }

    @Test
    public void customizeBlankProfileActivityTest() {
        //Login
        onView(withId(R.id.editUserID)).perform(clearText(), typeText("0"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());

        //Select Profile
        onView(withContentDescription("Profile")).perform(click());

        //Confirm the default information of the profile
        onView(withId(R.id.name)).check(matches(withText("Abhi")));
        onView(withId(R.id.age)).check(matches(withText("20")));
        onView(withId(R.id.gender)).check(matches(withText("M")));
        onView(withId(R.id.budget)).check(matches(withText("1500.0")));
        onView(withId(R.id.contact)).check(matches(withText("")));
        onView(withId(R.id.bio)).check(matches(withText("DESC Abhi")));

        onView(withId(R.id.submitButt)).perform(click());

        //Change things in the profile (invalid info)
        onView(withId(R.id.age)).perform(clearText(), typeText("500"));
        onView(withId(R.id.submitButton)).perform(click());
        onView(withText("Warning")).check(matches(isDisplayed()));

    }

}
