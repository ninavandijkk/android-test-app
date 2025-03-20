package com.example.testapplication.data

import android.content.Context
import com.example.testapplication.data.api.ApiClient

interface AppContainer {
    val recipesRepository: RecipesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val recipesRepository: RecipesRepository by lazy {
        OfflineRecipesRepository(RecipeDatabase.getDataBase(context).recipeDao(), ApiClient.apiService)
    }
}
