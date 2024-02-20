package de.alekseipopov.fooddiary.ui.overview

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme
import de.alekseipopov.fooddiary.util.testRecord
import de.alekseipopov.fooddiary.util.testRecordList
import de.alekseipopov.fooddiary.util.unixTimeToDate
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@ExperimentalMaterial3Api
@Composable
fun OverviewScreen(navController: NavHostController) {

    val viewModel: OverviewViewModel = koinViewModel()
    val recordsList = viewModel.recordsList.collectAsState()

    var showReportDatePickerDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = recordsList) {
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
                modifier = Modifier
                    .width(120.dp)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    icon = { Icon(Icons.Outlined.DateRange, "") },
                    text = { Text("Report") },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    onClick = { showReportDatePickerDialog = true },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                )
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    icon = { Icon(Icons.Filled.Add, "") },
                    text = { Text("Add") },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = { /*do something*/ },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                )
            }
        },
        content = { paddingValues ->
            OverviewScreenContent(
                paddingValues, recordsList.value, navController
            )
        }
    )

    if (showReportDatePickerDialog) {
        Dialog(
            onDismissRequest = { }
        ) {
            ReportDatePickerDialogContent(
                navController = navController,
                onDismiss = { showReportDatePickerDialog = false }
            )
        }
    }
}

@Composable
fun OverviewScreenContent(
    paddingValues: PaddingValues? = null,
    recordsList: List<DayRecord>?,
    navController: NavHostController
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 8.dp)
            .offset(y = 8.dp)
    ) {

        val (column) = createRefs()

        recordsList?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = paddingValues?.calculateTopPadding() ?: 0.dp)
                    .constrainAs(column) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = recordsList,
                    itemContent = {
                        DayRecordListItem(
                            dayRecord = it,
                            navigate = { id -> navController.navigate("details/$id") }
                        )
                    })
            }
        }
    }
}

@Composable
fun DayRecordListItem(
    dayRecord: DayRecord,
    navigate: (String?) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { navigate(dayRecord.id) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
        ) {
            Text(
                text = dayRecord.date.unixTimeToDate(),
                fontSize = 24.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDatePickerDialogContent(
    navController: NavHostController,
    onDismiss: () -> Unit,
) {
    var startDatePickerState = rememberDatePickerState()
    var showStartDatePickerDialog by remember { mutableStateOf(false) }
    var endDatePickerState = rememberDatePickerState()
    var showEndDatePickerDialog by remember { mutableStateOf(false) }

    val startDate = startDatePickerState.selectedDateMillis
    val endDate = endDatePickerState.selectedDateMillis

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .width(330.dp)
            .wrapContentWidth()

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "From")
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showStartDatePickerDialog =
                        !showStartDatePickerDialog && !showEndDatePickerDialog
                }
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    text = startDatePickerState.selectedDateMillis?.div(1000)?.unixTimeToDate()
                        ?: "Select start date"
                )
            }
            Text(text = "To")
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showEndDatePickerDialog = !showEndDatePickerDialog && !showStartDatePickerDialog
                }
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    text = endDatePickerState.selectedDateMillis?.div(1000)?.unixTimeToDate()
                        ?: "Select end date"
                )
            }

            if (showStartDatePickerDialog) {
                DatePicker(
                    state = startDatePickerState,
                    dateValidator = { date ->
                        if (endDatePickerState.selectedDateMillis != null)
                            date < endDatePickerState.selectedDateMillis!!
                        else {
                            val today = Date().time
                            date < today
                        }
                        date < (endDatePickerState.selectedDateMillis ?: Date().time)
                    }
                )
                Button(onClick = { showStartDatePickerDialog = false }) {
                    Text(text = "OK")
                }
            }
            if (showEndDatePickerDialog) {
                DatePicker(
                    state = endDatePickerState,
                    dateValidator = { date ->
                        date > (startDatePickerState.selectedDateMillis
                            ?: 0L) && date <= Date().time
                    }
                )
                Button(onClick = { showEndDatePickerDialog = false }) {
                    Text(text = "OK")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        text = "Dismiss"
                    )
                }

                TextButton(
                    onClick = {
                        if (startDate != null && endDate != null) {
                            navController.navigate(
                                "report/${startDate.div(1000)}/${endDate.div(1000)}"
                            )
                        } else {
                            val toast = Toast(context)
                            toast.setText("Select Dates")
                            toast.show()
                        }
                    }
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        text = "Confirm"
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ReportDatePickerDialogContentPreview() {
    FoodDiaryTheme {
        Surface {
            ReportDatePickerDialogContent(
                navController = rememberNavController(),
                onDismiss = { }
            )
        }
    }
}

@Composable
@Preview
fun DayRecordListItemPreview() {
    FoodDiaryTheme {
        DayRecordListItem(
            testRecord,
            navigate = { }
        )
    }
}

@ExperimentalMaterial3Api
@Composable
@Preview
fun OverviewScreenPreview() {
    FoodDiaryTheme {
        Surface {
            OverviewScreenContent(
                recordsList = testRecordList,
                navController = rememberNavController()
            )
        }
    }
}