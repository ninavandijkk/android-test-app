package com.example.testapplication.recipe.item

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.ApiService
import com.example.testapplication.PrepPartInterface
import com.example.testapplication.RecipeInterface
import com.example.testapplication.data.Recipe
import com.example.testapplication.data.RecipeDatabase
import com.example.testapplication.data.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.IOException

class RecipeViewModel(
    private val service: ApiService,
    private val repository: RecipesRepository
) : ViewModel() {
    val viewState: StateFlow<ViewState> = MutableStateFlow(ViewState.Init)

    fun getRecipeById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            (viewState as MutableStateFlow).emit(ViewState.Loading)
            if(repository.isFavorite(id)){
                val recipe = repository.getRecipeStream(id).firstOrNull()
                Log.i("RecipeViewModel(item)", "${recipe?.steps?.first()}")
                if(recipe != null){
                    (viewState as MutableStateFlow).emit(ViewState.Success(recipe, recipe.steps as List<PrepPartInterface>?))
                } else {
                    (viewState as MutableStateFlow).emit(ViewState.Failure("No recipes found"))
                }
            } else {
                try {
                    val responseRecipe = service.getRecipeById(id)
                    val responsePrep = service.getRecipeInstructionsById(id)

                    if (responseRecipe.isSuccessful){
                        val bodyRecipe = responseRecipe.body()!!
                        if(responsePrep.isSuccessful){
                            val bodyPrep = responsePrep.body()!!
                            Log.i("RecipeViewModel(item)", "${bodyPrep.first()}")
                            (viewState as MutableStateFlow).emit(ViewState.Success(bodyRecipe, bodyPrep))
                        } else {
                            (viewState as MutableStateFlow).emit(ViewState.Success(bodyRecipe, null))
                        }
                    } else {
                        val errorResponse = responseRecipe
                        Log.e("RecipeViewModel", "Call failed ${errorResponse.code()} ${errorResponse.errorBody()}")
                        (viewState as MutableStateFlow).emit(ViewState.Failure(errorResponse.message()))
                    }
                } catch (e: IOException) {
                    (viewState as MutableStateFlow).emit(ViewState.Failure(e.message))
                }
            }
        }
    }

    fun addRecipeToFavorites(recipe: RecipeInterface, prep: List<PrepPartInterface>?) {
        viewModelScope.launch(Dispatchers.IO) {
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
            if(repository.isFavorite(recipe.iid)){
                repository.deleteItem(formatedRecipe)
            } else {
                repository.insertItem(formatedRecipe)
            }

        }
    }
}
