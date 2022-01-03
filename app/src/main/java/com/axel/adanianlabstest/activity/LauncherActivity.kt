package com.axel.adanianlabstest.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.axel.adanianlabstest.R
import com.axel.adanianlabstest.utills.intention

class LauncherActivity : AppCompatActivity() {
    private val TIME_OUT = 1500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        Handler(Looper.getMainLooper()).postDelayed({
            intention(PhotoView::class.java)
            finish()
        },TIME_OUT.toLong())
    }
}