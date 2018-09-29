package com.example.reza.firstca.Rest;

import com.example.reza.firstca.Data;

import retrofit2.http.Body;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiInterface {

    @GET("/?s=Batman&page=1&apikey=96099fe3")
    Observable<Data> getMovieList();
}
