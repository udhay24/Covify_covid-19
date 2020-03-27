package com.udhay.helfy.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udhay.helfy.data.CasesRepository
import com.udhay.helfy.data.model.CountryModel
import com.udhay.helfy.data.model.StateModel
import com.udhay.helfy.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val casesRepository: CasesRepository
) : ViewModel(), CoroutineScope {

    private val exceptionHandler: CoroutineExceptionHandler
    init {
         exceptionHandler = CoroutineExceptionHandler { _, error ->
            Log.e("Home View model", error.message ?: "")
        }

        retryData() //load the data for the first time
    }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    val countryCount: MutableLiveData<Resource<CountryModel.CountryModelItem, Exception>> =
        MutableLiveData()

    val stateCount: MutableLiveData<Resource<StateModel, Exception>> = MutableLiveData()

    fun retryData() {
        launch(exceptionHandler) {
            postCountryData()
            postStateData()
        }
    }

    private suspend fun postCountryData() {
        stateCount.postValue(Resource.LoadingResponse)
        val result = casesRepository.fetchStateData()
        if (result.isSuccessful) {
            stateCount.postValue(Resource.SuccessResponse(result.body()!!))
        } else {
            stateCount.postValue(Resource.FailureResponse(Exception(result.errorBody().toString())))
        }
    }

    private suspend fun postStateData() {
        countryCount.postValue(Resource.LoadingResponse)
        val result = casesRepository.fetchCountryData()
        if (result.isSuccessful) {
            countryCount.postValue(Resource.SuccessResponse(result.body()!![0]))
        } else {
            countryCount.postValue(
                Resource.FailureResponse(
                    Exception(
                        result.errorBody().toString()
                    )
                )
            )
        }
    }
}
