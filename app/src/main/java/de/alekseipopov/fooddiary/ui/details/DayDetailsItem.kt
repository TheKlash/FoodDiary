package de.alekseipopov.fooddiary.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.data.model.Meal
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme
import de.alekseipopov.fooddiary.util.testRecord
import de.alekseipopov.fooddiary.util.unixTimeToDate
import de.alekseipopov.fooddiary.util.unixTimeToTime

@Composable
fun DayDetailsItem(
    dayRecord: DayRecord?
) {
    Column {
        Text(
            fontSize = 24.sp,
            text = dayRecord?.date?.unixTimeToDate() ?: ""
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "${dayRecord?.meals?.size} meals")
        dayRecord?.meals?.forEach {
            Spacer(modifier = Modifier.height(8.dp))
            MealListItem(meal = it)
        }

    }
}

@Composable
fun MealListItem(
    meal: Meal
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    text = meal.title,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    text = meal.time.unixTimeToTime() ?: ""
                )
            }
            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                meal.courses.forEach {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        text = it
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DayDetailsItemPreview() {
    FoodDiaryTheme {
        Surface {
            DayDetailsItem(dayRecord = testRecord)
        }
    }
}