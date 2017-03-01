package com.smedic.mvp;

import android.app.Application;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public class RxApplication extends Application {

    private NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();
        networkService = new NetworkService();
    }

    public NetworkService getNetworkService() {
        return networkService;
    }
}
