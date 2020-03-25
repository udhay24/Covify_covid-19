package com.udhay.helfycovid_19.di

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import twitter4j.Twitter
import twitter4j.TwitterFactory

import twitter4j.conf.ConfigurationBuilder




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

val networkModule = module {
    single {
        retrofit(androidContext())
    }
    single<Twitter> {
        twitter()
    }
}

private val twitter: () -> Twitter = {
    val cb = ConfigurationBuilder()
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey("vYg4Yz8FXcarulLbBZyOzd9fj")
        .setOAuthConsumerSecret("a7byq5DZHbn1RHqa7MfmfAULWaLkuKV8IvEtAiIDFgm89UNJGH")
        .setOAuthAccessToken("1068147708546826240-Yms0PDeEqv0gfCdR7Bv2YSdrWKiFCa")
        .setOAuthAccessTokenSecret("E6680IkSxv0xYCgZ8AnVjkFD0oNylMDjpp3rEAFSa0As9")
    val tf = TwitterFactory(cb.build())
    tf.instance
}

val accessecret = "E6680IkSxv0xYCgZ8AnVjkFD0oNylMDjpp3rEAFSa0As9"
val accesstoken = "1068147708546826240-Yms0PDeEqv0gfCdR7Bv2YSdrWKiFCa"