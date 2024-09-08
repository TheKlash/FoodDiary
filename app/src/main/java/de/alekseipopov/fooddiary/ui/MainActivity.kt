package de.alekseipopov.fooddiary.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.alekseipopov.fooddiary.ui.details.DetailsScreen
import de.alekseipopov.fooddiary.ui.navigation.DetailsScreenRoute
import de.alekseipopov.fooddiary.ui.navigation.MainCoordinator
import de.alekseipopov.fooddiary.ui.navigation.NavEvent
import de.alekseipopov.fooddiary.ui.navigation.OverviewScreenRoute
import de.alekseipopov.fooddiary.ui.navigation.ReportScreenRoute
import de.alekseipopov.fooddiary.ui.overview.OverviewScreen
import de.alekseipopov.fooddiary.ui.report.ReportScreen
import de.alekseipopov.fooddiary.core.ui.style.FoodDiaryTheme
import de.alekseipopov.fooddiary.ui.navigation.detailsScreen
import de.alekseipopov.fooddiary.ui.navigation.overviewScreen
import de.alekseipopov.fooddiary.ui.navigation.reportScreen
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val coordinator: MainCoordinator = koinInject()
            FoodDiaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = OverviewScreenRoute.route
                    ) {
                        overviewScreen()
                        detailsScreen()
                        reportScreen()
                    }
                }

                this.lifecycleScope.launch() {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        coordinator.events.collect { event ->
                            when (event) {
                                is NavEvent.ComposeScreen<*> -> {
                                    event.navigate(navController)
                                }

                                is NavEvent.Dialog -> {
                                    //TODO
                                }

                                is NavEvent.PopTo -> {
                                    //TODO
                                }

                                is NavEvent.Toast -> {
                                    //TODO
                                }

                                is NavEvent.Back -> {
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

