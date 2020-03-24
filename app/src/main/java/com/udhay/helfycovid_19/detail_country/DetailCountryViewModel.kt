package com.udhay.helfycovid_19.detail_country

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.udhay.helfycovid_19.data.CasesRepository
import com.udhay.helfycovid_19.data.model.CountryModel
import com.udhay.helfycovid_19.util.Resource
import java.lang.Exception

class DetailCountryViewModel(
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
}
