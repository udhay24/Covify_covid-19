package com.udhay.helfycovid_19.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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
        displayMap()
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
        states_recycler_view.adapter = StatesRecyclerAdapter(worstStates)
    }

    private fun displayMap() {
        val mapUrl = "https://who.maps.arcgis.com/apps/opsdashboard/index.html#/c88e37cfc43b4ed3baf977d77e4a0667"
        affected_web_view.settings.javaScriptEnabled = true // enable javascript
        affected_web_view.webViewClient = object: WebViewClient() {
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                Toast.makeText(this@HomeFragment.requireContext(), error?.description, Toast.LENGTH_SHORT).show()
            }
        }
        affected_web_view.loadUrl(mapUrl)
    }

}
