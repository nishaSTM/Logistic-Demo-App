package com.delivery.model;


import android.os.Parcel;
import android.os.Parcelable;

public class LocationCoordinates implements Parcelable {
    private final float lat;
    private final float lng;
    private final String address;

    public LocationCoordinates(float lat, float lng, String address) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getAddress() {
        return address;
    }

    private LocationCoordinates(Parcel in) {
        lat = in.readFloat();
        lng = in.readFloat();
        address = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(lat);
        dest.writeFloat(lng);
        dest.writeString(address);
    }

    public static final Parcelable.Creator<LocationCoordinates> CREATOR = new Parcelable.Creator<LocationCoordinates>() {
        @Override
        public LocationCoordinates createFromParcel(Parcel in) {
            return new LocationCoordinates(in);
        }

        @Override
        public LocationCoordinates[] newArray(int size) {
            return new LocationCoordinates[size];
        }
    };
}
