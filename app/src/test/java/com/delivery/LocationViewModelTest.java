package com.delivery;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.delivery.model.DeliveryItem;
import com.delivery.model.LocationCoordinates;
import com.delivery.viewmodel.LocationViewModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocationViewModelTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();
    private LocationViewModel locationViewModel;
    private DeliveryItem deliveryItem;

    @Before
    public void setUp() {
        LocationCoordinates location = new LocationCoordinates(2.1f, 2.1f, "delhi");
        deliveryItem =
                new DeliveryItem("desc", "image_url", "1", location);
        locationViewModel = new LocationViewModel();
        locationViewModel.setDeliveryItem(deliveryItem);

    }

    @Test
    public void getDeliveryItemAssertValue() {
        DeliveryItem actualDeliveryItem = locationViewModel.getDeliveryItem();
        Assert.assertEquals(deliveryItem, actualDeliveryItem);
    }

    @Test
    public void getDeliveryItemAssertNotNullValue() {
        DeliveryItem actualDeliveryItem = locationViewModel.getDeliveryItem();
        Assert.assertNotNull(actualDeliveryItem);
    }

    @Test
    public void getImageAssertValue() {

        String actualImageValue = locationViewModel.imageUrl.getValue();
        Assert.assertEquals(deliveryItem.getImage(), actualImageValue);
    }

    @Test
    public void getImageAssertNotNullValue() {

        String actualImageValue = locationViewModel.imageUrl.getValue();
        Assert.assertNotNull(actualImageValue);
    }

    @Test
    public void getDescriptionAssertValue() {
        String actualDescriptionValue = locationViewModel.description.getValue();
        Assert.assertEquals(deliveryItem.getDescription(), actualDescriptionValue);
    }

    @Test
    public void getDescriptionAssertNotNullValue() {
        String actualDescriptionValue = locationViewModel.description.getValue();
        Assert.assertNotNull(actualDescriptionValue);
    }
}