package com.udhay.helfycovid_19.di

import com.udhay.helfycovid_19.data.CasesRepository
import com.udhay.helfycovid_19.data.CasesService
import org.koin.dsl.module
import retrofit2.Retrofit

val repositoryModule = module {
    single {
        CasesRepository(service = get())
    }
}

val retrofitServiceModule = module {
    single {
        get<Retrofit>()
            .create(CasesService::class.java)
    }
}