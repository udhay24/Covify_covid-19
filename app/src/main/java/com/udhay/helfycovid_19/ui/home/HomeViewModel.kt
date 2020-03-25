package com.udhay.helfycovid_19.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.udhay.helfycovid_19.data.CasesRepository
import com.udhay.helfycovid_19.data.model.CountryModel
import com.udhay.helfycovid_19.data.model.StateModel
import com.udhay.helfycovid_19.data.model.WorldModel
import com.udhay.helfycovid_19.util.Resource
import java.lang.Exception

class HomeViewModel(
    private val casesRepository: CasesRepository
) : ViewModel() {

    val countryCount: LiveData<Resource<CountryModel.CountryCountModelItem, Exception>> = liveData {
        emit(Resource.LoadingResponse)

        val result = casesRepository.fetchCountryData()
        if (result.isSuccessful) {
            emit(Resource.SuccessResponse(result.body()!![0]))
        } else {
            emit(Resource.FailureResponse(Exception(result.errorBody().toString())))
        }
    }

    val stateCount: LiveData<Resource<StateModel, Exception>> = liveData {
        emit(Resource.LoadingResponse)

        val result = casesRepository.fetchStateData()
        if (result.isSuccessful) {
            emit(Resource.SuccessResponse(result.body()!!))
        } else {
            emit(Resource.FailureResponse(Exception(result.errorBody().toString())))
        }
    }

    val worldCount: LiveData<Resource<WorldModel, Exception>> = liveData {
        emit(Resource.LoadingResponse)

        val result = casesRepository.fetchWorldData()
        if (result.isSuccessful) {
            emit(Resource.SuccessResponse(result.body()!!))
        } else {
            emit(Resource.FailureResponse(Exception(result.errorBody().toString())))
        }
    }
}
