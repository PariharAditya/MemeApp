package com.example.apiproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.apiproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadMeme()

        //Share Button Fxn
        binding.shareButton.setOnClickListener {

        }

        binding.nextButton.setOnClickListener {

        }
    }

    private fun loadMeme(){
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val url = response.getString("url")
                Glide.with(this).load(url).into(binding.imageView)
            },
            { error ->
                Toast.makeText(this,"You were not wishing it but it's an Error",Toast.LENGTH_LONG).show()
            }
        )
// Add the request to the RequestQueue.
        // Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest)
    }
}