@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package com.example.testapplication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.testapplication.ui.FavoritesScreen
import com.example.testapplication.ui.HomeScreen
import com.example.testapplication.ui.RandomRecipeScreen
import com.example.testapplication.ui.RecipeDetailScreen
import com.example.testapplication.ui.RecipeSearchScreen
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDetail(val id: Int)

@Serializable
object Start

@Serializable
object Search

@Serializable
object Random

@Serializable
object Favorites

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeAppBar(
    currentScreen: Any,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("Recipe App") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun RecipeApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: Start

    Scaffold(
        topBar = {
            RecipeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Start,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Start>{
                HomeScreen(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable<Search> {
                RecipeSearchScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    navController = navController,
                )
            }
            composable<Random> {
                RandomRecipeScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    navController = navController,
                )
            }
            composable<Favorites> {
                FavoritesScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    navController = navController,
                )
            }
            composable<RecipeDetail> {
                backStackEntry ->
                val recipe: RecipeDetail = backStackEntry.toRoute()
                RecipeDetailScreen(
                    recipeId = recipe.id,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}
