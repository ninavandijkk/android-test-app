package com.example.testapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.testapplication.EquipmentInterface
import com.example.testapplication.IngredientInterface
import com.example.testapplication.PrepPartInterface
import com.example.testapplication.PrepStepInterface
import com.example.testapplication.RecipeInterface

@Entity(
    tableName = "recipes",
    indices = [Index(value = ["iid"], unique = true)]
)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    override val iid: Int,
    override val title: String,
    @ColumnInfo(name = "image_url")
    override val imageUrl: String?,
    @ColumnInfo(name = "nr_of_servings")
    override val nrOfServings: Int?,
    @ColumnInfo(name = "is_vegan")
    override val isVegan: Boolean,
    @ColumnInfo(name = "is_vegetarian")
    override val isVegetarian: Boolean,
    @ColumnInfo(name = "prep_time")
    override val prepTime: Int,
    override val ingredients: List<Ingredient>,
    val steps: List<PrepPart?>
) : RecipeInterface

@Entity(
    tableName = "ingredients",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = arrayOf("iid"),
            childColumns = arrayOf("recipe_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PrepStep::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("recipe_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val iid: Int,
    @ColumnInfo(name = "recipe_id")
    val recipeId: Int,
    override val name: String,
    override val amount: Double,
    override val unit: String,
    override val original: String,
) : IngredientInterface

@Entity(
    tableName = "prep_parts",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = arrayOf("iid"),
            childColumns = arrayOf("recipe_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class PrepPart(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "recipe_id")
    val recipeId: Int,
    override val name: String,
    override val steps: List<PrepStep>
) : PrepPartInterface

@Entity(
    tableName = "prep_steps",
    foreignKeys = [
        ForeignKey(
            entity = PrepPart::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("prep_part_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class PrepStep(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "prep_part_id")
    val prepPartId: Int,
    override val number: Int,
    override val step: String,
    override val ingredients: List<Ingredient>,
    override val equipment: List<Equipment>,
) : PrepStepInterface

@Entity(
    tableName = "equipments",
    foreignKeys = [
        ForeignKey(
            entity = PrepStep::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("step_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Equipment(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val iid: Int,
    @ColumnInfo("step_id")
    val stepId: Int,
    override val name: String,
) : EquipmentInterface
