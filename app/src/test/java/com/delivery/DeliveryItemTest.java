package com.delivery;


import com.delivery.model.DeliveryItem;
import com.delivery.model.LocationCoordinates;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class DeliveryItemTest {

    private LocationCoordinates locationCoordinates;
    private DeliveryItem deliveryItem;
    private String description;
    private String imageUrl;
    private String id;

    @Before
    public void setUp() {
        description = "Antrio";
        imageUrl = "https://www.lucy.com/lucy.jpg";
        id = "1";
        locationCoordinates = new LocationCoordinates(1.2f, 1.2f, "tojo");
        deliveryItem = new DeliveryItem(description, imageUrl, id, locationCoordinates);
    }

    @Test
    public void getImageAssertValue() {
        String imageUrl = deliveryItem.getImageUrl();
        Assert.assertEquals(this.imageUrl, imageUrl);
    }

    @Test
    public void getDescriptionAssertValue() {
        String description = deliveryItem.getDescription();
        Assert.assertEquals(this.description, description);
    }

    @Test
    public void getIdAssertValue() {
        String id = deliveryItem.getId();
        Assert.assertEquals(this.id, id);
    }

    @Test
    public void getLocationAssertValue() {
        LocationCoordinates location = deliveryItem.getLocation();
        Assert.assertEquals(this.locationCoordinates, location);
    }
}
