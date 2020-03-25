package com.udhay.helfycovid_19.ui.twitter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.udhay.helfycovid_19.data.TwitterRepository
import com.udhay.helfycovid_19.util.Resource
import twitter4j.Status
import twitter4j.TwitterResponse
import java.lang.Exception

class TwitterViewModel(
    private val twitterRepository: TwitterRepository
) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.N)
    val latestTweets: LiveData<Resource<List<Status>, Exception>> = liveData {
        emit(Resource.LoadingResponse)
        val tweets = twitterRepository.getTweetsFromMixedHandles()
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
