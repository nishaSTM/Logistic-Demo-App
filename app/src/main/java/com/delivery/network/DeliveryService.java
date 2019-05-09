package com.delivery.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.delivery.App;
import com.delivery.BuildConfig;
import com.google.gson.Gson;
import java.io.File;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeliveryService {
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final CharSequence NO_STORE = "no-store";
    private static final CharSequence NO_CACHE = "no-cache";
    private static final String OFFLINE_CACHE = "offlineCache";
    private static final String PUBLIC_ONLY_IF_CACHED = "public, only-if-cached";
    private static final CharSequence MUST_REVALIDATE = "must-revalidate";
    private static final String PUBLIC_MAX_AGE = "public, max-age=";
    private static final String PRAGMA = "Pragma";
    private static final long CACHE_SIZE = 10 * 1024 * 1024;
    private static final int CACHE_MAX = 5000;
    private static final CharSequence MAX_AGE_ZERO = "max-age=0";
    private DeliveryApi deliveryApi;

    private static DeliveryService instance;

    public static DeliveryService getInstance() {
        if(instance == null) {
            instance = new DeliveryService();
        }
        return instance;
    }

    private DeliveryService() {
        setupRetrofitAndOkHttpMethod(App.getInstance().getCacheDir());
    }

    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR = chain -> {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        String cacheControl = originalResponse.header(CACHE_CONTROL);
        if (cacheControl == null || cacheControl.contains(NO_STORE) || cacheControl.contains(NO_CACHE) ||
                cacheControl.contains(MUST_REVALIDATE) || cacheControl.contains(MAX_AGE_ZERO)) {
            return originalResponse.newBuilder()
                    .removeHeader(PRAGMA)
                    .header(CACHE_CONTROL, PUBLIC_MAX_AGE + CACHE_MAX)
                    .build();
        } else {
            return originalResponse;
        }
    };

    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = chain -> {
        Request request = chain.request();
        if (!isNetworkAvailable()) {
            request = request.newBuilder()
                    .removeHeader(PRAGMA)
                    .header(CACHE_CONTROL, PUBLIC_ONLY_IF_CACHED)
                    .build();
        }
        return chain.proceed(request);
    };

    private void setupRetrofitAndOkHttpMethod(File cacheDir) {
        File httpCacheDirectory = new File(cacheDir, OFFLINE_CACHE);
        Cache cache = new Cache(httpCacheDirectory, CACHE_SIZE);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .cache(cache)
                .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        deliveryApi = retrofit.create(DeliveryApi.class);
    }

    public DeliveryApi getDeliveryApi() {
        return deliveryApi;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}


