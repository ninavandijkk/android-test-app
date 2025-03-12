package com.example.testapplication.recipe.list

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.ApiService
import com.example.testapplication.data.Recipe
import com.example.testapplication.data.RecipesRepository
import com.example.testapplication.recipe.PrepPart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RecipeViewModel(private val service: ApiService, private val repository: RecipesRepository) : ViewModel() {
    val viewState: StateFlow<ViewState> = MutableStateFlow(ViewState.Init)

    fun getRandomRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            (viewState as MutableStateFlow).emit(ViewState.Loading)
            val response = service.getRandomRecipes()

            if (response.isSuccessful){
                val body = response.body()!!
                Log.d("RecipeViewModel", "$body")
                (viewState as MutableStateFlow).emit(ViewState.Success(body.recipeList))
            } else {
                Log.e("RecipeViewModel", "Call failed ${response.code()} ${response.errorBody()}")
                (viewState as MutableStateFlow).emit(ViewState.Failure)
            }
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            (viewState as MutableStateFlow).emit(ViewState.Loading)
            val response = service.searchRecipes(query)

            Log.d("RecipeViewModel", "$response")

            if (response.isSuccessful){
                val body = response.body()!!
                Log.d("RecipeViewModel", "$body")
                (viewState as MutableStateFlow).emit(ViewState.Success(body.searchResults))
            } else {
                Log.e("RecipeViewModel", "Call failed ${response.code()} ${response.errorBody()}")
                (viewState as MutableStateFlow).emit(ViewState.Failure)
            }
        }
    }

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            (viewState as MutableStateFlow).emit(ViewState.Loading)
            val recipes = repository.getAllRecipesStream()

            Log.d("RecipeViewModel", "$recipes")
            (viewState as MutableStateFlow).emit(ViewState.Success(recipes.first()))
        }
    }

    fun addRecipeToFavorites(recipeIid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("RecipeViewModel(list)", "${repository.isFavorite(recipeIid)}")
            if(repository.isFavorite(recipeIid)){
                val recipe = repository.getRecipeStream(recipeIid).first()
                repository.deleteItem(recipe)
            } else {
                val responseRecipe = service.getRecipeById(recipeIid)
                val responsePrep = service.getRecipeInstructionsById(recipeIid)
                if (responseRecipe.isSuccessful){
                    val recipe = responseRecipe.body()!!
                    var prep : List<PrepPart>? = null
                    if(responsePrep.isSuccessful){
                        prep = responsePrep.body()!!
                    }
                    val formatedRecipe = Recipe(
                        iid = recipe.iid,
                        title = recipe.title,
                        isVegan = recipe.isVegan,
                        isVegetarian = recipe.isVegetarian,
                        prepTime = recipe.prepTime,
                        nrOfServings = recipe.nrOfServings,
                        imageUrl = recipe.imageUrl,
                        ingredients = recipe.ingredients as List<com.example.testapplication.data.Ingredient>,
                        steps = prep as List<com.example.testapplication.data.PrepPart>,
                    )
                    repository.insertItem(formatedRecipe)
                }
            }
        }
    }
}
