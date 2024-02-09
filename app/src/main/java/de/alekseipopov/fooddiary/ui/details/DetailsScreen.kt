package de.alekseipopov.fooddiary.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.alekseipopov.fooddiary.data.model.Meal
import de.alekseipopov.fooddiary.util.testMeal
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(navController: NavHostController, recordId: String) {
    /*ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {

        val (greetingText) = createRefs()

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .constrainAs(greetingText) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "Details screen. RecordId: $recordId"
        )
    }*/

    val viewModel: DetailsViewModel = koinViewModel()
    viewModel.getRecord(recordId)

    val record = viewModel.recordLiveData.observeAsState()

}

@Composable
fun MealListItem(
    meal: Meal
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    text = meal.title,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    text = meal.time ?: ""
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .wrapContentHeight()
            ) {
                meal.courses.forEach {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = it
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MealListItemPreview() {
    MealListItem(meal = testMeal)
}