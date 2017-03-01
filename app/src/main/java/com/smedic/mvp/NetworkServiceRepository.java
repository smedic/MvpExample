package com.smedic.mvp;

import rx.Observable;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public interface NetworkServiceRepository {

    NetworkService.NetworkAPI getAPI();

    Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz, boolean cacheObservable, boolean useCache);
}
