package com.example.testapplication.recipe.item

import com.example.testapplication.PrepPartInterface
import com.example.testapplication.RecipeInterface

sealed class ViewState{
    object Init: ViewState()
    object Loading: ViewState()
    data class Success(val recipe: RecipeInterface, val prep: List<PrepPartInterface>?): ViewState()
    object Failure: ViewState()
}

