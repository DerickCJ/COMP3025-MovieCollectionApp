package com.comp3025.moviecollection.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.comp3025.moviecollection.databinding.ActivityRegisterBinding

class Register: AppCompatActivity()
{
    // Using View Binding to access views in the layout
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}