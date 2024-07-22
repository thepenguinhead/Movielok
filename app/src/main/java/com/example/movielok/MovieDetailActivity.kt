package com.example.movielok

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.movielok.databinding.ActivityMovieDetailBinding
import com.example.movielok.models.Movie
import com.example.movielok.utils.MovieManager

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieManager: MovieManager
    private lateinit var currentMovie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieManager = MovieManager(this)

        val movie = intent.getParcelableExtra<Movie>("movie")
        if (movie != null) {
            currentMovie = movie
            bindMovieDetails(movie)
        }

        binding.addToWatchlistButton.setOnClickListener {
            movieManager.addMovieToWatchlist(currentMovie)
            updateButtonStates()
        }

        binding.markAsViewedButton.setOnClickListener {
            movieManager.markMovieAsViewed(currentMovie)
            updateButtonStates()
        }

        // Initially, set button states based on movie's status
        updateButtonStates()
    }

    private fun bindMovieDetails(movie: Movie) {
        binding.titleTextView.text = movie.title
        binding.overviewTextView.text = movie.overview
        binding.releaseDateTextView.text = "Release Date: ${movie.release_date}"
        binding.voteAverageTextView.text = "Rating: ${movie.vote_average}"

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .into(binding.posterImageView)
    }

    private fun updateButtonStates() {
        val isInWatchlist = movieManager.isMovieInWatchlist(currentMovie)
        val isViewed = movieManager.isMovieViewed(currentMovie)

        // Hide both buttons if the movie is already in the viewed list
        if (isViewed) {
            binding.addToWatchlistButton.visibility = View.GONE
            binding.markAsViewedButton.visibility = View.GONE
        } else {
            binding.addToWatchlistButton.isEnabled = !isInWatchlist
            binding.markAsViewedButton.isEnabled = isInWatchlist
            binding.addToWatchlistButton.visibility = View.VISIBLE
            binding.markAsViewedButton.visibility = View.VISIBLE
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = currentFocus ?: View(this)
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }
}
