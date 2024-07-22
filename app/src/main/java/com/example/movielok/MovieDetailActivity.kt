// MovieDetailActivity.kt
package com.example.movielok

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movielok.databinding.ActivityMovieDetailBinding
import com.example.movielok.models.Movie

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movie = intent.getParcelableExtra<Movie>("movie")!!
        displayMovieDetails(movie)

        binding.addToWatchlistButton.setOnClickListener {
            handleWatchlistAction()
        }

        binding.markAsViewedButton.setOnClickListener {
            handleViewedAction()
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

    private fun handleWatchlistAction() {
        if (WatchlistManager.isMovieInWatchlist(this, movie)) {
            WatchlistManager.removeFromWatchlist(this, movie)
            Toast.makeText(this, "Removed from Watchlist", Toast.LENGTH_SHORT).show()
            binding.addToWatchlistButton.text = "Add to Watchlist"
        } else {
            WatchlistManager.addToWatchlist(this, movie)
            Toast.makeText(this, "Added to Watchlist", Toast.LENGTH_SHORT).show()
            binding.addToWatchlistButton.text = "Remove from Watchlist"
        }
    }

    private fun handleViewedAction() {
        WatchlistManager.addToViewedList(this, movie)
        WatchlistManager.removeFromWatchlist(this, movie)
        Toast.makeText(this, "Marked as Viewed", Toast.LENGTH_SHORT).show()
    }
}
