package com.udhay.helfycovid_19.data

import com.udhay.helfycovid_19.data.model.CountryModel
import com.udhay.helfycovid_19.data.model.StateModel
import retrofit2.Response
import retrofit2.http.GET

interface CasesService {

    @GET("country")
    suspend fun fetchCountryData(): Response<CountryModel>

    @GET("states")
    suspend fun fetchStatesData(): Response<StateModel>
}