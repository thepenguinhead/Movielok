package com.example.movielok

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movielok.databinding.ItemMovieBinding
import com.example.movielok.models.Movie

class MoviesAdapter(
    private var movies: List<Movie>,
    private val clickListener: (Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            binding.titleTextView.text = movie.title
            binding.overviewTextView.text = movie.overview
            binding.releaseDateTextView.text = movie.release_date

            Glide.with(binding.root.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(binding.posterImageView)

            binding.root.setOnClickListener {
                clickListener(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], clickListener)
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}
