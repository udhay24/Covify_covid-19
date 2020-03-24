package com.udhay.helfycovid_19.detail_country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.udhay.helfycovid_19.R
import kotlinx.android.synthetic.main.graph_fragment.*
import kotlin.math.abs


class GraphFragment : Fragment() {

    private val dataArgs: GraphFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.graph_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        graph_view_pager.adapter = GraphRecyclerAdapter(dataArgs.distributionData, dataArgs.frequencyModel)
        val pageMargin = 10
        val pageOffset = 30

        graph_view_pager.setPageTransformer { page, position ->
            val myOffset: Float = position * -(2 * pageOffset + pageMargin)
            when {
                position < -1 -> {
                    page.translationX = -myOffset
                }
                position <= 1 -> {
                    val scaleFactor =
                        0.7f.coerceAtLeast(1 - abs(position - 0.14285715f))
                    page.translationX = myOffset
                    page.scaleY = scaleFactor
                    page.alpha = scaleFactor
                }
                else -> {
                    page.alpha = 0f
                    page.translationX = myOffset
                }
            }
        }
    }
}
