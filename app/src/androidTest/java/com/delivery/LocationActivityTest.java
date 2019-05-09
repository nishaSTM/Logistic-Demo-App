package com.delivery;

import android.content.Intent;
import com.delivery.constant.AppConstants;
import com.delivery.model.DeliveryItem;
import com.delivery.model.LocationCoordinates;
import com.delivery.ui.activity.LocationActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;

public class LocationActivityTest {

    private static final String EMPTY_STRING = "";
    @Rule
    public final ActivityTestRule<LocationActivity> activityActivityTestRule = new ActivityTestRule<>(
            LocationActivity.class, true, false);

    @Test
    public void assertDescriptionWithCorrectData() {
        Intent intent = new Intent();
        DeliveryItem deliveryOb = new DeliveryItem("sample_desc", "image_url", "1", new LocationCoordinates(1.2f, 1.2f, "address"));
        intent.putExtra(AppConstants.DELIVERY_ITEM_OBJECT, deliveryOb);
        activityActivityTestRule.launchActivity(intent);
        onView(withId(R.id.desc_location)).check(matches(withText(deliveryOb.getDescription())));
    }

    @Test
    public void assertDescriptionWithNullData() {
        Intent intent = new Intent();
        activityActivityTestRule.launchActivity(intent);
        onView(withId(R.id.desc_location)).check(matches(withText(EMPTY_STRING)));

    }
}