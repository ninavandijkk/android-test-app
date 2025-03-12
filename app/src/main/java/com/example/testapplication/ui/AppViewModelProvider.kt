package com.example.testapplication.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.testapplication.ApiClient
import com.example.testapplication.RecipeApplication
import com.example.testapplication.recipe.list.RecipeViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            RecipeViewModel(
                ApiClient.apiService,
                recipeApplication().container.recipesRepository
            )
        }
        initializer {
            com.example.testapplication.recipe.item.RecipeViewModel(
                ApiClient.apiService,
                recipeApplication().container.recipesRepository
            )
        }
    }
}

fun CreationExtras.recipeApplication(): RecipeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as RecipeApplication)
