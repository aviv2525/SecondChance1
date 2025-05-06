package com.example.secondchance.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Seller(
    val sellerId: String,
    val name: String,
    val phone: String,
    val address: String,
    val products: List<Product>

): Parcelable
