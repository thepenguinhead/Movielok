package com.example.movielok.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.movielok.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MovieManagerPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addMovieToWatchlist(movie: Movie) {
        val watchlist = getWatchlist().toMutableList()
        if (!watchlist.contains(movie)) {
            watchlist.add(movie)
            saveList("watchlist", watchlist)
        }
    }

    fun markMovieAsViewed(movie: Movie) {
        val viewedList = getViewedList().toMutableList()
        val watchlist = getWatchlist().toMutableList()
        if (!viewedList.contains(movie)) {
            viewedList.add(movie)
            watchlist.remove(movie)
            saveList("viewed_list", viewedList)
            saveList("watchlist", watchlist)
        }
    }

    fun isMovieInWatchlist(movie: Movie): Boolean {
        return getWatchlist().contains(movie)
    }

    fun isMovieViewed(movie: Movie): Boolean {
        return getViewedList().contains(movie)
    }

    fun getWatchlist(): List<Movie> {
        return getList("watchlist")
    }

    fun getViewedList(): List<Movie> {
        return getList("viewed_list")
    }

    private fun getList(key: String): List<Movie> {
        val json = sharedPreferences.getString(key, "")
        val type = object : TypeToken<List<Movie>>() {}.type
        return if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            gson.fromJson(json, type)
        }
    }

    private fun saveList(key: String, list: List<Movie>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }
}
