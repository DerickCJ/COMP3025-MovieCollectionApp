package com.comp3025.moviecollection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.comp3025.moviecollection.databinding.ActivityLoginBinding

class Login : AppCompatActivity()
{
    // Using View Binding to access views in the layout
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}