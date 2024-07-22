package com.example.movielok

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movielok.models.Movie
import com.example.movielok.network.MovieApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchPopularMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = MovieApi.retrofitService.getPopularMovies(apiKey)
                if (response.isSuccessful) {
                    _movies.value = response.body()?.results
                } else {
                    _error.value = response.message()
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun searchMovies(query: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = MovieApi.retrofitService.searchMovies(query, apiKey)
                if (response.isSuccessful) {
                    _searchResults.value = response.body()?.results
                } else {
                    _error.value = response.message()
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
