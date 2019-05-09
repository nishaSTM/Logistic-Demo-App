package com.delivery.viewmodel;


import androidx.annotation.NonNull;

import com.delivery.App;
import com.delivery.R;
import com.delivery.constant.AppConstants;
import com.delivery.model.DeliveryItem;
import com.delivery.model.Result;
import com.delivery.network.DeliveryService;
import java.util.List;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryViewModel extends ViewModel {

    private final DeliveryService deliveryService;
    public final MutableLiveData<List<DeliveryItem>> deliveryListMutableLiveData = new MutableLiveData<>();
    public final MutableLiveData<Boolean> progressBar = new MutableLiveData<>();
    public final MutableLiveData<Boolean> emptyView = new MutableLiveData<>();
    public final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private int offsetValue = 0;

    public DeliveryViewModel(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        emptyView.setValue(false);
    }

    public void loadDeliveriesNetwork(int offset) {
        this.offsetValue = offset;
        progressBar.setValue(true);
        Call<List<DeliveryItem>> deliveryItemCall = deliveryService.getDeliveryApi().
                getAllDeliveryItems(offset, AppConstants.PAGE_LIMIT);
        deliveryItemCall.enqueue(new DeliveryCallback());
    }

    private void setDeliveries(Result deliveryItems) {
        progressBar.setValue(false);

        if (offsetValue == 0 && (deliveryItems.getDeliveryItemList() == null || deliveryItems.getDeliveryItemList().isEmpty())) {
            emptyView.setValue(true);
        } else {
            emptyView.setValue(false);
            deliveryListMutableLiveData.setValue(deliveryItems.getDeliveryItemList());
        }

        String error = deliveryItems.getError();
        if (error != null) {
            if (error.contains(AppConstants.UNSATISFIABLE_REQUEST_IF_CACHED)) {
                errorMessage.setValue(App.getInstance().getResources().getString(R.string.no_internet_connection));
            } else {
                errorMessage.setValue(error);
            }
        }

    }

    public void onClickRetryButton() {
        progressBar.setValue(true);
        emptyView.setValue(false);
        loadDeliveriesNetwork(0);
    }

    private class DeliveryCallback implements Callback<List<DeliveryItem>> {

        @Override
        public void onResponse(@NonNull Call<List<DeliveryItem>> call, @NonNull Response<List<DeliveryItem>> response) {
            List<DeliveryItem> deliveryItemResponse = response.body();
            Result result;
            if (deliveryItemResponse == null)
                result = new Result(Result.Status.ERROR, null, response.message());
            else if (deliveryItemResponse.isEmpty()) {
                result = new Result(Result.Status.ERROR, null, App.getInstance().getString(R.string.no_data));
            } else
                result = new Result(Result.Status.SUCCESS, deliveryItemResponse, null);

            setDeliveries(result);
        }

        @Override
        public void onFailure(@NonNull Call<List<DeliveryItem>> call, @NonNull Throwable t) {
            Result result = new Result(Result.Status.ERROR, null, t.getMessage());
            setDeliveries(result);
        }
    }
}