package com.example.testapplication.data

import android.content.Context

interface AppContainer {
    val recipesRepository: RecipesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val recipesRepository: RecipesRepository by lazy {
        OfflineRecipesRepository(RecipeDatabase.getDataBase(context).recipeDao())
    }
}
