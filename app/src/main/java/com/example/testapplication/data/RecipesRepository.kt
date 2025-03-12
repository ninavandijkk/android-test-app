package com.example.testapplication.data

import kotlinx.coroutines.flow.Flow

interface RecipesRepository {

    fun getAllRecipesStream(): Flow<List<Recipe>>

    fun getRecipeStream(id: Int): Flow<Recipe>

    fun isFavorite(id: Int): Boolean

    suspend fun insertItem(recipe: Recipe)

    suspend fun deleteItem(recipe: Recipe)

    suspend fun updateItem(recipe: Recipe)
}
