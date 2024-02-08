package de.alekseipopov.fooddiary.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.alekseipopov.fooddiary.ui.details.DetailsScreen
import de.alekseipopov.fooddiary.ui.overview.OverviewScreen
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            FoodDiaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(navController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "overview") {
        composable(route = "overview") {
            OverviewScreen(navController = navController)
        }
        composable(route = "details/{recordId}") {
            val recordId = it.arguments?.getString("recordId") ?: ""
            DetailsScreen(navController = navController, recordId = recordId)
        }
    }
}