package de.alekseipopov.fooddiary.ui.report

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReportScreen(
    navController: NavController,
    startDate: Long,
    endDate: Long
) {
    val viewModel: ReportScreenViewModel = koinViewModel()
}