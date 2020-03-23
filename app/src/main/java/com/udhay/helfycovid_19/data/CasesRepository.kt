package com.udhay.helfycovid_19.data

import com.google.gson.Gson
import com.udhay.helfycovid_19.data.model.CountryModel
import com.udhay.helfycovid_19.data.model.StateModel
import com.udhay.helfycovid_19.data.model.WorldModel
import com.udhay.helfycovid_19.util.testCountryJson
import com.udhay.helfycovid_19.util.testStateJson
import com.udhay.helfycovid_19.util.testWorldJson
import retrofit2.Response

class CasesRepository(
    private val service: CasesService
) {
    suspend fun fetchCountryData(): Response<CountryModel> {
        return Response.success(
            200,
            Gson().fromJson(testCountryJson, CountryModel::class.java)
        )
    }

    suspend fun fetchStateData(): Response<StateModel> {
        return Response.success(
            200,
            Gson().fromJson(testStateJson, StateModel::class.java)
        )
    }

    suspend fun fetchWorldData(): Response<WorldModel> {
        return Response.success(
            200,
            Gson().fromJson(testWorldJson, WorldModel::class.java)
        )
    }
}