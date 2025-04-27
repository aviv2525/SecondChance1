package com.example.secondchance


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
                // נניח שלמוצר אין ID ייחודי, אז נשווה לפי השם
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                // בדיקה אם כל התוכן זהה
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = product.price

            // שימוש ב-Glide לטעינת תמונה
            Glide.with(binding.root.context)
                .load(product.imageRes) // יכול להיות URL או resId תלוי אצלך
                .override(200, 200) // קבע גודל אם צריך
                .fitCenter()
                .into(binding.ivProductImage)

            // לחיצה רגילה
            binding.root.setOnClickListener {
                onItemClick(product)
            }

            // לחיצה ארוכה
            binding.root.setOnLongClickListener {
                onItemLongClick(product)
                true
            }
        }
    }
}













/*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.secondchance.databinding.ItemProductBinding

class ProductAdapter(
    //private var productList: List<Product>,
    private val onItemClick: (Product) -> Unit,
    private val longClickListener: (Product) -> Unit // הוספת Listener למחיקת מוצר בלחיצה ארוכה
//) : ListAdapter<Product, ProductAdapter.ProductViewHolder>() {

) : ListAdapter<ProductAdapter.ProductViewHolder>() {


    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvProductPrice.text = product.price

            Glide.with(binding.root.context)
                .load(product.imageRes)
                .override(200, 200) // שינוי הגודל הפיזי
                .fitCenter()       // חיתוך ביחס פרופורציונלי
                .into(binding.ivProductImage)

            binding.root.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount() = productList.size

    fun submitList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)

        // הוספת לחיצה ארוכה למחיקת מוצר
        holder.itemView.setOnLongClickListener {
            longClickListener(product)  // קריאה לפונקציה שמקבלת את המוצר למחיקה
            true // מחזיר true כדי לסיים את הלחיצה הארוכה
        }
    }
}*/
