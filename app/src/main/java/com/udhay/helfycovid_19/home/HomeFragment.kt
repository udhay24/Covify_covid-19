package com.udhay.helfycovid_19.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.udhay.helfycovid_19.R
import com.udhay.helfycovid_19.data.model.CountryModel
import com.udhay.helfycovid_19.data.model.StateModel
import com.udhay.helfycovid_19.data.model.WorldModel
import com.udhay.helfycovid_19.util.Resource
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.countryCount.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.SuccessResponse ->  updateCountryInfo(it.body)
                is Resource.FailureResponse -> Unit//TODO() show error message
                is Resource.LoadingResponse -> Unit //TODO() show loading message
            }
        })

        viewModel.stateCount.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.SuccessResponse ->  updateStateInfo(it.body)
                is Resource.FailureResponse -> Unit//TODO() show error message
                is Resource.LoadingResponse -> Unit //TODO() show loading message
            }
        })
    }

    private fun updateWorldInfo(worldModel: WorldModel) {
//        cases_text_view.text = worldModel.
    }

    private fun updateCountryInfo(countryModel: CountryModel) {
        cases_text_view.text = countryModel.confirmed_cases.toString()
        recovered_text_view.text = countryModel.recovered_cases.toString()
        deaths_text_view.text = countryModel.deaths.toString()
    }

    private fun updateStateInfo(stateModel: StateModel) {
        val worstStates = stateModel
            .sortedBy { it.confirmed_cases }
            .subList(0, 5)

    }
}
