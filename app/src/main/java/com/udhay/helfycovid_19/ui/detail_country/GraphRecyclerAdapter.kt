package com.udhay.helfycovid_19.ui.detail_country

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.udhay.helfycovid_19.R
import com.udhay.helfycovid_19.data.model.GenericDistributionModel
import com.udhay.helfycovid_19.data.model.GenericTimeFrequencyModel
import kotlinx.android.synthetic.main.line_chart_graph.view.*
import kotlinx.android.synthetic.main.pie_chart_graph.view.*
import java.text.SimpleDateFormat
import java.util.*


class GraphRecyclerAdapter(
    private val distributionModel: GenericDistributionModel,
    private val timeFrequencyModel: GenericTimeFrequencyModel
): RecyclerView.Adapter<GraphRecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = when(viewType) {
            0 -> LayoutInflater.from(parent.context).inflate(R.layout.line_chart_graph, parent, false)
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.pie_chart_graph, parent, false)
            else -> View(parent.context)
        }
        return Holder(view)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = 2

    @ExperimentalStdlibApi
    override fun onBindViewHolder(holder: Holder, position: Int) {
        when(position) {
            0 -> holder.showFrequencyChart()
            1 -> holder.showDistributionGraph(distributionModel)
        }
    }

    inner class Holder(private val view: View): RecyclerView.ViewHolder(view) {


        fun showDistributionGraph(distributionModel: GenericDistributionModel) {
            view.total_confirmed_text_view.text = distributionModel.confirmedCases.toString()
            view.pie_chart.setUsePercentValues(true)
            view.pie_chart.description.isEnabled = false
            view.pie_chart.setExtraOffsets(5f, 10f, 5f, 5f)
            view.pie_chart.dragDecelerationFrictionCoef = 0.95f

            view.pie_chart.centerText = generateCenterSpannableText()

            view.pie_chart.setExtraOffsets(20f, 0f, 20f, 0f)

            view.pie_chart.isDrawHoleEnabled = true
            view.pie_chart.setHoleColor(Color.WHITE)

            view.pie_chart.setTransparentCircleColor(Color.WHITE)
            view.pie_chart.setTransparentCircleAlpha(110)

            view.pie_chart.holeRadius = 58f
            view.pie_chart.transparentCircleRadius = 61f

            view.pie_chart.setDrawCenterText(true)

            view.pie_chart.isRotationEnabled = false
            view.pie_chart.isHighlightPerTapEnabled = true

            view.pie_chart.animateY(1400, Easing.EaseInOutQuart)

            val l: Legend = view.pie_chart.getLegend()
            l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            l.orientation = Legend.LegendOrientation.VERTICAL
            l.setDrawInside(false)
            l.isEnabled = false


            val pieDataset = PieDataSet(listOf(
                PieEntry(distributionModel.deaths.toFloat(), "Deaths"),
                PieEntry(distributionModel.hospitalizedCases.toFloat(), "Hospitals"),
                PieEntry(distributionModel.recoveredCases.toFloat(), "Recovered")
            ), "Cases Distribution")

            pieDataset.sliceSpace = 3f
            pieDataset.selectionShift = 5f

            // add a lot of colors
            val colors = ArrayList<Int>()

//            for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)

//            for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)

//            for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
//
            for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)

//            for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)

            colors.add(ColorTemplate.getHoloBlue())

            pieDataset.colors = colors

            pieDataset.selectionShift = 0f
            pieDataset.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

            pieDataset.valueLinePart1OffsetPercentage = 90f
            pieDataset.valueLinePart1Length = 0.10f
            pieDataset.valueLinePart2Length = 0.50f

            val pieData = PieData(pieDataset)
            pieData.setValueFormatter(PercentFormatter())
            pieData.setValueTextSize(11f)
            pieData.setValueTextColor(Color.BLACK)
            view.pie_chart.data = pieData
        }

        @ExperimentalStdlibApi
        fun showFrequencyChart() {
            val timeFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val confirmedData =
                timeFrequencyModel.cases.map { it.confirmedCases }.scanReduce { acc, i -> acc + i }
            val recoveredCases =
                timeFrequencyModel.cases.map { it.recovered }.scanReduce { acc, i -> acc + i }
            val deaths =
                timeFrequencyModel.cases.map { it.deaths }.scanReduce { acc, i -> acc + i }

            val dates = timeFrequencyModel.cases.map { timeFormat.parse(it.date)!!.time }

            val chart: LineChart = view.line_chart

            // disable description text
            chart.description.isEnabled = true
            chart.description = Description().apply {
                text = "Cases Trend"
            }

            // enable touch gestures
            chart.setTouchEnabled(true)

//            // set listeners
            chart.setDrawGridBackground(false)
//            chart.axisLeft.setDrawGridLines(false)
//            chart.xAxis.setDrawGridLines(false)

            val mv = MyMarkerView(view.context, R.layout.custom_marker_view)

            // Set the marker to the chart
            mv.chartView = chart
            chart.marker = mv

            // enable scaling and dragging
            chart.isDragEnabled = true
            chart.setScaleEnabled(true)
            chart.isScaleXEnabled = true
            chart.isScaleYEnabled = true

            // force pinch zoom along both axis
            chart.setPinchZoom(true)

            val xAxis: XAxis
            // // X-Axis Style // //
            xAxis = chart.xAxis
            xAxis.valueFormatter = object : IndexAxisValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val date = Date(value.toLong())
                    val sdf = SimpleDateFormat("MM/dd", Locale.ENGLISH)
                    return sdf.format(date)
                }
            }
            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f)
            val yAxis: YAxis
            // // Y-Axis Style // //
            yAxis = chart.axisLeft

