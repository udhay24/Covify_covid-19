package com.udhay.helfycovid_19.ui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.udhay.helfycovid_19.R
import kotlinx.android.synthetic.main.graph_fragment.*

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
        graph_view_pager.setPageTransformer { page, position ->
            when {
                position < -1 -> {    // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.alpha = 0f

                }
                position <= 0 -> {    // [-1,0]
                    page.alpha = 1f
                    page.translationX = 0f
                    page.scaleX = 1f
                    page.scaleY = 1f

                }
                position <= 1 -> {    // (0,1]
                    page.translationX = -position*page.width
                    page.alpha = 1-Math.abs(position)
                    page.scaleX = 1-Math.abs(position)
                    page.scaleY = 1-Math.abs(position)

                }
                else -> {    // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.alpha = 0f

                }
            }
        }
    }
}
