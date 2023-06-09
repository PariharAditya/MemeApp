package com.example.apiproject

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.apiproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentMemeUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadMeme()

        //Share Button Fxn
        binding.shareButton.setOnClickListener {
            sharingMeme()
        }

        //Next Button Fxn
        binding.nextButton.setOnClickListener {
            loadMeme()
        }
    }

    private fun loadMeme(){
        binding.progressBar.visibility = android.view.View.VISIBLE
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                currentMemeUrl = response.getString("url")
                Glide.with(this).load(currentMemeUrl).listener(object: RequestListener<Drawable>
                {
                    override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                    ): Boolean
                    {
                        binding.progressBar.visibility = android.view.View.GONE
                        return false
                    }

                    override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                    ): Boolean
                    {
                        binding.progressBar.visibility = android.view.View.GONE
                        return false
                    }
                }) .into(binding.imageView)
            },
            {
                Toast.makeText(this,"Turn On Internet\n" +
                                               "You were not wishing it but it's an Error",Toast.LENGTH_LONG).show()
            }
        )
// Add the request to the RequestQueue.
        // Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest)
    }
    private fun sharingMeme(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout this cool meme I got from Reddit App Developed By AdityaParihar $currentMemeUrl")
        val chooser = Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
}