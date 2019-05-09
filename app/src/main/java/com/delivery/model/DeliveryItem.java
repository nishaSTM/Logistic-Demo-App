package com.delivery.model;


import android.os.Parcel;
import android.os.Parcelable;

public class DeliveryItem implements Parcelable {
    private final String description;
    private final String imageUrl;
    private final String id;
    private final LocationCoordinates location;

    public DeliveryItem(String description, String imageUrl, String id, LocationCoordinates location) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.id = id;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return imageUrl;
    }

    public LocationCoordinates getLocation() {
        return location;
    }

    private DeliveryItem(Parcel in) {
        description = in.readString();
        imageUrl = in.readString();
        id = in.readString();
        location = (LocationCoordinates) in.readValue(LocationCoordinates.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(id);
        dest.writeValue(location);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DeliveryItem> CREATOR = new Parcelable.Creator<DeliveryItem>() {
        @Override
        public DeliveryItem createFromParcel(Parcel in) {
            return new DeliveryItem(in);
        }

        @Override
        public DeliveryItem[] newArray(int size) {
            return new DeliveryItem[size];
        }
    };
}
