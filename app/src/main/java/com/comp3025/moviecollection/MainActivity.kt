package com.comp3025.moviecollection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.comp3025.moviecollection.databinding.ActivityMainBinding
import com.comp3025.moviecollection.view.Login
import com.comp3025.moviecollection.view.Register

class MainActivity : AppCompatActivity()
{
    // Using View Binding to access views in the layout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //click button to open the LoginActivity
        binding.btnLogin.setOnClickListener {
           val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        //click button to open the RegisterActivity
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }



    }
}