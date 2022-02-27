package com.github.ddenaga.flix.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.github.ddenaga.flix.R
import com.github.ddenaga.flix.adapters.MOVIE_EXTRA
import com.github.ddenaga.flix.models.Movie

private const val TAG = "DetailActivity"
class DetailActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvOverview: TextView
    private lateinit var rbVoteAverage: RatingBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvTitle = findViewById(R.id.tvTitle)
        tvOverview = findViewById(R.id.tvOverview)
        rbVoteAverage = findViewById(R.id.rbVoteAverage)

        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        Log.i(TAG, "Movie is $movie")

        tvTitle.text = movie.title
        tvOverview.text = movie.overview
        // TODO: Get the rating of the movie
    }
}