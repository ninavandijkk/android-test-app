package com.example.testapplication.data

import kotlinx.coroutines.flow.Flow

class OfflineRecipesRepository(private val recipeDao: RecipeDao) : RecipesRepository {
    override fun getAllRecipesStream(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    override fun getRecipeStream(id: Int): Flow<Recipe> = recipeDao.getRecipe(id)

    override fun isFavorite(id: Int): Boolean = recipeDao.isFavorite(id)

    override suspend fun insertItem(recipe: Recipe) = recipeDao.insert(recipe)

    override suspend fun deleteItem(recipe: Recipe) = recipeDao.delete(recipe)

    override suspend fun updateItem(recipe: Recipe) = recipeDao.update(recipe)
}
