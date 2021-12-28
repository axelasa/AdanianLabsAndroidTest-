package com.axel.adanianlabstest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.axel.adanianlabstest.R

class PhotoDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)

        val headingView = findViewById<TextView>(R.id.user)
        val descriptionView = findViewById<TextView>(R.id.details)
        val imageView = findViewById<ImageView>(R.id.view)

        val bundle:Bundle? = intent.extras
        val image = bundle!!.getInt("image")
        val heading = bundle.getString("title")
        val description = bundle.getString("description")

        imageView.setImageResource(image)
        headingView.text = heading
        descriptionView.text = description

    }
}