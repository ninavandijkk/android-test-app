package com.example.testapplication.recipe.item

import com.example.testapplication.data.Recipe

sealed class ViewState{
    object Init: ViewState()
    object Loading: ViewState()
    data class Success(val recipe: Recipe): ViewState()
    data class Failure(val message: String?): ViewState()
}

