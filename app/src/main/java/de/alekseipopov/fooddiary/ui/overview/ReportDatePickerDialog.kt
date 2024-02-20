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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme
import de.alekseipopov.fooddiary.util.unixTimeToDate
import java.util.Date

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
                        .wrapContentSize(),
                    textAlign = TextAlign.Center,
                    text = startDatePickerState.selectedDateMillis?.div(1000)?.unixTimeToDate()
                        ?: "Select start date"
                )
            }

            Text(text = "To")

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    showEndDatePickerDialog = !showEndDatePickerDialog && !showStartDatePickerDialog
                }
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentSize(),
                    textAlign = TextAlign.Center,
                    text = endDatePickerState.selectedDateMillis?.div(1000)?.unixTimeToDate()
                        ?: "Select end date"
                )
            }

            if (showStartDatePickerDialog) {
                DatePicker(
                    state = startDatePickerState,
                    dateValidator = { date ->
                        date < (endDatePickerState.selectedDateMillis ?: Date().time)
                    }
                )
                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { showStartDatePickerDialog = false }
                ) {
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
                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { showEndDatePickerDialog = false },
                ) {
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
