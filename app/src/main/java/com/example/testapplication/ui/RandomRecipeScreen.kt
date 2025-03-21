package com.example.testapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.testapplication.recipe.list.RecipeViewModel
import com.example.testapplication.recipe.list.ViewState
import com.example.testapplication.ui.components.RecipeList

@Composable
fun RandomRecipeScreen(
    modifier: Modifier,
    navController: NavController,
) {
    val vm : RecipeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val viewState by vm.viewState.collectAsState()

    Column(
        modifier = modifier
            .heightIn(min = 16.dp, max = 32.dp)
            .padding(vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = viewState) {
            is ViewState.Init -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Text(
                        "Press the button to display 3 random recipes",
                        textAlign = TextAlign.Center
                    )
                }
            }
            is ViewState.Loading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(32.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
            is ViewState.Success -> {
                RecipeList(state.recipes, navController, vm, false)
            }

            is ViewState.Failure -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Text(
                        state.message ?: "An error occurred",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { vm.getRandomRecipes() },
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text("Get random recipes")
        }
    }
}
