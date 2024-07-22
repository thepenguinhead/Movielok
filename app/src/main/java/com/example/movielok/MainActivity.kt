// MainActivity.kt
package com.example.movielok

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movielok.databinding.ActivityMainBinding
import com.example.movielok.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val apiService by lazy { ApiClient.retrofit.create(MovieApiService::class.java) }
    private lateinit var searchAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchResultsRecyclerView.layoutManager = LinearLayoutManager(this)
        searchAdapter = MoviesAdapter(emptyList()) { movie -> navigateToDetails(movie) }
        binding.searchResultsRecyclerView.adapter = searchAdapter

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    searchMovies(query)
                } else {
                    binding.searchResultsRecyclerView.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.watchlistButton.setOnClickListener {
            val intent = Intent(this, WatchlistActivity::class.java)
            startActivity(intent)
        }

        binding.viewedListButton.setOnClickListener {
            val intent = Intent(this, ViewedListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun searchMovies(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.searchMovies("638d0ed1588f69da7c3bb522a3f804c2", query)
            runOnUiThread {
                if (response.results.isNotEmpty()) {
                    searchAdapter.updateMovies(response.results)
                    binding.searchResultsRecyclerView.visibility = View.VISIBLE
                } else {
                    binding.searchResultsRecyclerView.visibility = View.GONE
                }
            }
        }
    }

    private fun navigateToDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }
}
