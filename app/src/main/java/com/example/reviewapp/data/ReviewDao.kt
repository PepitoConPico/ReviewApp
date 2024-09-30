package com.example.reviewapp.data
import androidx.room.Dao
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews")
    fun getAllReviews(): Flow<List<Review>>

    @Query("SELECT * FROM reviews WHERE id = :id")
    suspend fun getReviewById(id: Int): Review?

    @Insert
    suspend fun insertReview(review: Review)

    @Update
    suspend fun updateReview(review: Review)

    @Delete
    suspend fun deleteReview(review: Review)
}