package com.udhay.helfycovid_19.di

import com.udhay.helfycovid_19.ui.home.HomeViewModel
import com.udhay.helfycovid_19.ui.twitter.TwitterViewModel
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
            twitterRepository = get()
        )
    }
}