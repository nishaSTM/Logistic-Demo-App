package com.delivery;

import com.delivery.model.DeliveryItem;

import com.delivery.network.DeliveryApi;
import com.delivery.network.DeliveryService;
import com.delivery.viewmodel.DeliveryViewModel;
import com.delivery.constant.AppConstants;
import com.jraska.livedata.TestObserver;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryViewModelTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    private DeliveryViewModel deliveryViewModel;

    @Mock
    DeliveryService deliveryService;

    @Mock
    DeliveryApi deliveryApi;

    @Mock
    private Call<List<DeliveryItem>> deliveryItemList;

    @Before
    public void setUp() {
        deliveryViewModel = new DeliveryViewModel(deliveryService);
    }

    @Test
    public void testDeliveryViewModelResponseSuccess() throws InterruptedException {
        List<DeliveryItem> list = new ArrayList<>();
        DeliveryItem itemResponseModel = new DeliveryItem("desc", "imagUrl", "id", null);
        list.add(itemResponseModel);
        Mockito.doAnswer(invocation -> {
            Callback<List<DeliveryItem>> call = invocation.getArgument(0);
            call.onResponse(deliveryItemList, Response.success(list));
            return null;
        }).when(deliveryItemList).enqueue(Mockito.any());
        Mockito.when(deliveryApi.getAllDeliveryItems(0, AppConstants.PAGE_LIMIT)).thenReturn(deliveryItemList);
        Mockito.when(deliveryService.getDeliveryApi()).thenReturn(deliveryApi);
        deliveryViewModel.loadDeliveriesNetwork(0);

        TestObserver.test(deliveryViewModel.deliveryListMutableLiveData).awaitValue().assertHasValue()
                .assertValue(list);
    }

    @Test
    public void testDeliveryViewModelResponseFailure() throws InterruptedException {
        final String errorMessage = "Bad Request";
        Mockito.doAnswer(invocation -> {
            Callback<List<DeliveryItem>> call = invocation.getArgument(0);
            call.onFailure(deliveryItemList, new Throwable(errorMessage));
            return null;
        }).when(deliveryItemList).enqueue(Mockito.any());
        Mockito.when(deliveryApi.getAllDeliveryItems(0, AppConstants.PAGE_LIMIT)).thenReturn(deliveryItemList);
        Mockito.when(deliveryService.getDeliveryApi()).thenReturn(deliveryApi);
        deliveryViewModel.loadDeliveriesNetwork(0);
        TestObserver.test(deliveryViewModel.errorMessage).awaitValue()
                .assertValue(errorMessage);
    }
}
