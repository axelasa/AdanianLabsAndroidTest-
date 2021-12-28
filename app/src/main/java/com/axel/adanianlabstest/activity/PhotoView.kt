package com.axel.adanianlabstest.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axel.adanianlabstest.R
import com.axel.adanianlabstest.adapters.ImageAdapter
import com.axel.adanianlabstest.api.PixabayApi
import com.axel.adanianlabstest.apiBody.APIClient
import com.axel.adanianlabstest.helpers.Loader
import com.axel.adanianlabstest.models.Dogs
import com.axel.adanianlabstest.models.Hit
import com.axel.adanianlabstest.payLoads.Tag
import com.axel.adanianlabstest.utills.intention
import com.axel.adanianlabstest.utills.toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream


class PhotoView : AppCompatActivity(),ImageAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var imageAdapter: RecyclerView.Adapter<*>
    private lateinit var apiC: PixabayApi
    var l: Loader = Loader
    private var tag: Tag = Tag()
    private lateinit var hit: Hit
    private lateinit var dog:Dogs
    private lateinit var bmp:Bitmap
    private lateinit var byteArray:ByteArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //api call initialization
        apiC = APIClient.client?.create(PixabayApi::class.java)!!

        val searchView = findViewById<SearchView>(R.id.searchView)




//        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                return false
//            }
//        })

        manager = LinearLayoutManager(this)
        getDogs()

        searching.setOnClickListener {
            searchPhoto(searchView.query.toString())
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
        toast("Clicked")

        //        val bmp = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher)
//        val stream = ByteArrayOutputStream()
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        val byteArray: ByteArray = stream.toByteArray()

        intention(PhotoDetails::class.java)
        bmp = BitmapFactory.decodeResource(resources,R.drawable.ic_launcher_background)
        val stream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
        byteArray = stream.toByteArray()
        intent.putExtra("image",dog.hits[position].webformatURL )
        intent.putExtra("title",dog.hits[position].user)
        intent.putExtra("description",dog.hits[position].tags)

    }

}