package com.example.testapplication.recipe

import com.example.testapplication.EquipmentInterface
import com.example.testapplication.IngredientInterface
import com.example.testapplication.PrepPartInterface
import com.example.testapplication.PrepStepInterface
import com.example.testapplication.RecipeInterface
import com.google.gson.annotations.SerializedName

data class Recipes(
    @SerializedName("recipes")
    var recipeList: List<RecipeModel>,
    @SerializedName("results")
    var searchResults: List<RecipeModel>,
)

data class RecipeModel(
    @SerializedName("id")
    override val iid: Int,
    @SerializedName("title")
    override val title: String,
    @SerializedName("image")
    override val imageUrl: String?,
    @SerializedName("servings")
    override val nrOfServings: Int?,
    @SerializedName("vegan")
    override val isVegan: Boolean,
    @SerializedName("vegetarian")
    override val isVegetarian: Boolean,
    @SerializedName("readyInMinutes")
    override val prepTime: Int,
    @SerializedName("extendedIngredients")
    override val ingredients: List<Ingredient>,
) : RecipeInterface

data class Ingredient(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("name")
    override val name: String,
    @SerializedName("amount")
    override val amount: Double,
    @SerializedName("unit")
    override val unit: String,
    @SerializedName("original")
    override val original: String,
) : IngredientInterface

data class PrepPart(
    @SerializedName("name")
    override val name: String,
    @SerializedName("steps")
    override val steps: List<PrepStep>,
) : PrepPartInterface

data class PrepStep(
    @SerializedName("number")
    override val number: Int,
    override val step: String,
    @SerializedName("ingredients")
    override val ingredients: List<Ingredient>,
    @SerializedName("equipment")
    override val equipment: List<Equipment>,
) : PrepStepInterface

data class Equipment(
    @SerializedName("id")
    override val id: Int,
    @SerializedName("name")
    override val name: String,
) : EquipmentInterface
