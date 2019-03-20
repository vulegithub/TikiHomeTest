package com.vult.tikihometest.remoteservice;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebApiServiceImp implements WebApiService {

    private WebApiService mWebApiService;
    private static final String URL = "https://raw.githubusercontent.com/tikivn/";

    public WebApiServiceImp() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mWebApiService = retrofit.create(WebApiService.class);
    }

    @Override
    public Observable<List<String>> getKeywords() {
        return mWebApiService.getKeywords();
    }

}
