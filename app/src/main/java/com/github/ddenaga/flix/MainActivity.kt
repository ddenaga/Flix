package com.github.ddenaga.flix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

// URL and API key to retrieve JSON data of movies "now playing" from the Movie DB.
private const val API_KEY = BuildConfig.API_KEY
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=$API_KEY"

// Logcat tag.
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Asynchronous HTTP client for making requests to the API.
        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e(TAG, "onFailure: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                try {
                    Log.i(TAG, "onSuccess: JSON data $json")
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    Log.i(TAG, "Movie list: $movies")
                }
                catch (e: JSONException) {
                    Log.e(TAG, "Encountered exception: $e")
                }
            }
        })
    }
}