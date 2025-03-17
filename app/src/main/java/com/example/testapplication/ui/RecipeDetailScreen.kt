package com.example.testapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.testapplication.IngredientInterface
import com.example.testapplication.PrepPartInterface
import com.example.testapplication.PrepStepInterface
import com.example.testapplication.RecipeInterface
import com.example.testapplication.data.Recipe
import com.example.testapplication.recipe.item.ViewState
import com.example.testapplication.recipe.item.RecipeViewModel

@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    modifier: Modifier = Modifier
) {
    val vm : RecipeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val viewState by vm.viewState.collectAsState()

    Column {
        when (val state = viewState) {
            is ViewState.Init -> {
                vm.getRecipeById(recipeId)
            }
            is ViewState.Loading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier.fillMaxHeight()
                ) {
                    CircularProgressIndicator(
                        modifier = modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
            is ViewState.Success -> {
                RecipeView(state.recipe, state.prep, vm)
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
}

@Composable
fun RecipeView(recipe: RecipeInterface, prep: List<PrepPartInterface>?, vm : RecipeViewModel) {
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(),
    ) {
        RecipeInfoCard(recipe, vm, prep)
        IngredientList(recipe.ingredients)
        PrepPartList(prep)
    }
}

@Composable
fun RecipeInfoCard(recipe: RecipeInterface, vm : RecipeViewModel, prep: List<PrepPartInterface>?) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = "Image of the recipe ${recipe.title}",
            modifier = Modifier.fillMaxWidth()
        )
        Column (
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = recipe.title,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                lineHeight = 32.sp
            )
            Row (
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row {
                    Icon(
                        Icons.Rounded.Person,
                        contentDescription = "Icon for number of servings"
                    )
                    Text(
                        "${recipe.nrOfServings}"
                    )
                }
                VerticalDivider()
                Text(
                    "Preparation time: ${recipe.prepTime} minutes"
                )
            }
            IconButton(
                onClick = {
                    vm.addRecipeToFavorites(recipe, prep)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite icon"
                )
            }

        }
    }
}

@Composable
fun IngredientList(ingredients: List<IngredientInterface>){
    Column (
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text= "Ingredients",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        for (ingredient: IngredientInterface in ingredients) {
            Text(
                text = ingredient.original,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun PrepPartList(prepParts: List<PrepPartInterface>?){
    if (!prepParts.isNullOrEmpty()) {
        Column (
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Preparation steps",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
            for (prepPart: PrepPartInterface in prepParts) {
                Text(prepPart.name)
                for (prepStep: PrepStepInterface in prepPart.steps) {
                    PrepStep(prepStep)
                }
            }
        }
    }
}

@Composable
fun PrepStep(prepStep: PrepStepInterface){
    Column {
        Text(
            text = "Step ${prepStep.number}",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp, top = 10.dp)
        )
        Text(
            text = prepStep.step,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        HorizontalDivider()
    }
}
