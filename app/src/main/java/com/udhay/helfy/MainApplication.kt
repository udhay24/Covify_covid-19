package com.udhay.helfy

import android.app.Application
import com.udhay.helfy.di.repositoryModule
import com.udhay.helfy.di.networkModule
import com.udhay.helfy.di.retrofitServiceModule
import com.udhay.helfy.di.viewModelModule
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