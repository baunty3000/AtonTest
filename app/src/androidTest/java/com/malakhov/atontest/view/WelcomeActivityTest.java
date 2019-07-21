package com.malakhov.atontest.view;

import com.malakhov.atontest.R;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> activityTestRule = new ActivityTestRule<WelcomeActivity>(WelcomeActivity.class);

    @Test
    public void testLogin() {
        IdlingResource idlingResource = activityTestRule.getActivity().getCountingIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);

        // на экране 1й фрагмент с кнопкой logout
//        onView(withId(R.id.logout)).check(matches(isDisplayed())).perform(click());

        // на экране 1й фрагмент с кнопкой логина
        onView(withId(R.id.login)).check(matches(isDisplayed())).perform(click());

        //после логина видим ресайклер, нажимаем на элемент ресайклера, видим imageview следующего фрагмента
        onView(withId(R.id.recycler)).check(matches(isDisplayed())).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.photo)).check(matches(isDisplayed()));
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void testLoggedIn() {
        // после нажатия на элемент ресайклера, видим imageview следующего фрагмента
        onView(withId(R.id.recycler)).check(matches(isDisplayed())).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.photo)).check(matches(isDisplayed()));
    }

}