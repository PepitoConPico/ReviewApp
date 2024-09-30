package com.example.reviewapp.ui.theme

import com.example.reviewapp.data.Review
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reviewapp.viewmodel.ReviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: ReviewViewModel,
    navController: NavController
) {
    val reviews by viewModel.reviews.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reviews") },
                actions = {
                    IconButton(onClick = { navController.navigate("addReview") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Review")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(reviews) { review ->
                ReviewItem(
                    review = review,
                    onItemClick = { navController.navigate("reviewDetail/${review.id}") }
                )
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onItemClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = review.productName, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Rating: ${review.rating}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}