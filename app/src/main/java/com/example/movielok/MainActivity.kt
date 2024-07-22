// MainActivity.kt
package com.example.movielok

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movielok.databinding.ActivityMainBinding
import com.example.movielok.models.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        moviesAdapter = MoviesAdapter(emptyList()) { movie ->
            navigateToDetails(movie)
        }
        binding.recyclerView.adapter = moviesAdapter

        mainViewModel.movies.observe(this, Observer { movies ->
            movies?.let {
                moviesAdapter.updateMovies(it)
                Log.d("MainActivity", "Popular movies loaded: ${it.size}")
            }
        })

        mainViewModel.searchResults.observe(this, Observer { results ->
            results?.let {
                moviesAdapter.updateMovies(it)
                Log.d("MainActivity", "Search results loaded: ${it.size}")
            }
        })

        mainViewModel.error.observe(this, Observer { error ->
            error?.let {
                Log.e("MainActivity", "Error: $it")
            }
        })

        // Fetch popular movies when the activity is created
        mainViewModel.fetchPopularMovies(Constants.API_KEY)

        binding.watchlistButton.setOnClickListener {
            hideKeyboard()
            val intent = Intent(this, WatchlistActivity::class.java)
            startActivity(intent)
        }

        binding.viewedListButton.setOnClickListener {
            hideKeyboard()
            val intent = Intent(this, ViewedListActivity::class.java)
            startActivity(intent)
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                val query = binding.searchEditText.text.toString()
                if (query.isNotEmpty()) {
                    mainViewModel.searchMovies(query, Constants.API_KEY)
                    hideKeyboard()
                }
                true
            } else {
                false
            }
        }

        binding.searchButton.setOnClickListener {
            val query = binding.searchEditText.text.toString()
            if (query.isNotEmpty()) {
                mainViewModel.searchMovies(query, Constants.API_KEY)
                hideKeyboard()
            }
        }
    }

    private fun navigateToDetails(movie: Movie) {
        hideKeyboard()
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = currentFocus ?: View(this)
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}
