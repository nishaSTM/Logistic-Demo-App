package com.delivery.ui.adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.delivery.R;
import com.delivery.databinding.ItemDeliveryBinding;
import com.delivery.listener.DeliveryItemListener;
import com.delivery.model.DeliveryItem;
import com.delivery.viewmodel.DeliveryItemViewModel;
import java.util.ArrayList;
import java.util.List;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    private final List<DeliveryItem> deliveryItemList;
    private final DeliveryItemListener deliveryItemListener;
    private final DeliveryItemViewModel deliveryItemViewModel;

    public DeliveryAdapter(DeliveryItemListener deliveryItemListener, DeliveryItemViewModel deliveryItemViewModel) {
        this.deliveryItemListener = deliveryItemListener;
        deliveryItemList = new ArrayList<>();
        this.deliveryItemViewModel = deliveryItemViewModel;
    }

    public void addItems(List<DeliveryItem> deliveryItems) {
        int positionStart = getItemCount();
        int positionEnd = positionStart + deliveryItems.size();
        deliveryItemList.addAll(deliveryItems);
        notifyItemRangeInserted(positionStart, positionEnd);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ItemDeliveryBinding itemDeliveryBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_delivery, parent,
                        false);
        return new ViewHolder(itemDeliveryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bindDeliveryItem(getItem(position));
        holder.itemView.setOnClickListener(v -> deliveryItemListener.onDeliveryItemClick(getItem(position)));
    }

    @Override
    public int getItemCount() {
        return deliveryItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private DeliveryItem getItem(int position) {
        return deliveryItemList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemDeliveryBinding itemDeliveryBinding;

        private ViewHolder(final ItemDeliveryBinding itemDeliveryBinding) {
            super(itemDeliveryBinding.getRoot());
            this.itemDeliveryBinding = itemDeliveryBinding;
        }

        void bindDeliveryItem(DeliveryItem deliveryItem) {
            if (itemDeliveryBinding.getDeliveryItemViewModel() == null) {
                itemDeliveryBinding.setDeliveryItemViewModel(deliveryItemViewModel);
            }
            itemDeliveryBinding.getDeliveryItemViewModel().setDeliveryItem(deliveryItem);
            itemDeliveryBinding.executePendingBindings();
        }
    }
}
