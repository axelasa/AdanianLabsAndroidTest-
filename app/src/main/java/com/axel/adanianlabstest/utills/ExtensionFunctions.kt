package com.axel.adanianlabstest.utills

import android.app.Activity
import android.content.Intent
import android.widget.Toast

//app toast messages
fun Activity.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//app intents
fun Activity.intention(navigateTo: Class<*>) {
    startActivity(Intent(this, navigateTo))
}