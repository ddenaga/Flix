package com.github.ddenaga.flix.activities

import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.github.ddenaga.flix.BuildConfig
import com.github.ddenaga.flix.R
import com.github.ddenaga.flix.adapters.MOVIE_EXTRA
import com.github.ddenaga.flix.models.Movie
import com.github.ddenaga.flix.network.MovieClient
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers

private const val YT_API_KEY = BuildConfig.YT_API_KEY
private const val TAG = "DetailActivity"
class DetailActivity : YouTubeBaseActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvOverview: TextView
    private lateinit var tvPopularity: TextView
    private lateinit var rbVoteAverage: RatingBar
    private lateinit var pvPlayer: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        tvPopularity = findViewById(R.id.tvPopularity)
        rbVoteAverage = findViewById(R.id.rbVoteAverage)
        pvPlayer = findViewById(R.id.pvPlayer)

        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        Log.i(TAG, "Movie is $movie")

        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        tvPopularity.text = getString(R.string.popularity, movie.popularity)
        rbVoteAverage.rating = movie.voteAverage.toFloat()

        val movieClient = MovieClient()
        movieClient.getVideos(movie.movieId, object: JsonHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.i(TAG, "onFailure: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: $json")
                val results = json.jsonObject.getJSONArray("results")
                if (results.length() == 0) {
                    Log.w(TAG, "No movie trailers found")
                    return
                }
                val movieTrailerJson = results.getJSONObject(results.length() - 1)
                val youtubeKey = movieTrailerJson.getString("key")

                initializeYoutube(youtubeKey)
            }
        })
    }

    private fun initializeYoutube(youtubeKey: String) {
        pvPlayer.initialize(YT_API_KEY, object: YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, p2: Boolean) {
                Log.i(TAG, "onInitializationSuccess")
                player?.cueVideo(youtubeKey) // "a0YrCABCOEY"
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider?, result: YouTubeInitializationResult?) {
                Log.i(TAG, "onInitializationFailure: $result")
            }
        })
    }
}