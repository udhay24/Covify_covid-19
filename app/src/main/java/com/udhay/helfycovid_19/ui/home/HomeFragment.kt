package com.udhay.helfycovid_19.ui.home

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.udhay.helfycovid_19.R
import com.udhay.helfycovid_19.data.model.CountryModel
import com.udhay.helfycovid_19.data.model.GenericDistributionModel
import com.udhay.helfycovid_19.data.model.GenericTimeFrequencyModel
import com.udhay.helfycovid_19.data.model.StateModel
import com.udhay.helfycovid_19.util.Resource
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.more_covid_tasks.*
import org.koin.android.ext.android.inject


class HomeFragment : Fragment(), StatesRecyclerAdapter.StateClickListener {

    private val viewModel: HomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.countryCount.observe(viewLifecycleOwner, Observer {countryData ->
            when(countryData) {
                is Resource.SuccessResponse ->  {
                    updateCountryInfo(countryData.body)
                    distribution_view_more_text.setOnClickListener {
                        val distributionData = GenericDistributionModel(
                            confirmedCases = countryData.body.confirmed_case,
                            hospitalizedCases = countryData.body.hospitalised_case,
                            recoveredCases = countryData.body.recovered,
                            deaths = countryData.body.death
                        )

                        val frequencyModel = GenericTimeFrequencyModel(
                            countryData.body.datewise_data.map {
                                GenericTimeFrequencyModel.TimeCases(
                                    it.date, it.hospitalised_case, it.recovered, it.death
                                )
                            }
                        )
                        val navOptions = HomeFragmentDirections.actionHomeFragmentToDetailCountryFragment(
                            distributionData, frequencyModel
                        )
                            findNavController().navigate(navOptions)
                    }
                }
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
        self_assesment_card.setOnClickListener {
            val webUrl = "https://covid.apollo247.com/"
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(ContextCompat.getColor(this@HomeFragment.requireContext(), R.color.colorPrimary))
            builder.addDefaultShareMenuItem()
            builder.setShowTitle(true)
            builder.setCloseButtonIcon(BitmapFactory.decodeResource(resources, android.R.drawable.ic_menu_close_clear_cancel))
            builder.setExitAnimations(this@HomeFragment.requireContext(), android.R.anim.fade_in, android.R.anim.fade_out)
            builder.build().launchUrl(this@HomeFragment.requireContext(), Uri.parse(webUrl))
        }
        twitter_card.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_twitterFragment)
        }
    }


    private fun updateCountryInfo(countryModel: CountryModel.CountryCountModelItem) {
        cases_text_view.text = countryModel.confirmed_case.toString()
        recovered_text_view.text = countryModel.recovered.toString()
        deaths_text_view.text = countryModel.death.toString()
    }

    private fun updateStateInfo(stateModel: StateModel) {
        val worstStates = stateModel
            .sortedByDescending { it.confirmed_case }

        states_recycler_view.adapter = StatesRecyclerAdapter(worstStates, this)
    }

    private fun displayMap() {
        val mapUrl = MapDetailFragment.MAP_URL
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
        affected_web_view.setOnTouchListener { _, event -> event.action == MotionEvent.ACTION_MOVE }

        map_detail_text_view.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapDetailFragment)
        }
    }

    override fun stateClicked(state: StateModel.StateModelItem) {
        val distributionModel = GenericDistributionModel(
            confirmedCases = state.confirmed_case,
            hospitalizedCases = state.hospitalised_case,
            recoveredCases = state.recovered,
            deaths = state.death
        )

        val timeFrequencyModel = GenericTimeFrequencyModel(
            state.datewise_data.map {
                GenericTimeFrequencyModel.TimeCases(
                    it.date, it.confirmed_case, it.recovered, it.death
                )
            }
        )
        val navOptions = HomeFragmentDirections.actionHomeFragmentToDetailCountryFragment(
            distributionModel, timeFrequencyModel
        )
        findNavController().navigate(navOptions)
    }
}
