package com.example.testapplication.recipe.list

import com.example.testapplication.RecipeInterface

sealed class ViewState{
    object Init: ViewState()
    object Loading: ViewState()
    data class Success(val recipes: List<RecipeInterface>): ViewState()
    object Failure: ViewState()
}
