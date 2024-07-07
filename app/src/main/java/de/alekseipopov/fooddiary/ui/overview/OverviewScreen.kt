package de.alekseipopov.fooddiary.ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import de.alekseipopov.fooddiary.R
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.ui.base.UiState
import de.alekseipopov.fooddiary.ui.details.EditDayDialogContent
import de.alekseipopov.fooddiary.ui.overview.model.OverviewUiEvents
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme
import de.alekseipopov.fooddiary.util.testRecord
import de.alekseipopov.fooddiary.util.testRecordList

@ExperimentalMaterial3Api
@Composable
fun OverviewScreen(
    navigateToDetails: (Int) -> Unit,
    navigateToReport: (Long, Long) -> Unit,
    viewModel: OverviewViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiEvents by viewModel.uiEvents.collectAsState()

    Scaffold(
        topBar = { TopBar() },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            Fab(
                onReportClick = { viewModel.showReportDatePickerDialog() },
                onAddClick = { viewModel.showNewEntryDialog() }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                when (uiState) {
                    is UiState.Loading -> {
                        StateLoading()
                    }

                    is UiState.Result<*> -> {
                        StateResult(
                            dayRecords = (uiState as UiState.Result<List<Day>>).data,
                            onDayRecordSelected = { id ->
                                navigateToDetails(id)
                            }
                        )
                    }

                    is UiState.Error<*> -> {
                        StateError(
                            throwable = (uiState as UiState.Error<*>).throwable,
                            onRetryPressed = { viewModel.getRecords() }
                        )
                    }
                }
            }
        }
    )

    ObserveUiEvents(uiEvents, viewModel, navigateToDetails, navigateToReport)
}

@Composable
private fun ObserveUiEvents(
    events: OverviewUiEvents?,
    viewModel: OverviewViewModel,
    navigateToDetails: (Int) -> Unit,
    navigateToReport: (Long, Long) -> Unit
) {
    when (events) {
        is OverviewUiEvents.ShowReportDatePickerDialog -> {
            Dialog(
                onDismissRequest = { viewModel.hideReportDatePickerDialog() }
            ) {
                ReportDatePickerDialogContent(
                    onConfirm = { startDate, endDate -> navigateToReport(startDate, endDate) },
                    onDismiss = { viewModel.hideReportDatePickerDialog() }
                )
            }
        }

        is OverviewUiEvents.ShowNewEntryDialog -> {
            Dialog(
                onDismissRequest = { viewModel.hideNewEntryDialog() }
            ) {
                Surface {
                    EditDayDialogContent(
                        onConfirm = {
                            viewModel.createNewDay(it)
                        },
                        onDismiss = { viewModel.hideNewEntryDialog() }
                    )
                }
            }
        }

        is OverviewUiEvents.ShowNewDay -> {
            navigateToDetails(events.id)
            viewModel.hideNewEntryDialog()
        }

        else -> {}
    }

}

@Composable
private fun StateLoading() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
        )

    }
}

@Composable
private fun StateResult(
    dayRecords: List<Day>,
    onDayRecordSelected: (Int) -> Unit = { }
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DayRecordList(
            dayRecords,
            onDayRecordSelected
        )
    }
}

@Composable
private fun StateError(
    throwable: Throwable,
    onRetryPressed: () -> Unit = { }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(onClick = onRetryPressed) {
            Text(
                textAlign = TextAlign.Center,
                text = "Error! ${throwable.localizedMessage} Please try again!"
            )
        }
    }
}

/** Components region **/

@ExperimentalMaterial3Api
@Composable
private fun TopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(stringResource(R.string.overview_title)) }
    )

}

@Composable
private fun Fab(
    onReportClick: () -> Unit = { },
    onAddClick: () -> Unit = { }
) {
    Column(
        modifier = Modifier.width(120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        ExtendedFloatingActionButton(
            modifier = Modifier.fillMaxWidth(),
            icon = { Icon(Icons.Outlined.DateRange, "") },
            text = { Text(stringResource(R.string.overview_button_report)) },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { onReportClick() },
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        )
        ExtendedFloatingActionButton(
            modifier = Modifier.fillMaxWidth(),
            icon = { Icon(Icons.Filled.Add, "") },
            text = { Text(stringResource(R.string.overview_button_add)) },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            onClick = { onAddClick() },
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        )
    }
}

@Composable
private fun DayRecordList(
    recordsList: List<Day>,
    onDayRecordSelected: (Int) -> Unit = { }
) {
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
                    day = it,
                )
            }
        )
    }

}

@Composable
fun DayRecordListItem(
    modifier: Modifier,
    day: Day
) {
    Card(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = day.fullTime,
                fontSize = 24.sp
            )
        }
    }
}

/** Preview Region **/

@Composable
@Preview
private fun FabPreview() {
    FoodDiaryTheme {
        Fab()
    }
}

@Composable
@Preview
private fun DayRecordListItemPreview() {
    FoodDiaryTheme {
        DayRecordListItem(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            day = testRecord
        )
    }
}


@Composable
@Preview
private fun OverviewScreenPreview_Loading() {
    FoodDiaryTheme {
        Surface {
            StateLoading()
        }
    }
}

@Composable
@Preview
private fun OverviewScreenPreview_Result() {
    FoodDiaryTheme {
        Surface {
            StateResult(dayRecords = testRecordList)
        }
    }
}

@Composable
@Preview
private fun OverviewScreenPreview_Error() {
    FoodDiaryTheme {
        Surface {
            StateError(throwable = Throwable("Your mom is gay!"))
        }
    }
}