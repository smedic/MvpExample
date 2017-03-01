package com.smedic.mvp;

import android.util.LruCache;

import com.smedic.mvp.model.EmployeesResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public class NetworkService implements NetworkServiceRepository {

    private static final String TAG = "SMEDIC";

    private static String baseUrl = "https://api.myjson.com";
    private NetworkAPI networkAPI;
    private OkHttpClient okHttpClient;
    private LruCache<Class<?>, Observable<?>> apiObservables;

    public NetworkService() {
        this(baseUrl);
    }

    public NetworkService(String baseUrl) {
        okHttpClient = buildClient();
        apiObservables = new LruCache<>(10);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        networkAPI = retrofit.create(NetworkAPI.class);
    }

    /**
     * Method to build and return an OkHttpClient so we can set/get
     * headers quickly and efficiently.
     *
     * @return
     */
    public OkHttpClient buildClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                // Do anything with response here
                //if we ant to grab a specific cookie or something..
                return response;
            }
        });

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //this is where we will add whatever we want to our request headers.
                Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
                return chain.proceed(request);
            }
        });

        return builder.build();
    }

    /**
     * Method to either return a cached observable or prepare a new one.
     *
     * @param unPreparedObservable
     * @param clazz
     * @param cacheObservable
     * @param useCache
     * @return Observable ready to be subscribed to
     */
    @Override
    public Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz, boolean cacheObservable, boolean useCache) {

        Observable<?> preparedObservable = null;

        if (useCache)//this way we don't reset anything in the cache if this is the only instance of us not wanting to use it.
            preparedObservable = apiObservables.get(clazz);

        if (preparedObservable != null)
            return preparedObservable;


        //we are here because we have never created this observable before or we didn't want to use the cache...
        preparedObservable = unPreparedObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        if (cacheObservable) {
            preparedObservable = preparedObservable.cache();
            apiObservables.put(clazz, preparedObservable);
        }


        return preparedObservable;
    }

    /**
     * Method to clear the entire cache of observables
     */
    public void clearCache() {
        apiObservables.evictAll();
    }

    @Override
    public NetworkAPI getAPI() {
        return networkAPI;
    }

    /**
     * call the Services to use for the retrofit requests.
     */
    public interface NetworkAPI {

        @GET("/bins/urald")
        Call<EmployeesResponse> getPersons();

        @GET("/bins/urald")
        Observable<EmployeesResponse> getPersonsObservable();
    }
}