package com.smedic.mvp;

import com.smedic.mvp.model.EmployeesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public class Presenter {

    private static final String TAG = "SMEDIC Presenter";
    private MainActivityView view;
    private NetworkServiceRepository service; //model
    private Subscription subscription;

    public Presenter(MainActivityView view, NetworkServiceRepository service) {
        this.view = view;
        this.service = service;
    }


    public void loadRxData() {
        view.showRxInProcess();
        @SuppressWarnings("unchecked")
        Observable<EmployeesResponse> friendResponseObservable = (Observable<EmployeesResponse>)
                service.getPreparedObservable(service.getAPI().getPersonsObservable(), EmployeesResponse.class, true, true);
        subscription = friendResponseObservable.subscribe(new Observer<EmployeesResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.showRxFailure(e);
            }

            @Override
            public void onNext(EmployeesResponse response) {
                view.showRxResults(response);
            }
        });
    }

    public void loadRetroData() {
        view.showRetroInProcess();
        Call<EmployeesResponse> call = service.getAPI().getPersons();
        call.enqueue(new Callback<EmployeesResponse>() {
            @Override
            public void onResponse(Call<EmployeesResponse> call, Response<EmployeesResponse> response) {
                view.showRetroResults(response);
            }

            @Override
            public void onFailure(Call<EmployeesResponse> call, Throwable t) {
                view.showRetroFailure(t);
            }
        });
    }

    public void rxUnSubscribe() {
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
