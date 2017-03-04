package com.smedic.mvp;

import com.smedic.mvp.model.DataReceiver;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public interface NetworkServiceRepository {

    //NetworkService.NetworkAPI getAPI();

    //Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz, boolean cacheObservable, boolean useCache);

    void loadRetroData();
    void loadRxData();
    void rxUnsubscribe();
    void setDataReceiver(DataReceiver receiver);
}
