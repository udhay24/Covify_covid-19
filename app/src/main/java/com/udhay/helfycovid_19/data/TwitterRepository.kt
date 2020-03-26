package com.udhay.helfycovid_19.data

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import twitter4j.*
import java.util.stream.Collectors


class TwitterRepository(
    private val twitter: Twitter
) {
    @RequiresApi(Build.VERSION_CODES.N)
    @Throws(TwitterException::class)
    suspend fun searchtweets(): List<String> {
        return withContext(Dispatchers.Default) {
            val query = Query("source:twitter4j baeldung")
            val result = twitter.search(query)
            result.tweets.stream()
                .map { item: Status -> item.text }
                .collect(Collectors.toList())
        }
    }

    suspend fun getTweetsFromHandle(user: String): ResponseList<Status> {
        return withContext(Dispatchers.Default) {
          twitter.getUserTimeline(user)
        }
    }

    suspend fun getTweetsFromMixedHandles(handles: List<String>): Response<List<Status>> {
        val combinedTweets: MutableList<Status> = mutableListOf()
        handles.forEach {
            try {
               val result = getTweetsFromHandle(it)
                combinedTweets.addAll(result)
            } catch (te: TwitterException) {
                te.printStackTrace()
                println("Failed to get timeline: " + te.message)
                return Response.error(
                    400,
                    "{\"key\":[\"${te.message}\"]}"
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
            }
        }
        return Response.success(combinedTweets.sortedBy { it.createdAt })
    }
}