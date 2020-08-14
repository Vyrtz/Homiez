package comp3350.group6.homiez.presentation;


import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;


import comp3350.group6.homiez.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


public class MatchesTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void sendRequestTest() {
        //Login
        onView(withId(R.id.editUserID)).perform(clearText(), typeText("4"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());

        onView(withText("Room at Bridgewater Apartment")).perform(click());
        onView(withContentDescription("Send Request")).perform(click());
        Espresso.pressBack();
        Espresso.pressBack();

        //Login
       /* onView(withId(R.id.editUserID)).perform(clearText(), typeText("2"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());

        onView(withContentDescription("Your Rooms")).perform(click());
        onView(withContentDescription("Requests")).perform(click());*/


    }

    @Test
    public void denyRequestTest() {
        //Login
        onView(withId(R.id.editUserID)).perform(clearText(), typeText("0"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());

        
    }

}
