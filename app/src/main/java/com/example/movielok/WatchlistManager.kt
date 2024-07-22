// WatchlistManager.kt
package com.example.movielok

import android.content.Context
import android.content.SharedPreferences
import com.example.movielok.models.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object WatchlistManager {

    private const val PREFS_NAME = "movielok_prefs"
    private const val WATCHLIST_KEY = "watchlist"
    private const val VIEWEDLIST_KEY = "viewedlist"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun addToWatchlist(context: Context, movie: Movie) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        val watchlist = getWatchlist(context).toMutableList()
        watchlist.add(movie)
        editor.putString(WATCHLIST_KEY, Gson().toJson(watchlist))
        editor.apply()
    }

    fun removeFromWatchlist(context: Context, movie: Movie) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        val watchlist = getWatchlist(context).toMutableList()
        watchlist.remove(movie)
        editor.putString(WATCHLIST_KEY, Gson().toJson(watchlist))
        editor.apply()
    }

    fun getWatchlist(context: Context): List<Movie> {
        val prefs = getPreferences(context)
        val watchlistJson = prefs.getString(WATCHLIST_KEY, "")
        return if (!watchlistJson.isNullOrEmpty()) {
            val type = object : TypeToken<List<Movie>>() {}.type
            Gson().fromJson(watchlistJson, type)
        } else {
            emptyList()
        }
    }

    fun isMovieInWatchlist(context: Context, movie: Movie): Boolean {
        return getWatchlist(context).contains(movie)
    }

    fun addToViewedList(context: Context, movie: Movie) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        val viewedList = getViewedList(context).toMutableList()
        viewedList.add(movie)
        editor.putString(VIEWEDLIST_KEY, Gson().toJson(viewedList))
        editor.apply()
    }

    fun getViewedList(context: Context): List<Movie> {
        val prefs = getPreferences(context)
        val viewedListJson = prefs.getString(VIEWEDLIST_KEY, "")
        return if (!viewedListJson.isNullOrEmpty()) {
            val type = object : TypeToken<List<Movie>>() {}.type
            Gson().fromJson(viewedListJson, type)
        } else {
            emptyList()
        }
    }
}
