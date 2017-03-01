package com.smedic.mvp;

import java.util.List;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public class Presenter {

    private static final String TAG = "SMEDIC Presenter";
    private ActivityView view;
    private NetworkServiceRepository model;

    public Presenter(ActivityView view, NetworkServiceRepository model) {
        this.view = view;
        this.model = model;
    }

    public void loadResults() {
        List<String> data = model.loadData();
        view.showResults(data);
    }
}
