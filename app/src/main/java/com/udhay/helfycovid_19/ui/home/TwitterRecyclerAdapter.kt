package com.udhay.helfycovid_19.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udhay.helfycovid_19.R
import kotlinx.android.synthetic.main.twitter_view_holder_layout.view.*
import twitter4j.Status
import java.text.SimpleDateFormat
import java.util.*

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
        private val dateFormat = SimpleDateFormat("HH:mm dd-MM", Locale.ENGLISH)
        fun bindTweet(tweet: Status) {
            view.profile_id.text = tweet.user.name
            view.tweet_time_text_view.text = dateFormat.format(tweet.createdAt)
            view.tweet_body_text_view.text = tweet.text
            Log.v("Image", tweet.user.originalProfileImageURL)
            Picasso.get()
                .load(tweet.user.originalProfileImageURL.trim())
                .fit()
                .centerCrop()
                .into(view.profile_icon)
        }
    }
}