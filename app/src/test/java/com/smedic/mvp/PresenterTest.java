package com.smedic.mvp;

import com.smedic.mvp.model.DataReceiver;
import com.smedic.mvp.model.Employee;
import com.smedic.mvp.model.EmployeesResponse;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public class PresenterTest {

    private boolean isLoadingShown;
    private boolean isLoadingDataStarted;
    private boolean isDataCallbackReceived;
    private boolean isListFilled;

    private boolean isUnsubscribed;

    private boolean isRetroLoadingFailed;
    private boolean isRxLoadingFailed;


    @Test
    public void shouldShowRetroResults() {
        MockActivityView view = new MockActivityView();
        MockRepository model = new MockRepository();

        Presenter presenter = new Presenter(view, model);
        model.setDataReceiver(presenter);

        presenter.loadRetroData();

        Assert.assertEquals(isLoadingShown, true);
        Assert.assertEquals(isLoadingDataStarted, true);
        Assert.assertEquals(isDataCallbackReceived, true);
        Assert.assertEquals(isListFilled, true);
    }

    @Test
    public void shouldShowRxResults() {
        MockActivityView view = new MockActivityView();
        MockRepository model = new MockRepository();

        Presenter presenter = new Presenter(view, model);
        model.setDataReceiver(presenter);

        presenter.loadRxData();

        Assert.assertEquals(isLoadingShown, true);
        Assert.assertEquals(isLoadingDataStarted, true);
        Assert.assertEquals(isDataCallbackReceived, true);
        Assert.assertEquals(isListFilled, true);
    }

    @Test
    public void shouldRxLoadingDataFail() {
        MockActivityView view = new MockActivityView();
        MockRepository model = new MockRepository(true);

        Presenter presenter = new Presenter(view, model);
        model.setDataReceiver(presenter);

        presenter.loadRxData();

        Assert.assertEquals(isRxLoadingFailed, true);
    }

    @Test
    public void shouldRetroLoadingDataFail() {
        MockActivityView view = new MockActivityView();
        MockRepository model = new MockRepository(true);

        Presenter presenter = new Presenter(view, model);
        model.setDataReceiver(presenter);

        presenter.loadRetroData();

        Assert.assertEquals(isRetroLoadingFailed, true);
    }

    @Test
    public void shouldBeUnsubscribed() {
        MockActivityView view = new MockActivityView();
        MockRepository model = new MockRepository();

        Presenter presenter = new Presenter(view, model);
        presenter.rxUnSubscribe();

        Assert.assertEquals(isUnsubscribed, true);
    }

    private class MockActivityView implements MainActivityView {

        @Override
        public void showRetroInProcess() {
            isLoadingShown = true;
        }

        @Override
        public void showRetroFailure(Throwable throwable) {
            isRetroLoadingFailed = true;
        }

        @Override
        public void showRetroResults(EmployeesResponse response) {
            if (response != null) {
                isDataCallbackReceived = true;

                if (response.getEmployees().size() > 0) {
                    isListFilled = true;
                }
            }
        }

        @Override
        public void showRxInProcess() {
            isLoadingShown = true;
        }

        @Override
        public void showRxResults(EmployeesResponse response) {
            if (response != null) {
                isDataCallbackReceived = true;

                if (response.getEmployees().size() > 0) {
                    isListFilled = true;
                }
            }
        }

        @Override
        public void showRxFailure(Throwable throwable) {
            isRxLoadingFailed = true;
        }
    }


    private class MockRepository implements NetworkServiceRepository {

        private DataReceiver receiver;
        private boolean shouldFail;

        public MockRepository() {
            this(false);
        }

        public MockRepository(boolean shouldFail) {
            this.shouldFail = shouldFail;
        }

        @Override
        public void loadRetroData() {
            isLoadingDataStarted = true;

            if (shouldFail) {
                receiver.retroDataFailed(new Throwable("FAILED"));
            } else {
                EmployeesResponse response = new EmployeesResponse();
                Employee employee1 = new Employee("AA", "BB", "CC");
                Employee employee2 = new Employee("AA", "BB", "CC");
                Employee employee3 = new Employee("AA", "BB", "CC");
                response.setEmployees(Arrays.asList(employee1, employee2, employee3));
                receiver.retroDataReceived(response);
            }
        }

        @Override
        public void loadRxData() {
            isLoadingDataStarted = true;

            if (shouldFail) {
                receiver.rxDataFailed(new Throwable("FAILED"));
            } else {
                EmployeesResponse response = new EmployeesResponse();
                Employee employee1 = new Employee("AA", "BB", "CC");
                Employee employee2 = new Employee("AA", "BB", "CC");
                Employee employee3 = new Employee("AA", "BB", "CC");
                response.setEmployees(Arrays.asList(employee1, employee2, employee3));
                receiver.rxDataReceived(response);
            }
        }

        @Override
        public void rxUnsubscribe() {
            isUnsubscribed = true;
        }

        @Override
        public void setDataReceiver(DataReceiver receiver) {
            this.receiver = receiver;
        }
    }
}
