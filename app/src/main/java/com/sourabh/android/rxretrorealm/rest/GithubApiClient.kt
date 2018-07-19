package com.sourabh.android.rxretrorealm.rest

import com.sourabh.android.rxretrorealm.modal.Repo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiClient {
    @GET("users/{user}/repos")
    fun getUserRepo(@Path("user") username: String): Observable<List<Repo>>
}
