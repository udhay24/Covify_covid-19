package com.udhay.helfycovid_19.di

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val covidApiClient: (context: Context) -> OkHttpClient = { context ->
    OkHttpClient()
        .newBuilder()
        .addInterceptor {
            var request = it.request()
            request =
                request.newBuilder()
                    .addHeader("x-api-key", "0059be4be9579db9a91a9cbf07b7788d4a5bc881")
                    .build()
            it.proceed(request)
        }
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.HEADERS
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .build()
}

private val retrofit: (context: Context) -> Retrofit = {
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.moviebuff.com/api/v2/")
        .client(covidApiClient(it))
        .build()
}

val retrofitModule = module {
    single {
        retrofit(androidContext())
    }
}