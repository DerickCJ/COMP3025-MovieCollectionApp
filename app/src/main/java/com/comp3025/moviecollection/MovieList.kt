package com.comp3025.moviecollection

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.comp3025.moviecollection.databinding.ActivityAddEditBinding
import com.comp3025.moviecollection.databinding.ActivityMovieListBinding

class MovieList : AppCompatActivity()
{
    // Using View Binding to access views in the layout
    private lateinit var binding: ActivityMovieListBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}