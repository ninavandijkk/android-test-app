package com.example.testapplication.recipe.item

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.data.api.ApiService
import com.example.testapplication.PrepPartInterface
import com.example.testapplication.RecipeInterface
import com.example.testapplication.data.Recipe
import com.example.testapplication.data.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.IOException

class RecipeViewModel(
    private val repository: RecipesRepository
) : ViewModel() {
    val viewState: StateFlow<ViewState> = MutableStateFlow(ViewState.Init)

    fun getRecipeById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            (viewState as MutableStateFlow).emit(ViewState.Loading)
            try {
                val recipe = repository.getRecipeById(id)
                (viewState as MutableStateFlow).emit(ViewState.Success(recipe))
            } catch (e: Exception) {
                (viewState as MutableStateFlow).emit(ViewState.Failure(e.message))
            }
        }
    }

    fun addRecipeToFavorites(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            if(repository.isFavorite(recipe.iid)){
                repository.deleteItem(recipe)
            } else {
                repository.insertItem(recipe)
            }

        }
    }
}
