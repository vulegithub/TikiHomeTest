package com.vult.tikihometest.remoteservice;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WebApiService {
    @GET("android-home-test/v2/keywords.json")
    Observable<List<String>> getKeywords();
}
