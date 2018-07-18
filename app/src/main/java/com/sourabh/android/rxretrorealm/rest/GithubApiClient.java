package com.sourabh.android.rxretrorealm.rest;

import com.sourabh.android.rxretrorealm.modal.Repo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApiClient {
    @GET("users/{user}/repos")
    Observable<List<Repo>> getUserRepo(@Path("user") String username);
}
