package com.axel.adanianlabstest.api

import com.axel.adanianlabstest.BuildConfig
import com.axel.adanianlabstest.constants.AppConstants
import com.axel.adanianlabstest.models.Dogs
import com.axel.adanianlabstest.models.Hit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("?key=${BuildConfig.PIXABAY_ACCESS_KEY}&q=dogs&image_type=photo")
    fun getDogs():Call<Dogs>

    @GET("${AppConstants.BASE_URL}?key=${BuildConfig.PIXABAY_ACCESS_KEY}")
    fun getPhotos(@Query("q")tags:String,@Query("image_type")type:String):Call<Dogs>

    @GET("?key=${BuildConfig.PIXABAY_ACCESS_KEY}&q=dogs&image_type=photo")
    fun getImages():Call<Hit>
}