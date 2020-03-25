package com.udhay.helfycovid_19

import android.app.Application
import com.udhay.helfycovid_19.di.repositoryModule
import com.udhay.helfycovid_19.di.networkModule
import com.udhay.helfycovid_19.di.retrofitServiceModule
import com.udhay.helfycovid_19.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                modules = arrayListOf(
                    viewModelModule,
                    networkModule,
                    retrofitServiceModule,
                    repositoryModule
                )
            )
        }
    }
}