package com.udhay.helfycovid_19.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udhay.helfycovid_19.R
import kotlinx.android.synthetic.main.twitter_view_holder_layout.view.*
import twitter4j.Status

class TwitterRecyclerAdapter(
    private val tweets: List<Status>
): RecyclerView.Adapter<TwitterRecyclerAdapter.TwitterViewModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwitterViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.twitter_view_holder_layout, parent, false)
        return TwitterViewModel(view)
    }

    override fun getItemCount(): Int = tweets.size

    override fun onBindViewHolder(holder: TwitterViewModel, position: Int) {
        holder.bindTweet(tweet = tweets[position])
    }

    inner class TwitterViewModel(private val view: View): RecyclerView.ViewHolder(view) {
        fun bindTweet(tweet: Status) {
            view.tweet_body.text = tweet.text
        }
    }
}