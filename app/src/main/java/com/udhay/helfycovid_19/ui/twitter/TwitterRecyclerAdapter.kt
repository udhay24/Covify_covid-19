package com.udhay.helfycovid_19.ui.twitter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udhay.helfycovid_19.R
import com.udhay.helfycovid_19.util.round
import kotlinx.android.synthetic.main.twitter_view_holder_layout.view.*
import twitter4j.Status
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


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
            view.profile_name.text = tweet.user.name
            view.verified_flag.visibility = if(tweet.user.isVerified) View.VISIBLE else View.INVISIBLE
            view.tweet_time_text_view.text = findCurrentTimeDelta(tweet.createdAt)
            view.tweet_body_text_view.text = tweet.text
            view.likes_count.text = getRounded(tweet.favoriteCount)
            view.retweets_count.text = getRounded(tweet.retweetCount)

            view.twitter_icon.setOnClickListener {
                val url = "https://twitter.com/" + tweet.user.screenName
                    .toString() + "/status/" + tweet.id
                openTwitter(url)
            }

            Picasso.get()
                .load(tweet.user.originalProfileImageURL.trim())
                .fit()
                .centerCrop()
                .into(view.profile_icon)
        }

        private fun openTwitter(url: String) {
            var intent: Intent? = null

                intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
            view.context.startActivity(intent)
        }

        private fun getRounded(count: Int): String {
            return if (count < 1000) {
                count.toString()
            } else {
                "${(count/1000).toDouble().round(2)} k"
            }
        }

        private fun findCurrentTimeDelta(time: Date): String {
            val currentTime = Date()
            val difference = currentTime.time - time.time

            val seconds = TimeUnit.MILLISECONDS.toSeconds(difference)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(difference)
            val hours = TimeUnit.MILLISECONDS.toHours(difference)
            val days = TimeUnit.MILLISECONDS.toDays(difference)

            return when {
                seconds in 0..60 -> "$seconds sec"
                minutes in 1..60 -> "$minutes min"
                hours in 1..24 -> "$hours hour"
                else -> "$days days"
            }
        }
    }
}