package com.smedic.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smedic.mvp.model.Employee;
import com.smedic.mvp.model.EmployeesResponse;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainActivityView, View.OnClickListener {

    private static final String TAG = "SMEDIC MainActivity";

    private static final String EXTRA_RX = "EXTRA_RX";

    private Button rxCall, retroCall;
    private TextView rxResponse, retroResponse;
    private ProgressBar progressBar;
    private NetworkService service;
    private boolean rxCallInWorks = false;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rxCall = (Button) findViewById(R.id.rxCall);
        retroCall = (Button) findViewById(R.id.retroCall);
        rxResponse = (TextView) findViewById(R.id.rxResponse);
        retroResponse = (TextView) findViewById(R.id.retroResponse);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rxCall.setOnClickListener(this);
        retroCall.setOnClickListener(this);
        service = ((RxApplication) getApplication()).getNetworkService();
        presenter = new Presenter(this, service);
        if (savedInstanceState != null) {
            rxCallInWorks = savedInstanceState.getBoolean(EXTRA_RX);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (rxCallInWorks)
            presenter.loadRxData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.rxUnSubscribe();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_RX, rxCallInWorks);
    }

    @Override
    public void showRetroInProcess() {
        retroResponse.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        retroCall.setEnabled(false);
        rxCall.setEnabled(false);
    }

    @Override
    public void showRetroFailure(Throwable throwable) {
        Log.d(TAG, throwable.toString());
        retroResponse.setText("ERROR");
        retroResponse.setVisibility(View.VISIBLE);
        retroCall.setEnabled(true);
        rxCall.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetroResults(Response<EmployeesResponse> response) {
        Log.d(TAG, "showRetroResults: ");
        StringBuilder builder = new StringBuilder();
        for (Employee employee : response.body().getEmployees()) {
            builder.append(employee.toString());
            builder.append("\n");
        }
        retroResponse.setText(builder.toString());
        retroResponse.setVisibility(View.VISIBLE);
        retroCall.setEnabled(true);
        rxCall.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRxInProcess() {
        rxResponse.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        retroCall.setEnabled(false);
        rxCall.setEnabled(false);
    }

    @Override
    public void showRxResults(EmployeesResponse response) {
        Log.d(TAG, "showRxResults: ");
        StringBuilder builder = new StringBuilder();
        for (Employee employee : response.getEmployees()) {
            builder.append(employee.toString());
            builder.append("\n");
        }
        rxResponse.setText(builder.toString());
        rxResponse.setVisibility(View.VISIBLE);
        rxCall.setEnabled(true);
        retroCall.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRxFailure(Throwable throwable) {
        Log.d(TAG, throwable.toString());
        rxResponse.setText("ERROR");
        rxResponse.setVisibility(View.VISIBLE);
        rxCall.setEnabled(true);
        retroCall.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retroCall:
                presenter.loadRetroData();
                break;
            case R.id.rxCall:
                rxCallInWorks = true;
                presenter.loadRxData();
                break;
        }
    }
}
