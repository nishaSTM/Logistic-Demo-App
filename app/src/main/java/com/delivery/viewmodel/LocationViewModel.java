package com.delivery.viewmodel;

import com.delivery.model.DeliveryItem;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocationViewModel extends ViewModel {

    public final MutableLiveData<String> imageUrl = new MutableLiveData<>();
    public final MutableLiveData<String> description = new MutableLiveData<>();
    private DeliveryItem deliveryItem;

    public DeliveryItem getDeliveryItem() {
        return deliveryItem;
    }

    public void setDeliveryItem(DeliveryItem deliveryItem) {
        if (deliveryItem != null) {
            this.deliveryItem = deliveryItem;
            imageUrl.setValue(deliveryItem.getImageUrl());
            description.setValue(deliveryItem.getDescription());
        }
    }
}
