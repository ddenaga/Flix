package com.github.ddenaga.flix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

// URL and API key to retrieve JSON data of movies "now playing" from the Movie DB.
private const val API_KEY = BuildConfig.API_KEY
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=$API_KEY"

private const val TAG = "MainActivity"  // Logcat tag.
private lateinit var rvMovies: RecyclerView

class MainActivity : AppCompatActivity() {

    private val movies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMovies = findViewById(R.id.rvMovies)

        // The movie adapter will observe the list of movies.
        val movieAdapter = MovieAdapter(this, movies)

        // Attach the movie adapter to the movie recycler view.
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)

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

                    // Add all the movies (JSONObject => Movie) to the list and update the recycler view via the adapter.
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list: $movies")
                }
                catch (e: JSONException) {
                    Log.e(TAG, "Encountered exception: $e")
                }
            }
        })
    }
}