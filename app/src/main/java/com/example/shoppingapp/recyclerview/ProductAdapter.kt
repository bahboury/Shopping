package com.example.shoppingapp.recyclerview

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.model.ApiModel

// Adapter for the RecyclerView to display a list of products
class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // Mutable list of product data
    private val mutableData: MutableList<ApiModel> = mutableListOf()

    // Read-only list to expose data to external code
    val data: List<ApiModel> get() = mutableData

    // Optional item click listener for the products
    var onItemClick: ((ApiModel) -> Unit)? = null

    // Method to set or update the data in the adapter with a diff callback to optimize the update process
    fun setData(newData: List<ApiModel>) {
        Log.d("ProductAdapter", "Setting new data: $newData") // Add log to see the data being passed

        // Existing DiffUtil code
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = mutableData.size

            override fun getNewListSize() = newData.size

            override fun areItemsTheSame(oldItemPos: Int, newItemPos: Int): Boolean {
                return mutableData[oldItemPos].id == newData[newItemPos].id
            }

            override fun areContentsTheSame(oldItemPos: Int, newItemPos: Int): Boolean {
                return mutableData[oldItemPos] == newData[newItemPos]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        mutableData.clear()
        mutableData.addAll(newData)

        diffResult.dispatchUpdatesTo(this)
    }


    // Create a new ViewHolder when needed (inflating the item layout)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    // Bind the data to the ViewHolder and set up a click listener for the item
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = mutableData[position]
        holder.bind(item)  // Bind the data to the views
        holder.itemView.setOnClickListener { onItemClick?.invoke(item) }  // Handle item click
    }

    // Return the size of the data list
    override fun getItemCount(): Int = mutableData.size

    // ViewHolder class to hold references to the views in each item
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Views for displaying product data
        private val imageView: ImageView = itemView.findViewById(R.id.product_image)
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val priceView: TextView = itemView.findViewById(R.id.price)
        private val descView: TextView = itemView.findViewById(R.id.description)
        private val categoryView: TextView = itemView.findViewById(R.id.category_text)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.rating_stars)
        private val ratingText: TextView = itemView.findViewById(R.id.rating_text)

        @SuppressLint("SetTextI18n")
        // Bind product data to the views
        fun bind(item: ApiModel) {
            // Load the product image using Glide (with a placeholder and error image)
            Glide.with(itemView.context)
                .load(item.image)
                .placeholder(R.drawable.placeholder) // optional placeholder
                .error(R.drawable.error_image)       // optional error image
                .into(imageView)

            // Set text for the other product details
            titleView.text = item.title
            priceView.text = "$${item.price}"
            descView.text = item.description
            categoryView.text = item.category
            ratingBar.rating = item.rating.rating.toFloat()  // Set the rating on the RatingBar
            ratingText.text = "${item.rating.rating} (${item.rating.reviewCount})"  // Set rating text (e.g., "4.8 (210)")
        }
    }
}
