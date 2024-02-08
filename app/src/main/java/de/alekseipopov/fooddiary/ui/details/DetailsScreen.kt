package de.alekseipopov.fooddiary.ui.details

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(navController: NavHostController, recordId: String) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {

        val detailsViewModel: DetailsViewModel = koinViewModel()
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


    }

}