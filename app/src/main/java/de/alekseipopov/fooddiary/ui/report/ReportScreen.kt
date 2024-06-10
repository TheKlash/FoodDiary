package de.alekseipopov.fooddiary.ui.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.alekseipopov.fooddiary.R
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.ui.details.DayDetailsItem
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme
import de.alekseipopov.fooddiary.util.testRecordList
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    onBackPressed: () -> Unit,
    startDate: Long,
    endDate: Long
) {
    val viewModel: ReportScreenViewModel = koinViewModel()
    val uiState = viewModel.reportRecords.collectAsState().value
    val title = stringResource(
        R.string.report_title,
        uiState.report?.startDateString ?: "",
        uiState.report?.endDateString ?: ""
    )

    viewModel.getReport(startDate, endDate)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Image(
                            imageVector = Icons.Filled.ArrowBack,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                            contentDescription = null
                        )
                    }
                },
                title = { Text(text = title) }
            )
        },
        content = { paddingValues ->
            uiState.report?.records?.let {
                ReportScreenContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = paddingValues.calculateTopPadding())
                        .padding(8.dp)
                        .nestedScroll(rememberNestedScrollInteropConnection()),
                    records = it
                )
            }
        }
    )
}

@Composable
fun ReportScreenContent(modifier: Modifier = Modifier, records: List<Day>) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(
            items = records,
            itemContent = {
                DayDetailsItem(day = it)
                Spacer(Modifier.height(24.dp))
            }
        )
    }
}

@Composable
@Preview
fun ReportScreenContentPreview() {
    FoodDiaryTheme {
        Surface {
            ReportScreenContent(
                modifier = Modifier.fillMaxSize(),
                records = testRecordList
            )
        }
    }
}