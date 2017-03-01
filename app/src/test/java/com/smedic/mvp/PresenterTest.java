package com.smedic.mvp;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        presenter.loadResults();

        Assert.assertEquals(isShown, true);
    }

    private class MockActivityView implements ActivityView {
        @Override
        public void showResults(List<String> results) {
            isShown = true;
        }
    }

    private class MockRepository implements NetworkServiceRepository {
        @Override
        public List<String> loadData() {
            return Arrays.asList("One", "Two", "Three");
        }
    }

}
