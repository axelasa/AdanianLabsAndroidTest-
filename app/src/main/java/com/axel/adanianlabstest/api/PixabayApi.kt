package com.axel.adanianlabstest.api

import com.axel.adanianlabstest.models.Dogs
import retrofit2.Call
import retrofit2.http.GET

interface PixabayApi {
    @get:GET("&q=dogs&image_type=photo")
    val dogs:Call<List<Dogs>>
}