package com.udhay.helfycovid_19.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.udhay.helfycovid_19.data.CasesRepository
import com.udhay.helfycovid_19.data.model.CountryModel
import com.udhay.helfycovid_19.data.model.StateModel
import com.udhay.helfycovid_19.data.model.WorldModel
import com.udhay.helfycovid_19.util.Resource
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception

class HomeViewModel(
    private val repository: CasesRepository
) : ViewModel() {

    val countryCount: LiveData<Resource<CountryModel, Exception>> = liveData {
        emit(Resource.LoadingResponse)

        val result = repository.fetchCountryData()
        if (result.isSuccessful) {
            emit(Resource.SuccessResponse(result.body()!!))
        } else {
            emit(Resource.FailureResponse(Exception(result.errorBody().toString())))
        }
    }

    val stateCount: LiveData<Resource<StateModel, Exception>> = liveData {
        emit(Resource.LoadingResponse)

        val result = repository.fetchStateData()
        if (result.isSuccessful) {
            emit(Resource.SuccessResponse(result.body()!!))
        } else {
            emit(Resource.FailureResponse(Exception(result.errorBody().toString())))
        }
    }

    val worldCount: LiveData<Resource<WorldModel, Exception>> = liveData {
        emit(Resource.LoadingResponse)

        val result = repository.fetchWorldData()
        if (result.isSuccessful) {
            emit(Resource.SuccessResponse(result.body()!!))
        } else {
            emit(Resource.FailureResponse(Exception(result.errorBody().toString())))
        }
    }


}
