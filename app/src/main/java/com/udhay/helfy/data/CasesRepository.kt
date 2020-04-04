package com.udhay.helfy.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.udhay.helfy.data.model.AppUpdateModel
import com.udhay.helfy.data.model.CountryModel
import com.udhay.helfy.data.model.StateModel
import com.udhay.helfy.data.model.WorldModel
import com.udhay.helfy.util.testWorldJson
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CasesRepository(
    private val service: CasesService
) {
    private val db = Firebase.firestore

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

    suspend fun fetchCurrentVersion(): Response<AppUpdateModel> {
        var response: Response<AppUpdateModel>
        return suspendCancellableCoroutine { coroutine ->
            db.collection("updates")
                .get()
                .addOnSuccessListener { result ->
                    val updateModel = AppUpdateModel(
                        result.documents[0].getLong("version_code")?.toInt() ?: 1,
                        result.documents[0].getString("download_url") ?: ""
                    )
                    response = Response.success(
                        200,
                        updateModel
                    )
                    coroutine.resume(response)
                }

        }
    }
}