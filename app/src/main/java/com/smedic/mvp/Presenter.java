package com.smedic.mvp;

import com.smedic.mvp.model.DataReceiver;
import com.smedic.mvp.model.EmployeesResponse;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public class Presenter implements DataReceiver {

    private static final String TAG = "SMEDIC Presenter";
    private MainActivityView view;
    private NetworkServiceRepository service; //model

    public Presenter(MainActivityView view, NetworkServiceRepository service) {
        this.view = view;
        this.service = service;
        this.service.setDataReceiver(this);
    }

    public void loadRxData() {
        view.showRxInProcess();
        service.loadRxData();
    }

    public void loadRetroData() {
        view.showRetroInProcess();
        service.loadRetroData();
    }

    public void rxUnSubscribe() {
        service.rxUnsubscribe();
    }

    // Received from model
    @Override
    public void retroDataReceived(EmployeesResponse response) {
        view.showRetroResults(response);
    }

    @Override
    public void retroDataFailed(Throwable t) {
        view.showRetroFailure(t);
    }

    @Override
    public void rxDataReceived(EmployeesResponse response) {
        view.showRxResults(response);
    }

    @Override
    public void rxDataFailed(Throwable t) {
        view.showRxFailure(t);
    }
}
