package com.smedic.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityView {

    private static final String TAG = "SMEDIC MainActivity";

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter(this, new NetworkService());
        presenter.loadResults();
    }

    @Override
    public void showResults(List<String> results) {
        Log.d(TAG, "showResults: ");
    }
}
