package de.alekseipopov.fooddiary.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import de.alekseipopov.fooddiary.data.model.Day
import de.alekseipopov.fooddiary.ui.base.UiState
import de.alekseipopov.fooddiary.ui.details.model.DetailsUiEvents
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme
import de.alekseipopov.fooddiary.util.testDay

@Composable
fun DetailsScreen(
    navigateBack: () -> Unit,
    viewModel: DetailsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiEvents by viewModel.uiEvents.collectAsState()

    when (val uiState = uiState) {
        is UiState.Error -> { }
        is UiState.Loading -> { StateLoading() }
        is UiState.Result -> {
            val day = uiState.data

            StateResult(
                navigateBack = navigateBack,
                onTopBarEditClick = { viewModel.showEditEntryDialog() },
                onTopBarDeleteClick = { viewModel.showDeleteDialog() },
                day = day
            )

            ObserveUiEvents(uiEvents, viewModel, day, navigateBack)
        }
    }
}

@Composable
private fun ObserveUiEvents(
    uiEvents: DetailsUiEvents?,
    viewModel: DetailsViewModel,
    day: Day,
    onBackPressed: () -> Unit
) {
    when (uiEvents) {
        is DetailsUiEvents.ShowEditDateDialog -> {
            Dialog(onDismissRequest = { viewModel.hideEditEntryDialog() }) {
                Surface {
                    EditDayDialogContent(
                        currentDay = day.time,
                        onConfirm = {
                            viewModel.updateDate(it / 1000)
                            viewModel.hideEditEntryDialog() },
                        onDismiss = { viewModel.hideEditEntryDialog() })
                }
            }

        }

        is DetailsUiEvents.ShowDeleteDialog -> {
            Dialog(onDismissRequest = { viewModel.hideDeleteDialog() }) {
                Surface {
                    DeleteDayDialog(
                        onConfirm = { viewModel.deleteDay() },
                        onDismiss = { viewModel.hideDeleteDialog() }
                    )
                }
            }
        }

        is DetailsUiEvents.NavigateBack -> {
            onBackPressed()
        }

        else -> {}
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    onBackPressed: () -> Unit,
    title: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentDescription = null
                )
            }
        },
        title = {
            Text(text = title)
        },
        actions = {
            TopBarActions(
                edit = { onEditClick() },
                delete = { onDeleteClick() }
            )
        }
    )
}

@Composable
private fun StateResult(
    navigateBack: () -> Unit = {},
    onTopBarEditClick: () -> Unit = {},
    onTopBarDeleteClick: () -> Unit = {},
    day: Day
) {
    Scaffold(
        topBar = {
            TopBar (
                onBackPressed = navigateBack,
                title = day.fullTime,
                onEditClick = onTopBarEditClick,
                onDeleteClick = onTopBarDeleteClick
            ) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .nestedScroll(rememberNestedScrollInteropConnection())
                ) {
                    DayDetailsItem(day)
                }
            }
        }
    )
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
private fun TopBarActions(
    edit: () -> Unit,
    delete: () -> Unit
) {
    IconButton(onClick = { edit() }) {
        Image(
            imageVector = Icons.Filled.Edit,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentDescription = null
        )
    }
    IconButton(onClick = { delete() }) {
        Image(
            imageVector = Icons.Filled.Delete,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentDescription = null
        )
    }

}

@Composable
@Preview
private fun TopBarPreview() {
    Surface {
        FoodDiaryTheme {
            TopBar(
                onBackPressed = {  },
                title = "Title",
                onEditClick = { },
                onDeleteClick = { }
            )
        }
    }
}

@Composable
@Preview
private fun StateLoadingPreview() {
    Surface {
        StateLoading()
    }
}

@Composable
@Preview
private fun StateResultPreview() {
    Surface {
        StateResult (
            day = testDay
        )
    }
}