package com.example.shoppingapp.model

data class ApiModel(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: ProductRating
)
