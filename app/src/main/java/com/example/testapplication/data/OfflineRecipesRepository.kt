package com.example.testapplication.data

import com.example.testapplication.data.api.ApiService
import com.example.testapplication.recipe.Recipes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okio.IOException

class OfflineRecipesRepository(private val recipeDao: RecipeDao, private val apiService: ApiService) : RecipesRepository {
    override fun getAllRecipesStream(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    override suspend fun getRecipeById(id: Int): Recipe = withContext(Dispatchers.IO) {
        if(isFavorite(id)){
            return@withContext recipeDao.getRecipe(id)
        } else {
            val responseRecipe = apiService.getRecipeById(id)
            val responsePrep = apiService.getRecipeInstructionsById(id)

            if (responseRecipe.isSuccessful){
                val bodyRecipe = responseRecipe.body()!!
                if(responsePrep.isSuccessful){
                    val bodyPrep = responsePrep.body()!!
                    val recipe = Recipe(
                        iid = bodyRecipe.iid,
                        title = bodyRecipe.title,
                        isVegan = bodyRecipe.isVegan,
                        isVegetarian = bodyRecipe.isVegetarian,
                        prepTime = bodyRecipe.prepTime,
                        nrOfServings = bodyRecipe.nrOfServings,
                        imageUrl = bodyRecipe.imageUrl,
                        ingredients = bodyRecipe.ingredients as List<Ingredient>,
                        steps = bodyPrep as List<PrepPart>,
                    )
                    return@withContext recipe
                } else {
                    return@withContext responseRecipe.body() as Recipe
                }
            } else {
                throw IOException("Request not successful")
            }
        }
    }

    override suspend fun searchRecipes(query: String): Recipes? = withContext(Dispatchers.IO) {
        return@withContext apiService.searchRecipes(query).body()
    }

    override suspend fun getRandomRecipes(): Recipes? = withContext(Dispatchers.IO) {
        return@withContext apiService.getRandomRecipes().body()
    }

    override fun isFavorite(id: Int): Boolean = recipeDao.isFavorite(id)

    override suspend fun insertItem(recipe: Recipe) = recipeDao.insert(recipe)

    override suspend fun deleteItem(recipe: Recipe) = recipeDao.delete(recipe)

    override suspend fun updateItem(recipe: Recipe) = recipeDao.update(recipe)
}
