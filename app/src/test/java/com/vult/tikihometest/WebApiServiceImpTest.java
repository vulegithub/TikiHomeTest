package com.vult.tikihometest;

import com.vult.tikihometest.presenter.MainPresenter;
import com.vult.tikihometest.remoteservice.WebApiService;
import com.vult.tikihometest.remoteservice.WebApiServiceImp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@RunWith(JUnit4.class)
public class WebApiServiceImpTest {

    @Mock
    private WebApiService mWebApiService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getKeywords_Exception_Test() {

        mock(MainPresenter.class, withSettings().verboseLogging());

        Exception exception = new Exception("HTTP 404 Not Found");

        when(mWebApiService.getKeywords())
                .thenReturn(Observable.error(exception));

        WebApiServiceImp webApiServiceImp = new WebApiServiceImp();
        Observable<List<String>> result = webApiServiceImp.getKeywords();
        result.subscribe(new Observer<List<String>>() {
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> strings) {
            }

            @Override
            public void onError(Throwable e) {
                Assert.assertEquals("getKeywords_Exception_Test is not correct", exception.getMessage(), e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void getKeywords_have_data_Test() {

        String testData1 = "balo nữ";
        String testData2 = "xiaomi";
        List<String> list = new ArrayList<>();
        list.add(testData1);
        list.add(testData2);
        when(mWebApiService.getKeywords())
                .thenReturn(Observable.just(list));

        WebApiServiceImp webApiServiceImp = new WebApiServiceImp();
        Observable<List<String>> result = webApiServiceImp.getKeywords();
        result.subscribe(new Observer<List<String>>() {
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> strings) {
                Assert.assertEquals("getKeywords_Test is not correct", strings, list);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}