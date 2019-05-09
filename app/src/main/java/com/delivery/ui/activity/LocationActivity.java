package com.delivery.ui.activity;

import android.os.Bundle;

import com.delivery.R;
import com.delivery.databinding.ActivityLocationBinding;
import com.delivery.model.DeliveryItem;
import com.delivery.model.LocationCoordinates;
import com.delivery.constant.AppConstants;
import com.delivery.viewmodel.LocationViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class LocationActivity extends BaseActivity implements OnMapReadyCallback {
    private LocationViewModel locationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        setupBindings();
    }

    private void setupBindings() {
        ActivityLocationBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_location);
        activityBinding.setLifecycleOwner(this);
        DeliveryItem deliveryItem = getIntent().getParcelableExtra(AppConstants.DELIVERY_ITEM_OBJECT);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.setDeliveryItem(deliveryItem);
        activityBinding.setLocationViewModel(locationViewModel);
        activityBinding.executePendingBindings();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        DeliveryItem deliveryItemObject = locationViewModel.getDeliveryItem();
        LocationCoordinates location = null;

        if (deliveryItemObject != null)
            location = deliveryItemObject.getLocation();

        if (location != null) {
            LatLng loc = new LatLng(location.getLat(), location.getLng());

            googleMap.addMarker(new MarkerOptions().position(loc)
                    .title(location.getAddress()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            CameraUpdateFactory.zoomTo(AppConstants.ZOOM_FACTOR);
        }
    }
}