package com.android.omiplekevin.workoutheaders.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by OMIPLEKEVIN on January 03, 2018.
 * F45PowerAndroid
 * com.f45tv.f45powerandroid.helper.services
 */

public class RequestManager {

    ///////////////////////////////////////////////////////////////////////////
    // STATIC
    ///////////////////////////////////////////////////////////////////////////
    private static final String TAG = "RequestManager";
    private static final int MAX_RETRY_COUNT = 5;
    private static volatile RequestManager _instance;
    private static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = null;

    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE
    ///////////////////////////////////////////////////////////////////////////
    private OkHttpClient client;

    public static RequestManager getInstance(Context context) {
        if (_instance == null) {
            synchronized (RequestManager.class) {
                if (_instance == null) {
                    _instance = new RequestManager(context);
                }
            }
        }
        return _instance;
    }

    private RequestManager(Context context) {
        Log.d(TAG, "RequestManager: constructor");

        initialize(context);
    }

    private void initialize(final Context context) {
        //based on <code>https://stackoverflow.com/a/23503804</code> accepted answer
        if (REWRITE_CACHE_CONTROL_INTERCEPTOR == null) {
            REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    int status = NetworkUtilities.getConnectivityStatus(context);
                    if (status != NetworkUtilities.TYPE_NOT_CONNECTED) {
                        int maxAge = 60; // read from cache for 1 minute
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .build();
                    } else {
                        int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .build();
                    }
                }

            };
        } else {
            Log.d(TAG, "initialize: CACHE_CONTROL_INTERCEPTOR is already initialized");
        }

        if (client == null) {
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            int cacheSize = 10*1024*1024; // 10MB of cache
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(3);
            client = new OkHttpClient.Builder()
                    .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .cache(cache)
                    .dispatcher(dispatcher)
                    .build();
        }
    }

    public Retrofit getRetrofit(@NonNull String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
