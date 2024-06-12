package de.alekseipopov.fooddiary.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import de.alekseipopov.fooddiary.ui.details.DetailsScreen
import de.alekseipopov.fooddiary.ui.navigation.Navigation
import de.alekseipopov.fooddiary.ui.navigation.Screen
import de.alekseipopov.fooddiary.ui.navigation.Screen.Report.endDate
import de.alekseipopov.fooddiary.ui.navigation.Screen.Report.startDate
import de.alekseipopov.fooddiary.ui.overview.OverviewScreen
import de.alekseipopov.fooddiary.ui.report.ReportScreen
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
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

