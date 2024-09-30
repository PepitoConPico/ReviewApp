package com.example.reviewapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Review::class], version = 1, exportSchema = false)
abstract class ReviewDatabase : RoomDatabase() {
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var Instance: ReviewDatabase? = null

        fun getDatabase(context: Context): ReviewDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ReviewDatabase::class.java, "review_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}