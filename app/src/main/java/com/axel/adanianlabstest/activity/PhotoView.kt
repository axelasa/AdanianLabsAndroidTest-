package com.axel.adanianlabstest.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.axel.adanianlabstest.R
import com.axel.adanianlabstest.activity.connectivity.Connection
import com.axel.adanianlabstest.activity.payLoads.Tag
import com.axel.adanianlabstest.adapters.ImageAdapter
import com.axel.adanianlabstest.api.PixabayApi
import com.axel.adanianlabstest.apiBody.APIClient
import com.axel.adanianlabstest.helpers.Loader
import com.axel.adanianlabstest.models.Dogs
import com.axel.adanianlabstest.models.Hit
import kotlinx.android.synthetic.main.activity_main.*
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhotoView : AppCompatActivity(),ImageAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var imageAdapter: RecyclerView.Adapter<*>
    private lateinit var apiC: PixabayApi
    var l: Loader = Loader
    private var tag: Tag = Tag()
    private  var hit:List<Hit>? = null
    private  var dog:Dogs? =null
    private lateinit var bmp:Bitmap
    private lateinit var byteArray:ByteArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Photo View"

        if (!Connection.isOnline(this)) {
            // No Internet Dialog: Pendulum
            NoInternetDialogPendulum.Builder(
                this,
                lifecycle
            ).apply {
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

        //api call initialization
        apiC = APIClient.client?.create(PixabayApi::class.java)!!

        val searchView = findViewById<EditText>(R.id.searchView)


        manager = LinearLayoutManager(this)
        getDogs()

        searching.setOnClickListener {
            searchPhoto(searchView.text.toString())
        }


    }

    private fun getDogs() {

        l.showProgressDialog(this, "Please wait ........")

        try {
            val call: Call<Dogs> = apiC.getDogs()
            call.enqueue(object : Callback<Dogs> {
                override fun onResponse(call: Call<Dogs>, response: Response<Dogs>) {

                    l.dismissProgress()
                    if (response.isSuccessful) {
                        Log.d("##SUCCESS##", "${response.body()!!.hits}")
                        recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
                            imageAdapter = ImageAdapter(response.body()!!.hits,this@PhotoView)
                            hit=response.body()!!.hits
                            layoutManager = manager
                            adapter = imageAdapter
                        }
                    }else{
                        var errorBodyString = ""
                        try {
                            errorBodyString = response.errorBody()!!.string()

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Loader.toastError(this@PhotoView, errorBodyString)
                        l.dismissProgress()
                        return
                    }
                }

                override fun onFailure(call: Call<Dogs>, t: Throwable) {
                    Loader.toastError(this@PhotoView, t.message!!)
                    l.dismissProgress()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    private fun searchPhoto(tags:String) {
        l.showProgressDialog(this@PhotoView, "Please Wait1......,")
        try {
            val payLoadDetails = tag.tags
            val call: Call<Dogs> = payLoadDetails.let { apiC.getPhotos(tags,"photo") }
            Log.d("####payload", "$payLoadDetails")
            call.enqueue(object : Callback<Dogs> {
                override fun onResponse(call: Call<Dogs>, response: Response<Dogs>) {
                    l.dismissProgress()
                    if (response.isSuccessful) {
                        Log.e("####SUCCESS", "${response.body()!!.hits}")
                        recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
                            imageAdapter = ImageAdapter(response.body()!!.hits,this@PhotoView)
                            hit=response.body()!!.hits
                            layoutManager = manager
                            adapter = imageAdapter
                        }
                    } else {
                        var errorBodyString = ""
                        try {
                            errorBodyString = response.errorBody()!!.string()

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Loader.toastError(this@PhotoView, errorBodyString)
                        l.dismissProgress()
                        return
                    }

                }

                override fun onFailure(call: Call<Dogs>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onItemClick(position:Int){
        //toast("Clicked")
        //intention(PhotoDetails::class.java)
        val intent = Intent(this, PhotoDetails::class.java)
        intent.putExtra("image", hit?.get(position)?.webformatURL)
        Log.e("####PICTURE",hit.toString())
        intent.putExtra("title", hit?.get(position)?.user)
        intent.putExtra("description",hit?.get(position)?.tags)
        startActivity(intent)



    }

}