package com.example.reviewapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reviewapp.viewmodel.ReviewViewModel
import com.example.reviewapp.data.Review
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreen(
    reviewId: Int,
    viewModel: ReviewViewModel,
    navController: NavController
) {
    val reviews by viewModel.reviews.collectAsState()
    val review = reviews.find { it.id == reviewId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Review Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("editReview/$reviewId") }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
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
            review?.let {
                Text(text = it.productName, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Rating: ${it.rating}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it.comment, style = MaterialTheme.typography.bodyMedium)
            } ?: Text("Review not found")
        }
    }
}