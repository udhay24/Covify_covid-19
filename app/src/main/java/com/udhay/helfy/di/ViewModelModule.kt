package com.udhay.helfy.di

import com.udhay.helfy.ui.home.HomeViewModel
import com.udhay.helfy.ui.twitter.TwitterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            casesRepository = get()
        )
    }

    viewModel {
        TwitterViewModel(
            twitterRepository = get(),
            remoteConfig = get()
        )
    }
}