package com.delivery.viewmodel;

import com.delivery.model.DeliveryItem;
import androidx.lifecycle.ViewModel;

public class DeliveryItemViewModel extends ViewModel {

    private DeliveryItem deliveryItem;

    public String getImageUrl() {
        return deliveryItem.getImage();
    }

    public String getDescription() {
        return deliveryItem.getDescription();
    }

    public void setDeliveryItem(DeliveryItem deliveryItem) {
        this.deliveryItem = deliveryItem;

    }
}
