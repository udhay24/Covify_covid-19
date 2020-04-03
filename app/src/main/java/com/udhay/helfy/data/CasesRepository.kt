package com.udhay.helfy.data

import com.google.gson.Gson
import com.udhay.helfy.data.model.CountryModel
import com.udhay.helfy.data.model.StateModel
import com.udhay.helfy.data.model.WorldModel
import com.udhay.helfy.util.testWorldJson
import retrofit2.Response

class CasesRepository(
    private val service: CasesService
) {
    suspend fun fetchCountryData(): Response<CountryModel> {
        return service.fetchCountryData()
    }

    suspend fun fetchStateData(): Response<StateModel> {
        return service.fetchStatesData()
    }

    suspend fun fetchWorldData(): Response<WorldModel> {
        return Response.success(
            200,
            Gson().fromJson(testWorldJson, WorldModel::class.java)
        )
    }
}