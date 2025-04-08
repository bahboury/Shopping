package com.example.shoppingapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.databinding.ActivityMainBinding
import com.example.shoppingapp.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {

    // Binding object to bind UI elements in XML
    private lateinit var binding: ActivityMainBinding

    // ViewModel instance using 'viewModels' delegate to retrieve the ViewModel
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setting up the binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // Set the adapter to RecyclerView
        binding.recyclerView.adapter = productViewModel.productAdapter

        // Observe products LiveData from ViewModel
        productViewModel.products.observe(this) { products ->
            // Pass the list of products to the adapter when data changes
            Log.d("MainActivity", "Loaded products: ${products.size}") // Log the number of products
            productViewModel.productAdapter.setData(products)
        }

        // Observe error state
        productViewModel.error.observe(this) { errorMessage ->
            // Show error message if any
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        // Trigger loading products
        productViewModel.loadProducts()
    }
}
