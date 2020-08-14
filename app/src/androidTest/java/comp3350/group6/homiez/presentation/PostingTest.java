package comp3350.group6.homiez.presentation;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.group6.homiez.R;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PostingTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void fullPostingTest() {
        //Login
        onView(withId(R.id.editUserID)).perform(clearText(), typeText("0"));
        Espresso.pressBack();
        onView(withId(R.id.editPassword)).perform(clearText(), typeText("dev"));
        Espresso.pressBack();
        onView(withId(R.id.loginButton)).perform(click());

        //Select Profile
        onView(withContentDescription("Your Rooms")).perform(click());

        // Create posting
        onView(withId(R.id.button_create_posting)).perform(click());
        onView(withId(R.id.editTextTitle)).perform(clearText(), typeText("TESTPOSTING"));
        onView(withId(R.id.editTextType)).perform(clearText(), typeText("House"));
        onView(withId(R.id.editTextLocation)).perform(clearText(), typeText("123 Test St."));
        onView(withId(R.id.editTextDescription)).perform(clearText(), typeText("TEST"));
        onView(withId(R.id.editTextPrice)).perform(clearText(), typeText("123"));
        onView(withId(R.id.editTextTenants)).perform(clearText(), typeText("1"));
        Espresso.pressBack();
        onView(withId(R.id.createPosting)).perform(click());

        // Select the posting
        onView(withText("TESTPOSTING")).perform(click());

        // Check that the post has right info
        onView(withId(R.id.titleText)).check(matches(withText("TESTPOSTING")));
        onView(withId(R.id.typeText)).check(matches(withText("House")));
        onView(withId(R.id.locationText)).check(matches(withText("123 Test St.")));
        onView(withId(R.id.descriptionText)).check(matches(withText("TEST")));
        onView(withId(R.id.priceText)).check(matches(withText("123.0")));

        // Edit the posting
        onView(withId(R.id.editButton)).perform(click());
        onView(withId(R.id.location)).perform(clearText(), typeText("123 Test St._UPDATE"));
        onView(withId(R.id.type)).perform(clearText(), typeText("House_UPDATE"));
        onView(withId(R.id.price)).perform(clearText(), typeText("1234"));
        Espresso.pressBack();
        onView(withId(R.id.submitButton)).perform(click());

        // Select the posting
        onView(withText("TESTPOSTING")).perform(click());

        // Check that the post has the right info
        onView(withId(R.id.titleText)).check(matches(withText("TESTPOSTING")));
        onView(withId(R.id.typeText)).check(matches(withText("House_UPDATE")));
        onView(withId(R.id.locationText)).check(matches(withText("123 Test St._UPDATE")));
        onView(withId(R.id.priceText)).check(matches(withText("1234.0")));

        // Delete Post
        onView(withId(R.id.DeleteButton)).perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
