package com.example.shoppingapp.repository

import android.util.Log
import com.example.shoppingapp.model.ApiModel
import com.example.shoppingapp.retrofit.ApiService
import java.io.IOException

class ProductRepository {
    private val apiService = ApiService.create()

    suspend fun getProducts(): Result<List<ApiModel>> {
        return try {
            val products = apiService.getProducts()  // Fetch products from API
            Log.d("ProductRepository", "Fetched products: $products")  // Log the response
            Result.success(products)
        } catch (e: IOException) {
            Log.e("ProductRepository", "Network error: ${e.message}")
            Result.failure(IOException("Network failure. Please check your internet connection.", e))
        } catch (e: Exception) {
            Log.e("ProductRepository", "Error: ${e.message}")
            Result.failure(Exception("An unknown error occurred.", e))
        }
    }

}
