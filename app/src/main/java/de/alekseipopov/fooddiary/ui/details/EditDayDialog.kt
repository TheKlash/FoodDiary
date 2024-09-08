package de.alekseipopov.fooddiary.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.alekseipopov.fooddiary.R
import de.alekseipopov.fooddiary.core.format.dateMillisToFullMillis
import de.alekseipopov.fooddiary.core.format.fullMillisToDateMillis
import de.alekseipopov.fooddiary.core.format.unixTimeToDateFull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDayDialogContent(
    currentDay: Long? = null,
    onConfirm: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = (currentDay?.dateMillisToFullMillis()) ?: (System.currentTimeMillis())
    )
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var selectedTimeMillis by remember { mutableLongStateOf(
        currentDay?.dateMillisToFullMillis() ?: (System.currentTimeMillis())
    ) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (currentDay == null) {
            Text(
                stringResource(R.string.edit_day_title_new_day),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        } else {
            Text(
                stringResource(R.string.edit_day_title),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { showDatePickerDialog = !showDatePickerDialog }
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = selectedTimeMillis
                    .fullMillisToDateMillis()
                    .unixTimeToDateFull()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(
                    color = MaterialTheme.colorScheme.secondary,
                    text = stringResource(R.string.edit_day_dismiss)
                )
            }
            TextButton(
                onClick = {
                    onConfirm(selectedTimeMillis.fullMillisToDateMillis())
                }
            ) {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    text = stringResource(R.string.edit_day_confirm)
                )
            }
        }
    }
    if (showDatePickerDialog) {
        DatePickerDialog(
            content = { DatePicker(state = datePickerState) },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedTimeMillis = datePickerState.selectedDateMillis ?: 0L
                        showDatePickerDialog = false
                    }
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        text = stringResource(R.string.edit_day_confirm)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePickerDialog = false }
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.secondary,
                        text = stringResource(R.string.edit_day_dismiss)
                    )
                }
            },
            onDismissRequest = { showDatePickerDialog = false }
        )
    }
}

@Composable
@Preview
fun EditDayDialogContentPreview_NoDate() {
    Surface {
        EditDayDialogContent(
            currentDay = null,
            onConfirm = {},
            onDismiss = {}
        )
    }
}

@Composable
@Preview
fun EditDayDialogContentPreview_WithDate() {
    Surface {
        EditDayDialogContent(
            currentDay = 1708905600,
            onConfirm = {},
            onDismiss = {}
        )
    }
}
