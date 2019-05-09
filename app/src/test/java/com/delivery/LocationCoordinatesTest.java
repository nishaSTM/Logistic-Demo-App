package com.delivery;

import com.delivery.model.LocationCoordinates;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocationCoordinatesTest {

    private LocationCoordinates locationCoordinates;
    private float lat;
    private float lng;
    private String address;

    @Before
    public void setUp() {
        lat = 1.2f;
        lng = 1.2f;
        address = "tojo";
        locationCoordinates = new LocationCoordinates(1.2f, 1.2f, "tojo");
    }

    @Test
    public void getLatAssertValue() {
        float lat = locationCoordinates.getLat();
        Assert.assertEquals(this.lat, lat, 1.2f);
    }

    @Test
    public void getLngAssertValue() {
        float lng = locationCoordinates.getLng();
        Assert.assertEquals(this.lng, lng, 1.2f);
    }

    @Test
    public void getAddressAssertValue() {
        String address = locationCoordinates.getAddress();
        Assert.assertEquals(this.address, address);
    }
}
