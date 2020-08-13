package comp3350.group6.homiez.presentation;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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
import comp3350.group6.homiez.business.AccessUser;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignupActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void validSignupTest() {
        // Click on sign up
        ViewInteraction textView = onView(allOf(withId(R.id.signup), withText("Sign up"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 5), isDisplayed()));
        textView.perform(click());

        // Check on correct page
        ViewInteraction textView2 = onView(allOf(withId(R.id.textView10), withText("Create Profile"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        textView2.check(matches(withText("Create Profile")));

        // Enter id
        ViewInteraction editText = onView(allOf(withId(R.id.editID), childAtPosition(allOf(withId(R.id.header), childAtPosition(withClassName(is("android.widget.LinearLayout")), 0)), 1)));
        editText.perform(scrollTo(), replaceText("TESTID12345"), closeSoftKeyboard());

        // Enter password
        ViewInteraction editText2 = onView(allOf(withId(R.id.editPassword), childAtPosition(allOf(withId(R.id.password), childAtPosition(withClassName(is("android.widget.LinearLayout")), 1)), 1)));
        editText2.perform(scrollTo(), replaceText("dev"), closeSoftKeyboard());

        // Enter price
        ViewInteraction editText3 = onView(allOf(withId(R.id.editBudget), childAtPosition(allOf(withId(R.id.budget), childAtPosition(withClassName(is("android.widget.LinearLayout")), 2)), 1)));
        editText3.perform(scrollTo(), replaceText("1000"), closeSoftKeyboard());

        // Enter name
        ViewInteraction editText4 = onView(allOf(withId(R.id.editName), childAtPosition(allOf(withId(R.id.name), childAtPosition(withClassName(is("android.widget.LinearLayout")), 3)), 1)));
        editText4.perform(scrollTo(), replaceText("TESTER"), closeSoftKeyboard());

        // Enter age
        ViewInteraction editText5 = onView(allOf(withId(R.id.editAge), childAtPosition(allOf(withId(R.id.age), childAtPosition(withClassName(is("android.widget.LinearLayout")), 4)), 1)));
        editText5.perform(scrollTo(), replaceText("22"), closeSoftKeyboard());

        // Enter gender
        ViewInteraction editText6 = onView(allOf(withId(R.id.editGender), childAtPosition(allOf(withId(R.id.gender), childAtPosition(withClassName(is("android.widget.LinearLayout")), 5)), 1)));
        editText6.perform(scrollTo(), replaceText("Male"), closeSoftKeyboard());

        // Enter contact info
        ViewInteraction editText8 = onView(allOf(withId(R.id.editContact), childAtPosition(allOf(withId(R.id.contact), childAtPosition(withClassName(is("android.widget.LinearLayout")), 6)), 1)));
        editText8.perform(scrollTo(), replaceText("Phone: (204)789-0765"), closeSoftKeyboard());

        // Enter biography
        ViewInteraction editText10 = onView(allOf(withId(R.id.editBiography), childAtPosition(allOf(withId(R.id.bio), childAtPosition(withClassName(is("android.widget.LinearLayout")), 7)), 1)));
        editText10.perform(scrollTo(), replaceText("Talking is my life"), closeSoftKeyboard());

        // Enter interests
        ViewInteraction editText11 = onView(allOf(withId(R.id.editInterest), childAtPosition(allOf(withId(R.id.interests), childAtPosition(withClassName(is("android.widget.LinearLayout")), 8)), 1)));
        editText11.perform(scrollTo(), replaceText("Coding, walking"), closeSoftKeyboard());

        // Click create button
        ViewInteraction button = onView(allOf(withId(R.id.loginButton), withText("Create"), childAtPosition(childAtPosition(withClassName(is("android.widget.LinearLayout")), 2), 0), isDisplayed()));
        button.perform(click());

        // Check that profile was created
        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.message), withText("Profile created!"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Profile created!")));

        // Clean up after
        AccessUser accessUser = new AccessUser();
        accessUser.deleteUser(accessUser.getUser("TESTID12345"));
    }

    @Test
    public void noInputSignupTest() {
        // Click on sign up
        ViewInteraction textView = onView(allOf(withId(R.id.signup), withText("Sign up"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 5), isDisplayed()));
        textView.perform(click());

        // Check on correct page
        ViewInteraction textView2 = onView(allOf(withId(R.id.textView10), withText("Create Profile"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        textView2.check(matches(withText("Create Profile")));

        // Click create button
        ViewInteraction button = onView(allOf(withId(R.id.loginButton), withText("Create"), childAtPosition(childAtPosition(withClassName(is("android.widget.LinearLayout")), 2), 0), isDisplayed()));
        button.perform(click());

        // Check that profile was created
        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.message), withText("Error: Could not create a new profile"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Error: Could not create a new profile")));

        // Clean up after
        AccessUser accessUser = new AccessUser();
        accessUser.deleteUser(accessUser.getUser("TESTID12345"));
    }

    @Test
    public void idExistsSignupTest() {
        // Create a user
        validSignupTest();

        // Click on sign up
        ViewInteraction textView = onView(allOf(withId(R.id.signup), withText("Sign up"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 5), isDisplayed()));
        textView.perform(click());

        // Check on correct page
        ViewInteraction textView2 = onView(allOf(withId(R.id.textView10), withText("Create Profile"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        textView2.check(matches(withText("Create Profile")));

        // Enter id
        ViewInteraction editText = onView(allOf(withId(R.id.editID), childAtPosition(allOf(withId(R.id.header), childAtPosition(withClassName(is("android.widget.LinearLayout")), 0)), 1)));
        editText.perform(scrollTo(), replaceText("TESTID12345"), closeSoftKeyboard());

        // Enter password
        ViewInteraction editText2 = onView(allOf(withId(R.id.editPassword), childAtPosition(allOf(withId(R.id.password), childAtPosition(withClassName(is("android.widget.LinearLayout")), 1)), 1)));
        editText2.perform(scrollTo(), replaceText("dev"), closeSoftKeyboard());

        // Enter price
        ViewInteraction editText3 = onView(allOf(withId(R.id.editBudget), childAtPosition(allOf(withId(R.id.budget), childAtPosition(withClassName(is("android.widget.LinearLayout")), 2)), 1)));
        editText3.perform(scrollTo(), replaceText("1000"), closeSoftKeyboard());

        // Enter name
        ViewInteraction editText4 = onView(allOf(withId(R.id.editName), childAtPosition(allOf(withId(R.id.name), childAtPosition(withClassName(is("android.widget.LinearLayout")), 3)), 1)));
        editText4.perform(scrollTo(), replaceText("TESTER"), closeSoftKeyboard());

        // Enter age
        ViewInteraction editText5 = onView(allOf(withId(R.id.editAge), childAtPosition(allOf(withId(R.id.age), childAtPosition(withClassName(is("android.widget.LinearLayout")), 4)), 1)));
        editText5.perform(scrollTo(), replaceText("22"), closeSoftKeyboard());

        // Enter gender
        ViewInteraction editText6 = onView(allOf(withId(R.id.editGender), childAtPosition(allOf(withId(R.id.gender), childAtPosition(withClassName(is("android.widget.LinearLayout")), 5)), 1)));
        editText6.perform(scrollTo(), replaceText("Male"), closeSoftKeyboard());

        // Enter contact info
        ViewInteraction editText8 = onView(allOf(withId(R.id.editContact), childAtPosition(allOf(withId(R.id.contact), childAtPosition(withClassName(is("android.widget.LinearLayout")), 6)), 1)));
        editText8.perform(scrollTo(), replaceText("Phone: (204)789-0765"), closeSoftKeyboard());

        // Enter biography
        ViewInteraction editText10 = onView(allOf(withId(R.id.editBiography), childAtPosition(allOf(withId(R.id.bio), childAtPosition(withClassName(is("android.widget.LinearLayout")), 7)), 1)));
        editText10.perform(scrollTo(), replaceText("Talking is my life"), closeSoftKeyboard());

        // Enter interests
        ViewInteraction editText11 = onView(allOf(withId(R.id.editInterest), childAtPosition(allOf(withId(R.id.interests), childAtPosition(withClassName(is("android.widget.LinearLayout")), 8)), 1)));
        editText11.perform(scrollTo(), replaceText("Coding, walking"), closeSoftKeyboard());

        // Click create button
        ViewInteraction button = onView(allOf(withId(R.id.loginButton), withText("Create"), childAtPosition(childAtPosition(withClassName(is("android.widget.LinearLayout")), 2), 0), isDisplayed()));
        button.perform(click());

        // Check that profile was not created
        ViewInteraction textView3 = onView(
                allOf(withId(android.R.id.message), withText("Error: Could not create a new profile"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Error: Could not create a new profile")));

        // Clean up after
        AccessUser accessUser = new AccessUser();
        accessUser.deleteUser(accessUser.getUser("TESTID12345"));

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
