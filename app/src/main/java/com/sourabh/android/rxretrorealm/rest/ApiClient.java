package com.sourabh.android.rxretrorealm.rest;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sourabh.android.rxretrorealm.application.AppsConst;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static ApiClient instance;
    Retrofit mRetrofit;
    private ApiClient(){
        buildApiClient();
    }

    public synchronized static ApiClient getInstance(){
        if(instance == null){
            instance = new ApiClient();
        }
        return instance;
    }
    private void buildApiClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppsConst.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
    }
    public GithubApiClient getUserGithubRepoClient(){
        return mRetrofit.create(GithubApiClient.class);
    }
}
