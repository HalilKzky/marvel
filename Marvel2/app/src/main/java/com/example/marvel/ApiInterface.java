package com.example.marvel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiInterface {



    @GET
    Call<String> getString(@Url String url);

}