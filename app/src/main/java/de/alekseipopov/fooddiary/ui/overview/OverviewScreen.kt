package de.alekseipopov.fooddiary.ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme
import de.alekseipopov.fooddiary.util.testRecord
import de.alekseipopov.fooddiary.util.testRecordList
import de.alekseipopov.fooddiary.util.unixTimeToDate
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun OverviewScreen(
    navigateToDetails: (String?) -> Unit,
    navigateToReport: (Long?, Long?) -> Unit
) {

    val viewModel: OverviewViewModel = koinViewModel()
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(key1 = uiState) {
        viewModel.getRecords()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "Overview") }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            Column(
                modifier = Modifier.width(120.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                ExtendedFloatingActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    icon = { Icon(Icons.Outlined.DateRange, "") },
                    text = { Text("Report") },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    onClick = {  viewModel.showDatePickerDialog() },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                )
                ExtendedFloatingActionButton(
                    modifier = Modifier.fillMaxWidth(),
                    icon = { Icon(Icons.Filled.Add, "") },
                    text = { Text("Add") },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = { /*do something*/ },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                )
            }
        },
        content = { paddingValues ->
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding())
                )
            } else {
                OverviewScreenContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding()) ,
                    recordsList = uiState.recordList,
                    onDayRecordSelected = { id -> navigateToDetails(id) }
                )
            }
        }
    )

    if (uiState.showDatePickerDialog) {
        Dialog(
            onDismissRequest = { viewModel.hideDatePickerDialog() }
        ) {
            ReportDatePickerDialogContent(
                onConfirm = { startDate, endDate -> navigateToReport(startDate, endDate) },
                onDismiss = { viewModel.hideDatePickerDialog() }
            )
        }
    }
}

@Composable
fun OverviewScreenContent(
    modifier : Modifier,
    recordsList: List<DayRecord>?,
    onDayRecordSelected: (String?) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        recordsList?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = recordsList,
                    itemContent = {
                        DayRecordListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onDayRecordSelected(it.id) },
                            dayRecord = it,
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun DayRecordListItem(
    modifier: Modifier,
    dayRecord: DayRecord,
) {
    Card(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = dayRecord.date.unixTimeToDate(),
                fontSize = 24.sp
            )
        }
    }
}

@Composable
@Preview
fun DayRecordListItemPreview() {
    FoodDiaryTheme {
        DayRecordListItem(
            modifier = Modifier.fillMaxSize(),
            dayRecord = testRecord
        )
    }
}

@ExperimentalMaterial3Api
@Composable
@Preview
fun OverviewScreenContentPreview() {
    FoodDiaryTheme {
        Surface {
            OverviewScreenContent(
                modifier = Modifier.fillMaxWidth(),
                recordsList = testRecordList,
                onDayRecordSelected = { }
            )
        }
    }
}