package com.udhay.helfycovid_19.detail_country

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.*
import com.anychart.graphics.vector.Stroke
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.udhay.helfycovid_19.R
import com.udhay.helfycovid_19.data.model.CountryModel
import com.udhay.helfycovid_19.util.Resource
import kotlinx.android.synthetic.main.detail_country_fragment.*
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
                    showCasesDistribution(it.body)
                    showCasesTimeline(it.body)

                }
                is Resource.FailureResponse -> Unit//TODO() show error message
                is Resource.LoadingResponse -> Unit //TODO() show loading message
            }
        })
    }

    private fun showCasesDistribution(countryData: CountryModel) {
        val pie = AnyChart.pie()
        APIlib.getInstance().setActiveAnyChartView(distribution_pie_chart)

        pie.setOnClickListener(object :
            ListenersInterface.OnClickListener(arrayOf("x", "value")) {
            override fun onClick(event: Event) {
                Toast.makeText(
                    this@DetailCountryFragment.requireContext(),
                    event.data["x"].toString() + ":" + event.data["value"],
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        val data: MutableList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Confirmed", countryData.confirmed_cases))
        data.add(ValueDataEntry("hospitalized", countryData.hospitalised_cases))
        data.add(ValueDataEntry("ICU", countryData.icu_cases))
        data.add(ValueDataEntry("Recovered", countryData.recovered_cases))
        data.add(ValueDataEntry("Deaths", countryData.deaths))

        pie.data(data)

        pie.title("Cases Distribution")

        pie.labels().position("outside")

        pie.legend().title().enabled(true)

        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)

        distribution_pie_chart.setChart(pie)

    }

    private fun showCasesTimeline(countryData: CountryModel) {
        val map: MutableList<Entry> = mutableListOf()
        countryData.datewise_data.filterNotNull().forEachIndexed { index, dateCases ->
            map.add(Entry(index.toFloat(), dateCases.confirmed_cases.toFloat()))
        }
        val dataSet = LineData(LineDataSet(map, "Label"))// add entries to dataset
        chart.data = dataSet
    }
}
