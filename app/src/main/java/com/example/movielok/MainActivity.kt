// MainActivity.kt
package com.example.movielok

import android.content.Intent
import android.os.Bundle
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
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.moviesRecyclerView.layoutManager = LinearLayoutManager(this)
        fetchMovies()
    }

    private fun fetchMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiService.getPopularMovies("638d0ed1588f69da7c3bb522a3f804c2", 1)
            runOnUiThread {
                moviesAdapter = MoviesAdapter(response.results) { movie ->
                    navigateToDetails(movie)
                }
                binding.moviesRecyclerView.adapter = moviesAdapter
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
