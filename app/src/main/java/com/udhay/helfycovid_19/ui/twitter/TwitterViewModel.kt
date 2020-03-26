package com.udhay.helfycovid_19.ui.twitter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.udhay.helfycovid_19.data.TwitterRepository
import com.udhay.helfycovid_19.util.Constants
import com.udhay.helfycovid_19.util.Resource
import twitter4j.Status
import twitter4j.TwitterResponse
import java.lang.Exception

class TwitterViewModel(
    private val twitterRepository: TwitterRepository,
    private val remoteConfig: FirebaseRemoteConfig
) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.N)
    val latestTweets: LiveData<Resource<List<Status>, Exception>> = liveData {
        val twitterHandles = remoteConfig.getString(Constants.TWITTER_HANDLE).split(",")
        emit(Resource.LoadingResponse)
        val tweets = twitterRepository.getTweetsFromMixedHandles(twitterHandles)
        if (tweets.isSuccessful) {
            emit(Resource.SuccessResponse(tweets.body()!!))
        } else {
            emit(
                Resource.FailureResponse(
                    Exception(tweets.errorBody()?.toString())
                ))
        }
    }
}
