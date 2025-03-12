package com.example.testapplication

import com.example.testapplication.recipe.PrepPart
import com.example.testapplication.recipe.RecipeModel
import com.example.testapplication.recipe.Recipes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("recipes/random?apiKey=c343d02710f54898b0b861240daa570f&number=3")
    suspend fun getRandomRecipes(): Response<Recipes>

    @GET("recipes/complexSearch?apiKey=c343d02710f54898b0b861240daa570f")
    suspend fun searchRecipes(@Query("query") query: String): Response<Recipes>

    @GET("recipes/{id}/information?apiKey=c343d02710f54898b0b861240daa570f")
    suspend fun getRecipeById(@Path("id") recipeId: Int): Response<RecipeModel>

    @GET("recipes/{id}/analyzedInstructions?apiKey=c343d02710f54898b0b861240daa570f")
    suspend fun getRecipeInstructionsById(@Path("id") recipeId: Int): Response<List<PrepPart>>
}
