package de.alekseipopov.fooddiary.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.ui.details.model.DetailsUiEvents
import de.alekseipopov.fooddiary.util.testRecord
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    onBackPressed: () -> Unit,
    recordId: Int
) {
    val viewModel: DetailsViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiEvents by viewModel.uiEvents.collectAsState(null)
    viewModel.getDay(recordId)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { onBackPressed() }
                    ) {
                        Image(
                            imageVector = Icons.Filled.ArrowBack,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(text = uiState.record?.fullTime ?: "")
                },
                actions = {
                    IconButton(onClick = { viewModel.showEditEntryDialog() }) {
                        Image(
                            imageVector = Icons.Filled.Edit,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            if (uiState.isLoading) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = paddingValues.calculateTopPadding())
                    .padding(8.dp)) {
                    CircularProgressIndicator()
                }
            } else {
                uiState.record?.let { dayRecord ->
                    DetailsScreenContent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = paddingValues.calculateTopPadding())
                            .padding(8.dp)
                            .nestedScroll(rememberNestedScrollInteropConnection()),
                        record = dayRecord
                    )
                }

            }
        }
    )
    when (uiEvents) {
        is DetailsUiEvents.ShowEditDateDialog -> {
            Dialog(
                onDismissRequest = { viewModel.hideEditEntryDialog() }
            ) {
                Surface {
                    EditDayDialogContent(
                        currentDay = (uiState.record?.time ?: 0) * 1000,
                        onConfirm = {
                                    // TODO: call ViewModel to update date
                            },
                        onDismiss = { viewModel.hideEditEntryDialog() }
                    )
                }
            }

        }
        else -> { }
    }

}

@Composable
fun DetailsScreenContent(modifier: Modifier, record: Day) {
    LazyColumn(modifier = modifier) {
        items(1) {
            DayDetailsItem(record)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
@Preview
fun DetailsScreenContentPreview() {
    Surface {
        DetailsScreenContent(
            modifier = Modifier.fillMaxSize(),
            record = testRecord
        )
    }
}