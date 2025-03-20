package com.example.testapplication.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.testapplication.RecipeApplication
import com.example.testapplication.recipe.list.RecipeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            RecipeViewModel(
                recipeApplication().container.recipesRepository
            )
        }
        initializer {
            com.example.testapplication.recipe.item.RecipeViewModel(
                recipeApplication().container.recipesRepository
            )
        }
    }
}

fun CreationExtras.recipeApplication(): RecipeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as RecipeApplication)
