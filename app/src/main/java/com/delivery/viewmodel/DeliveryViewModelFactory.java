package com.delivery.viewmodel;

import com.delivery.network.DeliveryService;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DeliveryViewModelFactory implements ViewModelProvider.Factory {
    private static final String UNKNOWN_VIEW_MODEL_CLASS = "Unknown viewmodel class";
    private final DeliveryService deliveryService;

    public DeliveryViewModelFactory(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DeliveryViewModel.class)) {
            return (T) new DeliveryViewModel(deliveryService);
        }

        throw new IllegalArgumentException(UNKNOWN_VIEW_MODEL_CLASS);
    }

}
