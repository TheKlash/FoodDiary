package de.alekseipopov.fooddiary.ui.overview

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.alekseipopov.fooddiary.R
import de.alekseipopov.fooddiary.core.ui.style.FoodDiaryTheme
import de.alekseipopov.fooddiary.core.format.unixTimeToDateFull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDatePickerDialogContent(
    onConfirm: (Long, Long) -> Unit = { _, _ -> },
    onDismiss: () -> Unit = { },
) {
    var startDateMills = remember { 0L }
    var endDateMills = remember { 0L }
    var currentDateMills = remember { System.currentTimeMillis() }

    var endPickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(selectedDateMills: Long): Boolean {
                return selectedDateMills in (startDateMills + 1)..<currentDateMills
            }
        }
    )

    var startPickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(selectedDateMills: Long): Boolean {
                return selectedDateMills in (endDateMills + 1)..<currentDateMills
            }
        }
    )

    var showStartPickerDialog by remember { mutableStateOf(false) }
    var showEndPickerDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Surface(
        modifier = Modifier.width(500.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(stringResource(R.string.report_from))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showStartPickerDialog = !showStartPickerDialog && !showEndPickerDialog
                }
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = startPickerState.selectedDateMillis?.div(1000)?.unixTimeToDateFull()
                        ?: stringResource(R.string.report_select_start_date)
                )
            }

            Text(stringResource(R.string.report_to))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showEndPickerDialog = !showEndPickerDialog && !showStartPickerDialog
                }
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = endPickerState.selectedDateMillis?.div(1000)?.unixTimeToDateFull()
                        ?: stringResource(R.string.report_select_end_date)
                )
            }

            if (showStartPickerDialog) {
                DatePickerDialog(
                    content = {
                        DatePicker(
                            state = startPickerState,
                        )

                    },
                    confirmButton = {
                        startDateMills = startPickerState.selectedDateMillis ?: 0L
                        TextButton(
                            onClick = { showStartPickerDialog = false }
                        ) {
                            Text(stringResource(R.string.report_ok))
                        }
                    },
                    onDismissRequest = { showEndPickerDialog = false },
                )
            }

            if (showEndPickerDialog) {
                DatePickerDialog(
                    content = {
                        DatePicker(
                            state = endPickerState
                        )

                    },
                    confirmButton = {
                        endDateMills = endPickerState.selectedDateMillis ?: 0L
                        TextButton(
                            onClick = { showEndPickerDialog = false }
                        ) {
                            Text(stringResource(R.string.report_ok))
                        }
                    },
                    onDismissRequest = { showEndPickerDialog = false },
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onDismiss() }) {
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        text = stringResource(R.string.report_dismiss)
                    )
                }

                TextButton(
                    onClick = {
                        if (startDateMills != 0L && endDateMills != 0L) {
                            onConfirm(startDateMills.div(1000), endDateMills.div(1000))
                        } else {
                            val toast = Toast(context)
                            toast.setText(R.string.report_select_dates)
                            toast.show()
                        }
                    }
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        text = stringResource(R.string.report_confirm)
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
            ReportDatePickerDialogContent()
        }
    }
}
