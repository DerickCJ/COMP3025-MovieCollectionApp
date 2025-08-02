package com.comp3025.moviecollection.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.comp3025.moviecollection.R
import com.comp3025.moviecollection.model.Movie
import com.comp3025.moviecollection.view.AddEdit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val movieList: MutableList<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    // Firebase instances
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

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
        
        // Edit button click listener
        holder.editButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddEdit::class.java)
            intent.putExtra("title", movie.title)
            intent.putExtra("year", movie.year)
            intent.putExtra("poster", movie.poster)
            intent.putExtra("imdbID", movie.imdbID)
            intent.putExtra("isEdit", true)
            holder.itemView.context.startActivity(intent)
        }
        
        // Delete button click listener
        holder.deleteButton.setOnClickListener {
            deleteMovie(movie, position)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
    
    private fun deleteMovie(movie: Movie, position: Int) {
        // Get current user
        val currentUser = auth.currentUser
        if (currentUser == null) {
            return
        }
        
        firestore.collection("users")
            .document(currentUser.uid)
            .collection("movies")
            .whereEqualTo("imdbID", movie.imdbID)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    return@addOnSuccessListener
                }
                
                val document = documents.documents[0]
                document.reference.delete()
                    .addOnSuccessListener {
                        // Remove from local list and refresh
                        movieList.removeAt(position)
                        notifyDataSetChanged()
                    }
            }
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