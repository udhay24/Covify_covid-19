package com.udhay.helfycovid_19.detail_country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.enums.*
import com.udhay.helfycovid_19.R
import com.udhay.helfycovid_19.data.model.CountryModel
import com.udhay.helfycovid_19.util.Resource
import kotlinx.android.synthetic.main.detail_country_fragment.*
import kotlinx.android.synthetic.main.graph_holder.view.*
import org.koin.android.ext.android.inject


class DetailCountryFragment : Fragment() {

    private val viewModel: DetailCountryViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_country_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.countryCount.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.SuccessResponse -> {
//                    showCasesDistribution(it.body)
//                    showCasesTimeline(it.body)
                    graph_view_pager.adapter = GraphRecyclerAdapter(it.body)

                }
                is Resource.FailureResponse -> Unit//TODO() show error message
                is Resource.LoadingResponse -> Unit //TODO() show loading message
            }
        })
    }
}
