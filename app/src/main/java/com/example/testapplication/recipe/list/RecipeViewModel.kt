package com.example.testapplication.recipe.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.RecipesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

class RecipeViewModel(private val repository: RecipesRepository) : ViewModel() {
    val viewState: StateFlow<ViewState> = MutableStateFlow(ViewState.Init)

    fun getRandomRecipes() {
        viewModelScope.launch {
            (viewState as MutableStateFlow).emit(ViewState.Loading)
            try {
                val recipes = repository.getRandomRecipes()

                if (recipes != null){
                    (viewState as MutableStateFlow).emit(ViewState.Success(recipes.recipeList))
                } else {
                    Log.e("RecipeViewModel", "Call failed no recipes found")
                    (viewState as MutableStateFlow).emit(ViewState.Failure("No recipes found"))
                }
            } catch (e: IOException) {
                (viewState as MutableStateFlow).emit(ViewState.Failure(e.message))
            }
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            (viewState as MutableStateFlow).emit(ViewState.Loading)
            try {
                val recipes = repository.searchRecipes(query)

                if (recipes != null){
                    (viewState as MutableStateFlow).emit(ViewState.Success(recipes.searchResults))
                } else {
                    (viewState as MutableStateFlow).emit(ViewState.Failure("No recipes found"))
                }
            } catch (e: IOException) {
                (viewState as MutableStateFlow).emit(ViewState.Failure(e.message))
            }
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            (viewState as MutableStateFlow).emit(ViewState.Loading)

            repository.getAllRecipesStream().collectLatest {recipes ->
                (viewState as MutableStateFlow).emit(ViewState.Success(recipes))
            }
        }
    }

    fun addRecipeToFavorites(recipeIid: Int) {
        viewModelScope.launch {
            val recipe = repository.getRecipeById(recipeIid)
            if(repository.isFavorite(recipeIid)){
                repository.deleteItem(recipe)
            } else {
                repository.insertItem(recipe)
            }
        }
    }
}
