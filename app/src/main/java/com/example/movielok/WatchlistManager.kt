// WatchlistManager.kt
package com.example.movielok

import android.content.Context
import android.content.SharedPreferences

object WatchlistManager {

    private const val PREFS_NAME = "watchlist_prefs"
    private const val WATCHLIST_KEY = "watchlist"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun addToWatchlist(context: Context, movieId: Int) {
        val prefs = getPreferences(context)
        val watchlist = getWatchlist(context).toMutableSet()
        watchlist.add(movieId.toString())
        prefs.edit().putStringSet(WATCHLIST_KEY, watchlist).apply()
    }

    fun removeFromWatchlist(context: Context, movieId: Int) {
        val prefs = getPreferences(context)
        val watchlist = getWatchlist(context).toMutableSet()
        watchlist.remove(movieId.toString())
        prefs.edit().putStringSet(WATCHLIST_KEY, watchlist).apply()
    }

    fun getWatchlist(context: Context): Set<String> {
        val prefs = getPreferences(context)
        return prefs.getStringSet(WATCHLIST_KEY, emptySet()) ?: emptySet()
    }

    fun isMovieInWatchlist(context: Context, movieId: Int): Boolean {
        return getWatchlist(context).contains(movieId.toString())
    }
}
