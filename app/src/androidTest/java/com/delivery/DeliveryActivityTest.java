package com.delivery;


import com.delivery.ui.activity.DeliveryActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DeliveryActivityTest {

    @Rule
    public final ActivityTestRule<DeliveryActivity> mActivityRule =
            new ActivityTestRule<>(DeliveryActivity.class);

    @Test
    public void onDeliveryItemClick() throws InterruptedException {
        Thread.sleep(3000);
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withText("Delivery Details")).check(matches(isDisplayed()));
    }
}

