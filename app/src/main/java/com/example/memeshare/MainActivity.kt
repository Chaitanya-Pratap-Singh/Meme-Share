package com.example.memeshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    var currentImageUrl :String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    private fun loadMeme(){
        // Instantiate the RequestQueue.
        val url = "https://meme-api.herokuapp.com/gimme"
// Request a string response from the provided URL.
        val jsonObjectRequest =JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).into(findViewById (R.id.memeImageView))
            },
            Response.ErrorListener { Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show()
            }
        )

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    fun shareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey,check out this cool meme which I found"+ " " +currentImageUrl)
        val chooser = Intent.createChooser(intent,"Where would you like to share this Meme..")
        startActivity(chooser)
    }


    fun nextMeme(view: View) {
        loadMeme()
    }


}


