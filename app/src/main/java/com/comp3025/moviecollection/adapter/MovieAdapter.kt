package com.comp3025.moviecollection.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.comp3025.moviecollection.R
import com.comp3025.moviecollection.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val movieList: MutableList<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        
        holder.titleTextView.text = movie.title
        holder.yearTextView.text = movie.year
        holder.imdbIdTextView.text = "IMDB: ${movie.imdbID}"
        Picasso.get().load(movie.poster).into(holder.posterImageView)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val yearTextView: TextView = itemView.findViewById(R.id.yearTextView)
        val imdbIdTextView: TextView = itemView.findViewById(R.id.imdbIdTextView)
        val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        val editButton: Button = itemView.findViewById(R.id.btn_edit)
        val deleteButton: Button = itemView.findViewById(R.id.btn_delete)
    }
}