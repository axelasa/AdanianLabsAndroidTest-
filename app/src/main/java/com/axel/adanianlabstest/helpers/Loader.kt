package com.axel.adanianlabstest.helpers

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast

object Loader {
    lateinit var pd: ProgressDialog
    fun showProgressDialog(context: Context?,msg: String){
        pd = ProgressDialog(context)
        pd.setMessage(msg)
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pd.isIndeterminate = true
        pd.show()
    }

    fun dismissProgress(){
        pd.dismiss()
    }
    fun toastError(context: Context?, errormsg: String) {
        var errormsg = errormsg
        errormsg = "Error: Please try again. $errormsg"
        Toast.makeText(context, errormsg, Toast.LENGTH_LONG).show()
        println(errormsg)
    }
}