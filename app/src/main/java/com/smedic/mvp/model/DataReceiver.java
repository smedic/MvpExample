package com.smedic.mvp.model;

/**
 * Created by smedic on 4.3.17..
 */

public interface DataReceiver {
    void retroDataReceived(EmployeesResponse response);

    void retroDataFailed(Throwable t);

    void rxDataReceived(EmployeesResponse response);

    void rxDataFailed(Throwable t);
}
