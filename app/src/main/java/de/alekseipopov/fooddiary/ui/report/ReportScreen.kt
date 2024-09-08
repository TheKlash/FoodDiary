package de.alekseipopov.fooddiary.ui.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.alekseipopov.fooddiary.R
import de.alekseipopov.fooddiary.core.data.testDaysList
import de.alekseipopov.fooddiary.core.ui.data.UiState
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.ui.details.DayDetailsItem
import de.alekseipopov.fooddiary.core.ui.style.FoodDiaryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    viewModel: ReportScreenViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val uiState = uiState) {
        is UiState.Error -> { }
        is UiState.Loading -> { StateLoading() }
        is UiState.Result -> {
            StateResult(
                startDate = uiState.data.startDateString,
                endDate = uiState.data.endDateString,
                navigateBack = { viewModel.back() },
                days = uiState.data.days
            )
        }
    }
}

@Composable
private fun StateLoading() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun StateResult(
    startDate: String,
    endDate: String,
    navigateBack: () -> Unit = { },
    days: List<Day>
) {
    val title = stringResource(R.string.report_title, startDate, endDate)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(onNavigateIconPressed = navigateBack, title = title) },
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = 8.dp,
                    start = 8.dp,
                    end = 8.dp
                )
            ) {
                ReportScreenContent(days)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar (
    onNavigateIconPressed: () -> Unit = {},
    title: String
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = { onNavigateIconPressed() }) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentDescription = null
                )
            }
        },
        title = { Text(text = title) }
    )

}

@Composable
private fun ReportScreenContent(days: List<Day>) {
    LazyColumn(
        modifier = Modifier.nestedScroll(rememberNestedScrollInteropConnection()),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        items(
            items = days,
            itemContent = {
                Text(
                    fontSize = 24.sp,
                    text = it.fullTime
                )
                DayDetailsItem(day = it)
            }
        )
    }
}

@Composable
@Preview
private fun TopBarPreview() {
    val title = stringResource(R.string.report_title, 01.01, 12.12)

    FoodDiaryTheme {
        TopBar(title = title)
    }
}

@Composable
@Preview
private fun StateLoadingPreview() {
    FoodDiaryTheme {
        Surface {
            StateLoading()
        }
    }
}

@Composable
@Preview
private fun StateResultPreview() {
    FoodDiaryTheme {
        Surface {
            StateResult(
                startDate = "01.01",
                endDate = "31.12",
                days = testDaysList
            )
        }
    }
}