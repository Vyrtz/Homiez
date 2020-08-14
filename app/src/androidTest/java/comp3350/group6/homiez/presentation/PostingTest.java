package comp3350.group6.homiez.presentation;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
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
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
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
    public void postingTest() {
        // Log in
        ViewInteraction editText = onView(allOf(withId(R.id.editUserID), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 1), isDisplayed()));
        editText.perform(replaceText("0"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(allOf(withId(R.id.editPassword), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 2), isDisplayed()));
        editText2.perform(replaceText("dev"), closeSoftKeyboard());

        ViewInteraction button = onView(allOf(withId(R.id.loginButton), withText("Login"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 3), isDisplayed()));
        button.perform(click());

        // Go to your postings
        ViewInteraction bottomNavigationItemView = onView(allOf(withId(R.id.your_rooms), withContentDescription("Your Rooms"), childAtPosition(childAtPosition(withId(R.id.bottom_nav), 0), 1), isDisplayed()));
        bottomNavigationItemView.perform(click());

        // Click button to creat posting
        ViewInteraction appCompatButton = onView(allOf(withId(R.id.button_create_posting), withText("Create Posting"), childAtPosition(allOf(withId(R.id.createPosting), childAtPosition(withId(R.id.container), 0)), 2), isDisplayed()));
        appCompatButton.perform(click());

        // Enter text in fields
        ViewInteraction editText3 = onView(allOf(withId(R.id.editTextTitle), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0), 1), isDisplayed()));
        editText3.perform(replaceText("TESTPOST"), closeSoftKeyboard());

        ViewInteraction editText4 = onView(allOf(withId(R.id.editTextType), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0), 2), isDisplayed()));
        editText4.perform(replaceText("House"), closeSoftKeyboard());

        ViewInteraction editText5 = onView(allOf(withId(R.id.editTextLocation), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0), 3), isDisplayed()));
        editText5.perform(replaceText("123 Test St"), closeSoftKeyboard());

        ViewInteraction editText6 = onView(allOf(withId(R.id.editTextDescription), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0), 4), isDisplayed()));
        editText6.perform(replaceText("TEST"), closeSoftKeyboard());

        ViewInteraction editText7 = onView(allOf(withId(R.id.editTextPrice), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0), 5), isDisplayed()));
        editText7.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction editText11 = onView(allOf(withId(R.id.editTextTenants), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0), 6), isDisplayed()));
        editText11.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction editText12 = onView(allOf(withId(R.id.editTextPrice), withText("123"), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0), 5), isDisplayed()));
        editText12.perform(click());

        ViewInteraction editText16 = onView(allOf(withId(R.id.editTextPrice), withText("123"), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0), 5), isDisplayed()));
        editText16.perform(pressImeActionButton());

        pressBack();

        // Create the posting
        ViewInteraction button2 = onView(allOf(withId(R.id.createPosting), withText("Create"), childAtPosition(childAtPosition(withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")), 0), 7), isDisplayed()));
        button2.perform(click());

        // Check that the posting was created
        ViewInteraction postingslist = onView(allOf(withId(R.id.postingsList), isDisplayed()));
        postingslist.check(matches(isDisplayed()));

        // View the posting
        DataInteraction linearLayout = onData(anything()).inAdapterView(allOf(withId(R.id.postingsList), childAtPosition(withId(R.id.createPosting), 1))).atPosition(3);
        linearLayout.perform(click());

        // Check we are on the correct posting
        ViewInteraction textView2 = onView(allOf(withId(R.id.titleText), withText("TESTPOST"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        textView2.check(matches(withText("TESTPOST")));

        // Click edit
        ViewInteraction button3 = onView(allOf(withId(R.id.editButton), withText("Edit"), childAtPosition(allOf(withId(R.id.edit_del), childAtPosition(withClassName(is("android.widget.RelativeLayout")), 1)), 0), isDisplayed()));
        button3.perform(click());

        // Check we are on the edit page
        ViewInteraction textView3 = onView(allOf(withId(R.id.title), withText("TESTPOST's Posting"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        textView3.check(matches(withText("TESTPOST's Posting")));

        // Update the posting
        ViewInteraction editText17 = onView(allOf(withId(R.id.location), withText("123 Test St"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 2)));
        editText17.perform(scrollTo(), click());

        ViewInteraction editText18 = onView(allOf(withId(R.id.location), withText("123 Test St"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 2)));
        editText18.perform(scrollTo(), replaceText("1123 Test St"));

        ViewInteraction editText19 = onView(allOf(withId(R.id.location), withText("1123 Test St"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 2), isDisplayed()));
        editText19.perform(closeSoftKeyboard());

        ViewInteraction editText20 = onView(allOf(withId(R.id.type), withText("House"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 4)));
        editText20.perform(scrollTo(), replaceText("1House"));

        ViewInteraction editText21 = onView(allOf(withId(R.id.type), withText("1House"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 4), isDisplayed()));
        editText21.perform(closeSoftKeyboard());

        ViewInteraction editText22 = onView(allOf(withId(R.id.price), withText("123.0"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 6)));
        editText22.perform(scrollTo(), replaceText("1123.0"));

        ViewInteraction editText23 = onView(allOf(withId(R.id.price), withText("1123.0"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 6), isDisplayed()));
        editText23.perform(closeSoftKeyboard());

        ViewInteraction editText24 = onView(allOf(withId(R.id.description), withText("TEST"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 8)));
        editText24.perform(scrollTo(), replaceText("1TEST"));

        ViewInteraction editText25 = onView(allOf(withId(R.id.description), withText("1TEST"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 8), isDisplayed()));
        editText25.perform(closeSoftKeyboard());

        ViewInteraction editText26 = onView(allOf(withId(R.id.tenants), withText("1,"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 10)));
        editText26.perform(scrollTo(), replaceText("1, 2"));

        ViewInteraction editText27 = onView(allOf(withId(R.id.tenants), withText("1, 2"), childAtPosition(childAtPosition(withClassName(is("android.widget.ScrollView")), 0), 10), isDisplayed()));
        editText27.perform(closeSoftKeyboard());

        // Submit changes
        ViewInteraction button4 = onView(allOf(withId(R.id.submitButton), withText("Submit"), childAtPosition(childAtPosition(withClassName(is("android.widget.RelativeLayout")), 1), 1), isDisplayed()));
        button4.perform(click());

        // Check that changes worked
        ViewInteraction textView4 = onView(allOf(withId(R.id.titleText), withText("TESTPOST"), childAtPosition(childAtPosition(withId(android.R.id.content), 0), 0), isDisplayed()));
        textView4.check(matches(withText("TESTPOST")));

        // Delete post
        ViewInteraction button5 = onView(allOf(withId(R.id.DeleteButton), withText("Delete"), childAtPosition(allOf(withId(R.id.edit_del), childAtPosition(withClassName(is("android.widget.RelativeLayout")), 1)), 1), isDisplayed()));
        button5.perform(click());

        // Check post was deleted
        ViewInteraction textView5 = onView(allOf(withId(R.id.textTitle), withText("Your Postings"), childAtPosition(allOf(withId(R.id.createPosting), childAtPosition(withId(R.id.container), 0)), 0), isDisplayed()));
        textView5.check(matches(withText("Your Postings")));
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
