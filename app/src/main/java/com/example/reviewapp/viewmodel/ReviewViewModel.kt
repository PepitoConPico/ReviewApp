package com.example.reviewapp.viewmodel

import com.example.reviewapp.data.Review
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.reviewapp.data.ReviewDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(private val reviewDao: ReviewDao) : ViewModel() {
    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    init {
        viewModelScope.launch {
            reviewDao.getAllReviews().collect { reviews ->
                _reviews.value = reviews
            }
        }
    }

    fun addReview(review: Review) {
        viewModelScope.launch {
            reviewDao.insertReview(review)
        }
    }

    fun updateReview(review: Review) {
        viewModelScope.launch {
            reviewDao.updateReview(review)
        }
    }

    fun deleteReview(review: Review) {
        viewModelScope.launch {
            reviewDao.deleteReview(review)
        }
    }

    class Factory(private val reviewDao: ReviewDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ReviewViewModel(reviewDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}