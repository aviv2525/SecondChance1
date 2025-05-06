package com.example.secondchance.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.secondchance.R

import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ProductsTable")
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo("Name")
    val name: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("Price")
    val price: String,
    @ColumnInfo(name = "ImageResId")
    val imageRes: Int = R.drawable.second_chance_def,
    @ColumnInfo(name = "ImageUri")
    val imageUri: String? = null,
    val sellerId: String

) :Parcelable




