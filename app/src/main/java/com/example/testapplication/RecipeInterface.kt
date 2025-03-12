package com.example.testapplication

interface RecipeInterface {
    val iid: Int
    val title: String
    val imageUrl: String?
    val nrOfServings: Int?
    val isVegan: Boolean
    val isVegetarian: Boolean
    val prepTime: Int
    val ingredients: List<IngredientInterface>
}


interface IngredientInterface {
    val id: Int
    val name: String
    val amount: Double
    val unit: String
    val original: String
}

interface PrepPartInterface {
    val name: String
    val steps: List<PrepStepInterface>
}

interface PrepStepInterface {
    val number: Int
    val step: String
    val ingredients: List<IngredientInterface>
    val equipment: List<EquipmentInterface>
}

interface EquipmentInterface {
    val id: Int
    val name: String
}
