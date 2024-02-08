package de.alekseipopov.fooddiary.ui.overview

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun OverviewScreen(navController: NavHostController) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
    ) {

        val viewModel: OverviewViewModel = koinViewModel()
        val (greetingText, button) = createRefs()

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
            text = "Hello, World!"
        )

        Button(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .constrainAs(button) {
                    top.linkTo(greetingText.bottom, 8.dp)
                    start.linkTo(greetingText.start)
                    end.linkTo(greetingText.end)
                },
            onClick = { navController.navigate("details/1") }
        ) {
            Text("To details screen")
        }
    }
}