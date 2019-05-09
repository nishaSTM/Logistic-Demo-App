package com.delivery;


import com.delivery.model.DeliveryItem;
import com.delivery.model.LocationCoordinates;
import com.delivery.model.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ResultTest {

    private Result.Status status;
    private List<DeliveryItem> deliveryItemList;
    private String error;
    private Result result;

    @Before
    public void setUp() {
        DeliveryItem deliveryItem = new DeliveryItem("description", "imageUrl", "id", new LocationCoordinates(1.2f, 2f, "tojo"));
        deliveryItemList = new ArrayList<>();
        deliveryItemList.add(deliveryItem);
        error = "No Internet";
        status = Result.Status.ERROR;
        result = new Result(status, deliveryItemList, error);
    }

    @Test
    public void getDeliveryItemListAssertValue() {
        List<DeliveryItem> deliveryItemList = result.getDeliveryItemList();
        Assert.assertEquals(this.deliveryItemList, deliveryItemList);
    }

    @Test
    public void getErrorAssertValue() {
        String error = result.getError();
        Assert.assertEquals(this.error, error);
    }

    @Test
    public void getStatusAssertValue() {
        Result.Status status = result.getStatus();
        Assert.assertEquals(this.status, status);
    }
}