//             disable dual axis (only use LEFT axis)
            chart.axisRight.isEnabled = false

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f)

            // axis range
            yAxis.axisMaximum = confirmedData.last().toFloat()
            yAxis.axisMinimum = 0f
            // // Create Limit Lines // //
            val llXAxis = LimitLine(9f, "Index 10")
            llXAxis.lineWidth = 4f
            llXAxis.enableDashedLine(10f, 10f, 0f)
            llXAxis.labelPosition = LimitLine.LimitLabelPosition.LEFT_BOTTOM
            llXAxis.textSize = 10f
            val ll1 = LimitLine(150f, "Upper Limit")
            ll1.lineWidth = 4f
//            ll1.enableDashedLine(10f, 10f, 0f)
            ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
            ll1.textSize = 10f
            val ll2 = LimitLine(-30f, "Lower Limit")
            ll2.lineWidth = 4f
//            ll2.enableDashedLine(10f, 10f, 0f)
            ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
            ll2.textSize = 10f

            val values = ArrayList<Entry>()

            confirmedData.forEachIndexed { index, i ->
                values.add(
                    Entry(
                        dates[index].toFloat(), i.toFloat()
                    )
                )
            }

            val set1: LineDataSet
            if (chart.data != null &&
                chart.data.dataSetCount > 0
            ) {
                set1 = chart.data.getDataSetByIndex(0) as LineDataSet
                set1.values = values
                set1.notifyDataSetChanged()
                chart.data.notifyDataChanged()
                chart.notifyDataSetChanged()
                set1.setMode(LineDataSet.Mode.CUBIC_BEZIER)
            } else {
                // create a dataset and give it a type
                set1 = LineDataSet(values, "Confirmed Cases")
                set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                set1.setDrawIcons(false)

                // draw dashed line
                set1.enableDashedLine(10f, 5f, 0f)

                // black lines and points
                set1.color = Color.BLACK
                set1.setCircleColor(Color.BLACK)

                // line thickness and point size
                set1.lineWidth = 1f
                set1.circleRadius = 3f

                // draw points as solid circles
                set1.setDrawCircleHole(false)

                // customize legend entry
                set1.formLineWidth = 1f
                set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
                set1.formSize = 15f

                // text size of values
                set1.valueTextSize = 9f

                // draw selection line as dashed
                set1.enableDashedHighlightLine(10f, 5f, 0f)

                // set the filled area
                set1.setDrawFilled(true)
                set1.fillFormatter =
                    IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }

                // drawables only supported on api level 18 and above
                val drawable =
                    ContextCompat.getDrawable(
                        view.context,
                        R.drawable.state_card_gradient
                    )
                set1.fillDrawable = drawable

                val dataSets = ArrayList<ILineDataSet>()
                dataSets.add(set1) // add the data sets

                // create a data object with the data sets
                val data = LineData(dataSets)

                // set data
                chart.data = data
            }
            // draw points over time
            chart.animateX(1500)

            // get the legend (only possible after setting data)
            val l = chart.legend

            // draw legend entries as lines
            l.form = Legend.LegendForm.LINE
        }

        private fun generateCenterSpannableText(): SpannableString? {
            val numberSize = distributionModel.confirmedCases.toString().count()
            val s = SpannableString("Total Confirmed Cases : \n ${distributionModel.confirmedCases}")
            s.setSpan(RelativeSizeSpan(1.0f), 0, 24, 0)
            s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
            s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
            s.setSpan(RelativeSizeSpan(1.65f), 14, s.length - 15, 0)
            s.setSpan(StyleSpan(Typeface.ITALIC), s.length - numberSize, s.length, 0)
            s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - numberSize, s.length, 0)
            return s

        }

        @SuppressLint("ViewConstructor")
        inner class MyMarkerView(context: Context?, layoutResource: Int) :
            MarkerView(context, layoutResource) {
            private val tvContent: TextView = findViewById(R.id.tvContent)

            // runs every time the MarkerView is redrawn, can be used to update the
            // content (user-interface)
            override fun refreshContent(
                e: Entry,
                highlight: Highlight
            ) {
                if (e is CandleEntry) {
                    tvContent.text = Utils.formatNumber(
                        e.high,
                        0,
                        true
                    )
                } else {
                    tvContent.text = Utils.formatNumber(
                        e.y,
                        0,
                        true
                    )
                }
                super.refreshContent(e, highlight)
            }

            override fun getOffset(): MPPointF {
                return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
            }

        }
        
        
    }
}