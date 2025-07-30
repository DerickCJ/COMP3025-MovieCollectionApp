package com.comp3025.moviecollection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.comp3025.moviecollection.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    // Using View Binding to access views in the layout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}