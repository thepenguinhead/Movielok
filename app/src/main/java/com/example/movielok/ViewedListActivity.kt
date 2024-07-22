// ViewedListActivity.kt
package com.example.movielok

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movielok.databinding.ActivityViewedListBinding
import com.example.movielok.models.Movie

class ViewedListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewedListBinding
    private lateinit var viewedListAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewedListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewedListRecyclerView.layoutManager = LinearLayoutManager(this)
        viewedListAdapter = MoviesAdapter(WatchlistManager.getViewedList(this)) { movie -> navigateToDetails(movie) }
        binding.viewedListRecyclerView.adapter = viewedListAdapter
    }

    private fun navigateToDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }
}
