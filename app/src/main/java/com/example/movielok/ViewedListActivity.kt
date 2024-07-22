package com.example.movielok

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movielok.databinding.ActivityViewedListBinding
import com.example.movielok.models.Movie
import com.example.movielok.utils.MovieManager

class ViewedListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewedListBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var movieManager: MovieManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewedListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieManager = MovieManager(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        moviesAdapter = MoviesAdapter(movieManager.getViewedList()) { movie ->
            navigateToDetails(movie)
        }
        binding.recyclerView.adapter = moviesAdapter
    }

    override fun onResume() {
        super.onResume()
        // Refresh the viewed list when returning to this activity
        moviesAdapter.updateMovies(movieManager.getViewedList())
    }

    private fun navigateToDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }
}
