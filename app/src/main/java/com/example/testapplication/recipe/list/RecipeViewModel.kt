package com.example.testapplication.recipe.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException

class RecipeViewModel(private val repository: RecipesRepository) : ViewModel() {
    val viewState: StateFlow<ViewState> = MutableStateFlow(ViewState.Init)

    fun getRandomRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            (viewState as MutableStateFlow).emit(ViewState.Loading)
            try {
                val response = repository.getRandomRecipes()

                if (response.isSuccessful){
                    val body = response.body()!!
                    Log.d("RecipeViewModel", "$body")
                    (viewState as MutableStateFlow).emit(ViewState.Success(body.recipeList))
                } else {
                    Log.e("RecipeViewModel", "Call failed ${response.code()} ${response.errorBody()}")
                    (viewState as MutableStateFlow).emit(ViewState.Failure(response.message()))
                }
            } catch (e: IOException) {
                (viewState as MutableStateFlow).emit(ViewState.Failure(e.message))
            }
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            (viewState as MutableStateFlow).emit(ViewState.Loading)
            try {
                val response = repository.searchRecipes(query)

                Log.d("RecipeViewModel", "$response")

                if (response.isSuccessful){
                    val body = response.body()!!
                    Log.d("RecipeViewModel", "$body")
                    (viewState as MutableStateFlow).emit(ViewState.Success(body.searchResults))
                } else {
                    val errorResponse = response
                    Log.e("RecipeViewModel", "Call failed ${errorResponse.code()} ${errorResponse.errorBody()}")
                    (viewState as MutableStateFlow).emit(ViewState.Failure(errorResponse.message()))
                }
            } catch (e: IOException) {
                (viewState as MutableStateFlow).emit(ViewState.Failure(e.message))
            }
        }
    }

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            (viewState as MutableStateFlow).emit(ViewState.Loading)

            repository.getAllRecipesStream().collectLatest {recipes ->
                (viewState as MutableStateFlow).emit(ViewState.Success(recipes))
            }
        }
    }

    fun addRecipeToFavorites(recipeIid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val recipe = repository.getRecipeById(recipeIid)
            if(repository.isFavorite(recipeIid)){
                repository.deleteItem(recipe)
            } else {
                repository.insertItem(recipe)
            }
        }
    }
}
