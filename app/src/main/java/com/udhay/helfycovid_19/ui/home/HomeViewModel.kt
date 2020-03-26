package com.udhay.helfycovid_19.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.udhay.helfycovid_19.data.CasesRepository
import com.udhay.helfycovid_19.data.model.CountryModel
import com.udhay.helfycovid_19.data.model.StateModel
import com.udhay.helfycovid_19.data.model.WorldModel
import com.udhay.helfycovid_19.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val casesRepository: CasesRepository
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    val countryCount: MutableLiveData<Resource<CountryModel.CountryCountModelItem, Exception>> =
        MutableLiveData()

    val stateCount: MutableLiveData<Resource<StateModel, Exception>> = MutableLiveData()

    fun retryData() {
        launch {
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
