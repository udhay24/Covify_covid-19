package com.udhay.helfycovid_19.ui.twitter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager

import com.udhay.helfycovid_19.R
import com.udhay.helfycovid_19.util.Resource
import kotlinx.android.synthetic.main.twitter_fragment.*
import org.koin.android.ext.android.inject
import twitter4j.Status

class TwitterFragment : Fragment() {

    private val viewModel: TwitterViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.twitter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animation.playAnimation()
        animation.visibility = View.VISIBLE
        twitter_recycler_view.visibility = View.GONE
        viewModel.latestTweets.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.SuccessResponse ->  populateTwitterFeeds(it.body)
//                is Resource.FailureResponse -> Unit//TODO() show error message
                is Resource.LoadingResponse -> Unit //TODO() show loading message
            }
        })
    }

    private fun populateTwitterFeeds(feeds: List<Status>) {
        animation.pauseAnimation()
        animation.visibility = View.GONE
        twitter_recycler_view.visibility = View.VISIBLE
        twitter_recycler_view.adapter =
            TwitterRecyclerAdapter(feeds)
    }
}
