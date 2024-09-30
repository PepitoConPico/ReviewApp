package com.example.reviewapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reviewapp.viewmodel.ReviewViewModel
import com.example.reviewapp.data.Review

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReviewScreen(
    reviewId: Int,
    viewModel: ReviewViewModel,
    navController: NavController
) {
    val reviews by viewModel.reviews.collectAsState()
    val review = reviews.find { it.id == reviewId }

    var productName by remember { mutableStateOf(review?.productName ?: "") }
    var rating by remember { mutableStateOf(review?.rating?.toString() ?: "") }
    var comment by remember { mutableStateOf(review?.comment ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Review") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = rating,
                onValueChange = { rating = it },
                label = { Text("Rating") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comment") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    review?.let {
                        val updatedReview = it.copy(
                            productName = productName,
                            rating = rating.toIntOrNull() ?: 0,
                            comment = comment
                        )
                        viewModel.updateReview(updatedReview)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Review")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    review?.let {
                        viewModel.deleteReview(it)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Delete Review")
            }
        }
    }
}