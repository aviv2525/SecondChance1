package com.example.secondchance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Product::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun ProductsDau(): ProductDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // 🛠️ מוסיפים את מיגרציה מגרסה 1 לגרסה 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // מוסיפים עמודה חדשה לטבלת המוצרים
                database.execSQL("ALTER TABLE product ADD COLUMN description TEXT DEFAULT ''")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "product_database"
                )
                    .addMigrations(MIGRATION_1_2) // ✅ מחבר את המיגרציה
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
