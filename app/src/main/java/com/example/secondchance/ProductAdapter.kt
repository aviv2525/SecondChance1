package com.example.secondchance

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondchance.databinding.ItemProductBinding

class ProductAdapter(
    private val onItemClick: (Product) -> Unit,
    private val onItemLongClick: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = product.price

            if (!product.imageUri.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(Uri.parse(product.imageUri))
                    .override(200, 200)
                    .centerCrop()
                    .into(binding.ivProductImage)
            } else {
                Glide.with(binding.root.context)
                    .load(product.imageRes)
                    .override(200, 200)
                    .centerCrop()
                    .into(binding.ivProductImage)
            }

            binding.root.setOnClickListener {
                onItemClick(product)
            }

            binding.root.setOnLongClickListener {
                onItemLongClick(product)
                true
            }
        }
    }
}
