package com.vult.tikihometest;

import com.vult.tikihometest.presenter.MainContract;
import com.vult.tikihometest.presenter.MainPresenter;
import com.vult.tikihometest.remoteservice.WebApiServiceImp;

import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@RunWith(JUnit4.class)
public class MainPresenterTest {

    @Mock
    private WebApiServiceImp mWebApiServiceImp;

    @Mock
    private MainContract.View mView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadData_have_data_Test() {

        // Because WebApiServiceImp call WebApiService to get String List, so we need to mock return
        // list
        String testData1 = "balo nữ";
        String testData2 = "xiaomi";
        List<String> mockStringList = new ArrayList<>();
        mockStringList.add(testData1);
        mockStringList.add(testData2);

        //mock(MainPresenter.class, withSettings().verboseLogging());

        when(mWebApiServiceImp.getKeywords())
                .thenReturn(Observable.just(mockStringList));

        MainPresenter mainPresenter = new MainPresenter(
                mView, mWebApiServiceImp,
                Schedulers.io(),
                Schedulers.io(), new CompositeDisposable()
        );

        mainPresenter.loadData();

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView, times(1)).onFetchDataStarted();
        inOrder.verify(mView, times(1)).onFetchDataSuccess(argThat(new ArgumentMatcher<List<String>>() {
            @Override
            public boolean matches(Object argument) {

                List<String> actualList = convertToList(argument);

                if (actualList.size() == mockStringList.size()) {
                    for (int index = 0; index < actualList.size(); index++) {
                        if (!actualList.get(index).equals(mockStringList.get(index))) {
                            return false;
                        }
                    }

                    return true;
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Expected List and Actual List are different.");
            }
        }));
        inOrder.verify(mView, times(1)).onFetchDataCompleted();
        //verify(mView).onFetchDataSuccess(eq(expectedKeywordList));
    }

    @Test
    public void loadData_Exception_Test() {

        //mock(MainPresenter.class, withSettings().verboseLogging());

        Exception exception = new Exception();

        when(mWebApiServiceImp.getKeywords())
                .thenReturn(Observable.error(exception));

        MainPresenter mainPresenter = new MainPresenter(
                mView, mWebApiServiceImp,
                Schedulers.io(),
                Schedulers.io(), new CompositeDisposable()
        );

        mainPresenter.loadData();

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView, times(1)).onFetchDataStarted();
        inOrder.verify(mView, times(1)).onFetchDataError(exception);
        inOrder.verify(mView, times(1)).onFetchDataCompleted();
    }

    @Test
    public void onDestroy_Test() {
        MainPresenter mainPresenter = new MainPresenter(
                mView, mWebApiServiceImp,
                Schedulers.io(),
                Schedulers.io(), new CompositeDisposable()
        );

        mainPresenter.onDestroy();
        Assert.assertNull("onDestroy_Test is not correct", mainPresenter.getCompositeDisposable());
        Assert.assertNull("onDestroy_Test is not correct", mainPresenter.getView());
    }

    @Test
    public void unsubscribe_Test() {
        MainPresenter mainPresenter = new MainPresenter(
                mView, mWebApiServiceImp,
                Schedulers.trampoline(),
                Schedulers.trampoline(), new CompositeDisposable()
        );

        mainPresenter.unsubscribe();
        Assert.assertEquals("unsubscribe_Test is not correct", 0, mainPresenter.getCompositeDisposable().size());
    }

    private List<String> convertToList(Object object) {
        List<String> result = new ArrayList<>();
        if (object instanceof List) {
            for (int i = 0; i < ((List<?>) object).size(); i++) {
                Object item = ((List<?>) object).get(i);
                if (item instanceof String) {
                    result.add((String) item);
                }
            }
        }
        return result;
    }
}