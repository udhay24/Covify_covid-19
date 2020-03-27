package com.udhay.helfy.di

import com.udhay.helfy.data.CasesRepository
import com.udhay.helfy.data.CasesService
import com.udhay.helfy.data.TwitterRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {
    single {
        CasesRepository(service = get())
    }

    single {
        TwitterRepository(
            twitter = get()
        )
    }
}

val retrofitServiceModule = module {
    single {
        get<Retrofit>()
            .create(CasesService::class.java)
    }
}
