package com.example.retrofitdemo.API;

import com.example.retrofitdemo.model.GitModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tejalpar on 3/21/16.
 */
public interface GitAPI {

    @GET("users/{user}")
    Call<GitModel> getFeedFromUser(@Path("user") String user);
}
