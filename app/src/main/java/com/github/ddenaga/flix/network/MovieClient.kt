package com.github.ddenaga.flix.network

import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.github.ddenaga.flix.BuildConfig

class MovieClient {
    private val client: AsyncHttpClient = AsyncHttpClient()

    companion object {
        // URLs and API key to retrieve JSON data from the Movie DB.
        private const val API_KEY = BuildConfig.TMDB_API_KEY
        private const val API_BASE_URL = "https://api.themoviedb.org/3"
        private const val NOW_PLAYING_URL = "$API_BASE_URL/movie/now_playing?api_key=$API_KEY"
        private const val TRAILER_URL = "$API_BASE_URL/movie/%d/videos?api_key=$API_KEY"
    }

    fun getNowPlaying(handler: JsonHttpResponseHandler) {
        client.get(NOW_PLAYING_URL, handler)
    }

    fun getVideos(movieId: Int, handler: JsonHttpResponseHandler) {
        client.get(TRAILER_URL.format(movieId), handler)
    }
}