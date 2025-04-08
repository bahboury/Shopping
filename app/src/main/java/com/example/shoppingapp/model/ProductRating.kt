package com.example.shoppingapp.model

import com.google.gson.annotations.SerializedName

data class ProductRating(
    @SerializedName("rate") val rating: Double,
    @SerializedName("count") val reviewCount: Int
)