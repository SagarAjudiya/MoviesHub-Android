package com.example.movieshub.api

import com.example.movieshub.BuildConfig
import com.example.movieshub.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val mHttpLoggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.BASIC
        )
    }

    private val client =
        OkHttpClient.Builder().addInterceptor(mHttpLoggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("accept", "application/json")
                    .addHeader(
                        "Authorization",
                        "Bearer ${Constants.AUTH_TOKEN}"
                    ).build()
                chain.proceed(request)
            }.build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

}