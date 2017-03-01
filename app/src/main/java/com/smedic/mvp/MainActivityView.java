package com.smedic.mvp;

import com.smedic.mvp.model.EmployeesResponse;

import retrofit2.Response;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public interface MainActivityView {

    void showRetroInProcess();

    void showRetroFailure(Throwable throwable);

    void showRetroResults(Response<EmployeesResponse> response);

    void showRxInProcess();

    void showRxResults(EmployeesResponse response);

    void showRxFailure(Throwable throwable);
}
