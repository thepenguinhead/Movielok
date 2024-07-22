// MovieDetailActivity.kt
package com.example.movielok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movielok.databinding.ActivityMovieDetailBinding
import com.example.movielok.models.Movie

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getParcelableExtra<Movie>("movie")
        movie?.let {
            displayMovieDetails(it)
        }
    }

    private fun displayMovieDetails(movie: Movie) {
        binding.titleTextView.text = movie.title
        binding.releaseDateTextView.text = "Release Date: ${movie.release_date}"
        binding.voteAverageTextView.text = "Rating: ${movie.vote_average}"
        binding.overviewTextView.text = movie.overview

        val posterUrl = "https://image.tmdb.org/t/p/w500" + movie.poster_path
        Glide.with(this)
            .load(posterUrl)
            .into(binding.posterImageView)
    }
}
