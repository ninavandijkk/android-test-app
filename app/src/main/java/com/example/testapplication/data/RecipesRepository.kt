package com.example.testapplication.data

import com.example.testapplication.recipe.Recipes
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getAllRecipesStream(): Flow<List<Recipe>>

    suspend fun getRecipeById(id: Int): Recipe

    suspend fun searchRecipes(query: String): Recipes?

    suspend fun getRandomRecipes(): Recipes?

    fun isFavorite(id: Int): Boolean

    suspend fun insertItem(recipe: Recipe)

    suspend fun deleteItem(recipe: Recipe)

    suspend fun updateItem(recipe: Recipe)
}
