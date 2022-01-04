package com.axel.adanianlabstest.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.axel.adanianlabstest.R
import com.axel.adanianlabstest.activity.connectivity.Connection
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_photo_details.*
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum

class PhotoDetails : AppCompatActivity() {
//    private var noInternetDialog: NoInternetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_details)
        title = "Photo Detail"

        if (!Connection.isOnline(this)) {
            //NoInternetDialog:Signal
            NoInternetDialogPendulum.Builder(this,lifecycle).apply {

                dialogProperties.apply {
                    connectionCallback = object : ConnectionCallback { // Optional
                        override fun hasActiveConnection(hasActiveConnection: Boolean) {
                            // ...
                        }
                    }

                    cancelable = false // Optional
                    noInternetConnectionTitle = "No Internet" // Optional
                    noInternetConnectionMessage =
                        "Check your Internet connection and try again." // Optional
                    showInternetOnButtons = true // Optional
                    pleaseTurnOnText = "Please turn on" // Optional
                    wifiOnButtonText = "Wifi" // Optional
                    mobileDataOnButtonText = "Mobile data" // Optional

                    onAirplaneModeTitle = "No Internet" // Optional
                    onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                    pleaseTurnOffText = "Please turn off" // Optional
                    airplaneModeOffButtonText = "Airplane mode" // Optional
                    showAirplaneModeOffButtons = true // Optional
                }
            }.build()
        }

        val headingView = findViewById<TextView>(R.id.user)
        val descriptionView = findViewById<TextView>(R.id.details)
        val imageView = findViewById<ImageView>(R.id.view)

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