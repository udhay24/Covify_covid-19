package com.udhay.helfycovid_19.detail_country

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Mapping
import com.anychart.graphics.vector.Stroke
import com.udhay.helfycovid_19.R
import com.udhay.helfycovid_19.data.model.CountryModel
import kotlinx.android.synthetic.main.graph_holder.view.*
import com.anychart.data.Set;
import com.anychart.enums.*


class GraphRecyclerAdapter(
    private val data: CountryModel
): RecyclerView.Adapter<GraphRecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.graph_holder, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (position == 0) {
            holder.showCasesDistribution(data)
        } else {
            holder.showTimelineGraph(data)
        }
    }

    inner class Holder(val view: View): RecyclerView.ViewHolder(view) {

        fun showCasesDistribution(countryData: CountryModel) {
            val pie = AnyChart.pie()
            APIlib.getInstance().setActiveAnyChartView(view.distribution_pie_chart)

            pie.setOnClickListener(object :
                ListenersInterface.OnClickListener(arrayOf("x", "value")) {
                override fun onClick(event: Event) {
                    Toast.makeText(
                        view.context,
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

            view.distribution_pie_chart.setChart(pie)
        }


        fun showTimelineGraph(countryData: CountryModel) {
            val anyChartView = view.distribution_pie_chart
            val cartesian: Cartesian = AnyChart.line()
            cartesian.animation(true)
            cartesian.padding(10.0, 20.0, 5.0, 20.0)
            cartesian.crosshair().enabled(true)
            cartesian.crosshair()
                .yLabel(true) // TODO ystroke
                .yStroke(null as Stroke?, null, null, null as String?, null as String?)
            cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
            cartesian.title("Timeline of the identified coronavirus cases in india")
            cartesian.yAxis(0).title("Confirmed Cases")
            cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)
            val seriesData: MutableList<DataEntry> = ArrayList()

            countryData.datewise_data.filterNotNull().forEach {
                seriesData.add(CustomDataEntry(it.date, it.confirmed_cases))
            }
            val set: Set = Set.instantiate()
            set.data(seriesData)
            val series1Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value' }")
            val series1: Line = cartesian.line(series1Mapping)
            series1.name("Identified Cases")
            series1.hovered().markers().enabled(true)
            series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4.0)

            series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5.0)
                .offsetY(5.0)

            cartesian.legend().enabled(true)
            cartesian.legend().fontSize(13.0)
            cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)
            anyChartView.setChart(cartesian)
        }
        private inner class CustomDataEntry internal constructor(
            x: String?,
            value: Number?
        ) : ValueDataEntry(x, value)
    }
}



