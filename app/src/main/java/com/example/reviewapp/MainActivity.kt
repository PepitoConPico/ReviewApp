package com.example.reviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reviewapp.data.ReviewDatabase
import com.example.reviewapp.ui.theme.AddReviewScreen
import com.example.reviewapp.ui.theme.EditReviewScreen
import com.example.reviewapp.ui.theme.MainScreen
import com.example.reviewapp.ui.theme.ReviewDetailScreen
import com.example.reviewapp.viewmodel.ReviewViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val database = ReviewDatabase.getDatabase(applicationContext)
            val reviewDao = database.reviewDao()
            val viewModel: ReviewViewModel = viewModel(factory = ReviewViewModel.Factory(reviewDao))

            ReviewApp(viewModel)
        }
    }
}

@Composable
fun ReviewApp(viewModel: ReviewViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(viewModel = viewModel, navController = navController)
        }
        composable("addReview") {
            AddReviewScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            "reviewDetail/{reviewId}",
            arguments = listOf(navArgument("reviewId") { type = NavType.IntType })
        ) { backStackEntry ->
            val reviewId = backStackEntry.arguments?.getInt("reviewId") ?: return@composable
            ReviewDetailScreen(reviewId = reviewId, viewModel = viewModel, navController = navController)
        }
        composable(
            "editReview/{reviewId}",
            arguments = listOf(navArgument("reviewId") { type = NavType.IntType })
        ) { backStackEntry ->
            val reviewId = backStackEntry.arguments?.getInt("reviewId") ?: return@composable
            EditReviewScreen(reviewId = reviewId, viewModel = viewModel, navController = navController)
        }
    }
}