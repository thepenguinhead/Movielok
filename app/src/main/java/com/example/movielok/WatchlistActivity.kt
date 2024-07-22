// WatchlistActivity.kt
package com.example.movielok

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movielok.databinding.ActivityWatchlistBinding
import com.example.movielok.models.Movie

class WatchlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWatchlistBinding
    private lateinit var watchlistAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.watchlistRecyclerView.layoutManager = LinearLayoutManager(this)
        watchlistAdapter = MoviesAdapter(WatchlistManager.getWatchlist(this)) { movie -> navigateToDetails(movie) }
        binding.watchlistRecyclerView.adapter = watchlistAdapter
    }

    private fun navigateToDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }
}
