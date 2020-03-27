package com.udhay.helfy.di

import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.udhay.helfy.R
import com.udhay.helfy.util.Constants
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
        .baseUrl("https://helfy-covid.herokuapp.com/")
        .client(covidApiClient(it))
        .build()
}

val networkModule = module {
    single {
        retrofit(androidContext())
    }

    single {
        getFirebaseRemoteConfig()
    }

    single {
        getTwitterClient(
            remoteConfig = get()
        )
    }
}

private fun getTwitterClient(remoteConfig: FirebaseRemoteConfig): Twitter {
    val cb = ConfigurationBuilder()
    cb.setDebugEnabled(true)
        .setOAuthConsumerKey(remoteConfig.getString(Constants.O_AUTH_CONSUMER_KEY))
        .setOAuthConsumerSecret(remoteConfig.getString(Constants.O_AUTH_CONSUMER_KEY_SECRET))
        .setOAuthAccessToken(remoteConfig.getString(Constants.O_AUTH_ACCESS_TOKEN))
        .setOAuthAccessTokenSecret(remoteConfig.getString(Constants.O_AUTH_ACCESS_TOKEN_SECRET))
    val tf = TwitterFactory(cb.build())
    return tf.instance
}

private fun getFirebaseRemoteConfig(): FirebaseRemoteConfig  {
    val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    val configSettings = FirebaseRemoteConfigSettings.Builder()
        .setMinimumFetchIntervalInSeconds(3600)
        .build()
    mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)

    mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config)
    return mFirebaseRemoteConfig
}
