package com.example.shoppingapp.retrofit

import com.example.shoppingapp.model.ApiModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<ApiModel>

    companion object {
        fun create(): ApiService {
//            val logging = HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            }
//
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build()

            return Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/") // Replace with your API
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}