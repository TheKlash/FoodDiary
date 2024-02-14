package de.alekseipopov.fooddiary.ui.details

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.data.model.Meal
import de.alekseipopov.fooddiary.ui.overview.OverviewScreenContent
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme
import de.alekseipopov.fooddiary.util.testMeal
import de.alekseipopov.fooddiary.util.testRecord
import de.alekseipopov.fooddiary.util.unixTimeToDate
import de.alekseipopov.fooddiary.util.unixTimeToTime
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavHostController, recordId: String) {

    val viewModel: DetailsViewModel = koinViewModel()
    val record = viewModel.record.collectAsState()
    LaunchedEffect(key1 = record) {
        viewModel.getRecord(recordId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                   IconButton(
                       onClick = { navController.popBackStack() }
                   ) {
                       Image(
                           imageVector = Icons.Filled.ArrowBack,
                           colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                           contentDescription = null
                       )
                   }
                } ,
                title = { Text(text = "Day review") }
            )
        },
        content = { paddingValues ->
            record.value?.let {
                DetailsScreenContent(navController, paddingValues, it)
            }
        }
    )
}

@Composable
fun DetailsScreenContent(
    navController: NavHostController,
    paddingValues: PaddingValues? = null,
    dayRecord: DayRecord?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .offset(y = paddingValues?.calculateTopPadding() ?: 0.dp)
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp,
            text = dayRecord?.date?.unixTimeToDate() ?: ""
        )
        Spacer(
            modifier = Modifier
                .height(24.dp)
        )
        Text(
            text = "${dayRecord?.meals?.size} meals"
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = dayRecord?.meals ?: emptyList(),
                itemContent = {
                    MealListItem(it)
                }
            )
        }
    }
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
                    fontSize = 20.sp,
                    text = meal.title,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    text = meal.time.unixTimeToTime() ?: ""
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                meal.courses.forEach {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
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
fun DetailsScreenContentPreview() {
    FoodDiaryTheme {
        Surface {
            DetailsScreenContent(
                navController = rememberNavController(),
                dayRecord = testRecord
            )
        }
    }
}

@Preview
@Composable
fun MealListItemPreview() {
    MealListItem(meal = testMeal)
}