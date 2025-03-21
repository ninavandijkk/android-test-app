package com.example.testapplication.data

import com.example.testapplication.recipe.Recipes
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RecipesRepository {

    fun getAllRecipesStream(): Flow<List<Recipe>>

    suspend fun getRecipeById(id: Int): Recipe

    suspend fun searchRecipes(query: String): Response<Recipes>

    suspend fun getRandomRecipes(): Response<Recipes>

    fun isFavorite(id: Int): Boolean

    suspend fun insertItem(recipe: Recipe)

    suspend fun deleteItem(recipe: Recipe)

    suspend fun updateItem(recipe: Recipe)
}
