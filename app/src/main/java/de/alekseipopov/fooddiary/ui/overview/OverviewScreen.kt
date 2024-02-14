package de.alekseipopov.fooddiary.ui.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.alekseipopov.fooddiary.data.model.DayRecord
import de.alekseipopov.fooddiary.ui.theme.FoodDiaryTheme
import de.alekseipopov.fooddiary.util.testRecord
import de.alekseipopov.fooddiary.util.testRecordList
import de.alekseipopov.fooddiary.util.unixTimeToDate
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun OverviewScreen(navController: NavHostController) {

    val viewModel: OverviewViewModel = koinViewModel()
    val recordsList = viewModel.recordsList.collectAsState()
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
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.Add, "") },
                text = { Text("Add") },
                onClick = { /*do something*/ },
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            )
        },
        content = { paddingValues ->
            OverviewScreenContent(
                paddingValues, recordsList.value, navController
            )
        }
    )
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
                text = "${dayRecord.date.unixTimeToDate()}",
                fontSize = 24.sp
            )
        }
    }
}

@Composable
@Preview
fun DayRecordListItemPreview() {
    DayRecordListItem(
        testRecord,
        navigate = { }
    )
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

