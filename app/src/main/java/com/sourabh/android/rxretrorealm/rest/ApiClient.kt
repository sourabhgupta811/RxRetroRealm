package com.sourabh.android.rxretrorealm.rest

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sourabh.android.rxretrorealm.application.AppsConst

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor() {
    private var mRetrofit: Retrofit? = null
    val userGithubRepoClient: GithubApiClient
        get() = this.mRetrofit!!.create(GithubApiClient::class.java)

    init {
        this.buildApiClient()
    }

    private fun buildApiClient() {
        val builder = OkHttpClient().newBuilder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        mRetrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppsConst.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build()
    }

    companion object {
        private var instance: ApiClient? = null

        @Synchronized
        fun getInstance(): ApiClient {
            if (instance == null) {
                instance = ApiClient()
            }
            return instance as ApiClient
        }
    }
}
