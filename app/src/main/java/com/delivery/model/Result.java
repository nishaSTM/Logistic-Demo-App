package com.delivery.model;

import java.util.List;

public class Result {

    public enum Status {SUCCESS, ERROR}

    private final Status status;
    private final List<DeliveryItem> deliveryItemList;
    private final String error;

    public Result(Status status, List<DeliveryItem> deliveryItemList, String error) {
        this.status = status;
        this.deliveryItemList = deliveryItemList;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public List<DeliveryItem> getDeliveryItemList() {
        return deliveryItemList;
    }

    public String getError() {
        return error;
    }
}
