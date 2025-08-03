package com.comp3025.moviecollection.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.comp3025.moviecollection.databinding.ItemMovieBinding
import com.comp3025.moviecollection.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(

    private val onEditClick: (Movie) -> Unit,
    private val onDeleteClick: (Movie, Int) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList: List<Movie> = emptyList()

    // ViewHolder class to hold the view for each movie item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    // Binds the movie data to the ViewHolder
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position], position)
    }

    // Returns the total number of items in the movie list
    override fun getItemCount(): Int = movieList.size

    // Method to update the movie list and notify the adapter
    fun updateMovies(newMovieList: List<Movie>) {
        movieList = newMovieList
        notifyDataSetChanged()
    }

    // ViewHolder class to bind movie data to the view
    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, position: Int) {
            with(binding) {
                titleTextView.text = movie.title
                yearTextView.text = movie.year
                imdbIdTextView.text = "IMDB: ${movie.imdbID}"

                // Using Picasso to load the movie poster image
                Picasso.get()
                    .load(movie.poster)
                    .into(posterImageView)

                // Setting up click listeners for edit and delete buttons
                btnEdit.setOnClickListener { onEditClick(movie) }
                btnDelete.setOnClickListener { onDeleteClick(movie, position) }
            }
        }
    }
}