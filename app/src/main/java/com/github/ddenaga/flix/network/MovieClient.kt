package com.github.ddenaga.flix.network

import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.github.ddenaga.flix.BuildConfig

class MovieClient {
    private val client: AsyncHttpClient = AsyncHttpClient()

    companion object {
        // URLs and API key to retrieve JSON data from the Movie DB.
        private const val API_KEY = BuildConfig.API_KEY
        private const val API_BASE_URL = "https://api.themoviedb.org/3"
        private const val NOW_PLAYING_URL = "$API_BASE_URL/movie/now_playing?api_key=$API_KEY"
    }

    fun getNowPlaying(handler: JsonHttpResponseHandler) {
        client.get(NOW_PLAYING_URL, handler)
    }
}