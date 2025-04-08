package com.example.shoppingapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.model.ApiModel
import com.example.shoppingapp.repository.ProductRepository
import com.example.shoppingapp.recyclerview.ProductAdapter
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    // Repository instance
    private val repository = ProductRepository()

    // LiveData to hold the list of products
    private val _products = MutableLiveData<List<ApiModel>>()
    val products: LiveData<List<ApiModel>> get() = _products

    // LiveData to track loading state
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    // LiveData to track error state
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    // ProductAdapter to bind data to RecyclerView
    val productAdapter = ProductAdapter()

    // Load the products asynchronously using coroutines
    fun loadProducts() {
        _loading.value = true  // Set loading state to true
        _error.value = null    // Clear any previous error messages

        viewModelScope.launch {
            val result = repository.getProducts()

            // Handling the result
            if (result.isSuccess) {
                val productsList = result.getOrNull() ?: emptyList()
                _products.value = productsList
                productAdapter.setData(productsList)
                Log.d("ProductViewModel", "Products Loaded: ${productsList.size} items")
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "An error occurred"
                Log.e("ProductViewModel", "Error loading products: ${result.exceptionOrNull()?.message}")
            }

            _loading.value = false  // Set loading state to false after operation
        }
    }
}
