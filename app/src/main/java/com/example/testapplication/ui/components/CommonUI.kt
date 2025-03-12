package com.example.testapplication.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.testapplication.RecipeDetail
import com.example.testapplication.RecipeInterface
import com.example.testapplication.recipe.list.RecipeViewModel

@Composable
fun RecipeList (recipes: List<RecipeInterface>, navController: NavController, vm: RecipeViewModel){
    if(recipes.isNotEmpty()){
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .padding(vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            for (recipe: RecipeInterface in recipes){
                RecipeView(recipe, navController, vm)
            }
        }
    } else {
        Text(
            "No results found"
        )
    }

}

@Composable
fun RecipeView(recipe: RecipeInterface, navController: NavController, vm: RecipeViewModel) {
    val context = LocalContext.current
    Row(
        Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .fillMaxWidth()
            .clickable { navController.navigate(RecipeDetail(recipe.iid)) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = "Image of the recipe ${recipe.title}"
        )
        Column(
            Modifier
                .padding(16.dp)
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = recipe.title,
            )
            Column {
                Row{
                    Icon(
                        Icons.Rounded.Person,
                        contentDescription = "Icon for number of servings"
                    )
                    Text(
                        "${recipe.nrOfServings}"
                    )
                }
                Row {
                    Text(
                        "Preparation time: ${recipe.prepTime} minutes"
                    )
                }
                if (recipe.isVegan){
                    Text("Vegan")
                }
                if(recipe.isVegetarian){
                    Text("Vegetarian")
                }
            }
            IconButton(
                onClick = {
                    vm.addRecipeToFavorites(recipe.iid)
                    Toast.makeText(context, "Recipe added/removed from favorites", Toast.LENGTH_LONG).show()
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
