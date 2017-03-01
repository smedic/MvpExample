package com.smedic.mvp;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public class NetworkService implements NetworkServiceRepository {

    private static final String TAG = "SMEDIC";

    @Override
    public List<String> loadData() {
        Log.d(TAG, "loadData: ");
        return Arrays.asList("One", "Two", "Three");

    }
}
