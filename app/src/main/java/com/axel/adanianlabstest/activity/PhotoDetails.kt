package com.axel.adanianlabstest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.axel.adanianlabstest.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_photo_details.*

class PhotoDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)

        val headingView = findViewById<TextView>(R.id.user)
        val descriptionView = findViewById<TextView>(R.id.details)
        val imageView = findViewById<ImageView>(R.id.view)

        //Glide.with(view.context).load(hits.webformatURL).centerCrop().into(imageView)
        val bundle:Bundle? = intent.extras
        val image = bundle?.getString("image").toString()
        Log.e("####Image",image.toString())
        Glide.with(view.context).load(image).centerCrop().into(imageView)
        val heading = bundle?.getString("title")
        if (heading != null) {
            Log.e("####TITLE",heading)
        }
        val description = bundle?.getString("description")
        if (description != null) {
            Log.e("####DESCRIPTION",description)
        }

        headingView.text = heading
        descriptionView.text = description

    }
}