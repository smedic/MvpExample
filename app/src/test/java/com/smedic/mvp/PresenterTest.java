package com.smedic.mvp;

import com.smedic.mvp.model.EmployeesResponse;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by Stevan Medic on 1.3.17..
 */

public class PresenterTest {

    private static final String TAG = "SMEDIC test";

    private boolean isShown;

    @Test
    public void shouldShowResults() {
        MockActivityView view = new MockActivityView();
        MockRepository model = new MockRepository();

        Presenter presenter = new Presenter(view, model);
        presenter.loadRetroData();

        //Assert.assertEquals(isShown, true);
    }

    private class MockActivityView implements MainActivityView {

        @Override
        public void showRetroInProcess() {

        }

        @Override
        public void showRetroFailure(Throwable throwable) {

        }

        @Override
        public void showRetroResults(Response<EmployeesResponse> response) {

        }

        @Override
        public void showRxInProcess() {

        }

        @Override
        public void showRxResults(EmployeesResponse response) {

        }

        @Override
        public void showRxFailure(Throwable throwable) {

        }
    }



    private class MockRepository implements NetworkServiceRepository {
        @Override
        public NetworkService.NetworkAPI getAPI() {
            return null;
        }

        @Override
        public Observable<?> getPreparedObservable(Observable<?> unPreparedObservable, Class<?> clazz, boolean cacheObservable, boolean useCache) {
            return null;
        }
    }

    private class MockNetworkApi implements NetworkService.NetworkAPI {
        @Override
        public Call<EmployeesResponse> getPersons() {
            return null;
        }

        @Override
        public Observable<EmployeesResponse> getPersonsObservable() {
            return null;
        }
    }

}
