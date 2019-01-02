package com.omeram.kotlin.photoapp.api

import com.omeram.kotlin.photoapp.models.PhotoList
import retrofit2.Call
import retrofit2.http.GET

interface PhotoApi {

    @GET("?key=11152478-200b12f41865e233d9c426131&q=donald%20trump&image_type=photo")
    fun getPhotos() : Call<PhotoList>

}