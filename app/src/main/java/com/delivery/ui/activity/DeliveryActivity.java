package com.delivery.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.delivery.R;
import com.delivery.network.DeliveryService;
import com.delivery.viewmodel.DeliveryViewModelFactory;
import com.delivery.databinding.ActivityMainBinding;
import com.delivery.listener.DeliveryItemListener;
import com.delivery.model.DeliveryItem;
import com.delivery.ui.adapter.DeliveryAdapter;
import com.delivery.constant.AppConstants;
import com.delivery.viewmodel.DeliveryItemViewModel;
import com.delivery.viewmodel.DeliveryViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.widget.LinearLayout.VERTICAL;

public class DeliveryActivity extends BaseActivity implements DeliveryItemListener {
    private DeliveryAdapter deliverAdapter;
    private DeliveryViewModel deliveryViewModel;
    private boolean loading = false;
    private DeliveryItemViewModel deliveryItemViewModel;
    private CoordinatorLayout parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDeliveryViewModelBindings();
        parentView = findViewById(R.id.main_content);
        deliveryViewModel.deliveryListMutableLiveData.observe(this, new DeliveryItemListObserver());
        deliveryViewModel.errorMessage.observe(this, new ErrorMessageObserver());
        deliveryViewModel.loadDeliveriesNetwork(0);
    }

    private void setupDeliveryViewModelBindings() {
        ActivityMainBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityBinding.setLifecycleOwner(this);
        DeliveryViewModelFactory factory = new DeliveryViewModelFactory(DeliveryService.getInstance());
        deliveryViewModel = ViewModelProviders.of(this, factory).get(DeliveryViewModel.class);
        activityBinding.setDeliveryViewModel(deliveryViewModel);
        deliveryItemViewModel = ViewModelProviders.of(this).get(DeliveryItemViewModel.class);
        setUpAdapter(activityBinding.recyclerView);
    }

    private void setUpAdapter(final RecyclerView recyclerView) {
        deliverAdapter = new DeliveryAdapter(this, deliveryItemViewModel);
        DividerItemDecoration decoration = new DividerItemDecoration(this, VERTICAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(deliverAdapter);
        setScrollListener(recyclerView);
    }

    private void setScrollListener(final RecyclerView recyclerView) {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager != null ? linearLayoutManager.getItemCount() : 0;
                int firstVisibleItemPos = linearLayoutManager != null ? linearLayoutManager.findFirstVisibleItemPosition() : 0;
                int visibleItemCount = linearLayoutManager != null ? linearLayoutManager.getChildCount() : 0;
                boolean isScrolledDown = dy > 0;

                if (!loading && isScrolledDown && (visibleItemCount + firstVisibleItemPos >= totalItemCount
                        && firstVisibleItemPos >= 0
                        && totalItemCount >= AppConstants.PAGE_LIMIT)) {
                    loading = true;
                    deliveryViewModel.loadDeliveriesNetwork(totalItemCount);
                }
            }
        });
    }


    @Override
    public void onDeliveryItemClick(DeliveryItem deliveryItem) {
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra(AppConstants.DELIVERY_ITEM_OBJECT, deliveryItem);
        startActivity(intent);
    }


    private class DeliveryItemListObserver implements Observer<List<DeliveryItem>> {
        @Override
        public void onChanged(@Nullable List<DeliveryItem> deliveryResult) {

            if (deliveryResult != null && !deliveryResult.isEmpty())
                deliverAdapter.addItems(deliveryResult);
                 loading = false;
        }
    }

    private class ErrorMessageObserver implements Observer<String> {
        @Override
        public void onChanged(@Nullable String error) {
            if (error != null && !error.isEmpty()) {
                Snackbar snackbar = Snackbar.make(parentView, error, Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(Color.RED);
                snackbar.show();
            }
        }
    }


}
