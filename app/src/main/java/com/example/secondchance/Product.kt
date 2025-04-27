package com.example.secondchance

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ProductsTable")
data class Product(
    @ColumnInfo("Name")
    val name: String,
    @ColumnInfo("Price")
    val price: String,
//    @ColumnInfo("Description")
//    val description: String ,
    @ColumnInfo("Image")
    val imageRes: Int

) :Parcelable{
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}




/*@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao






    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(application: Application): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    application.applicationContext,
                    AppDatabase::class.java, "product_database"
                ).build()
            }
            return INSTANCE!!
        }
    }*/
