package com.example.testapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Database(
    entities = [
        Recipe::class,
        Ingredient::class,
        PrepStep::class,
        PrepPart::class,
        Equipment::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var Instance: RecipeDatabase? = null

        fun getDataBase(context: Context): RecipeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RecipeDatabase::class.java, "recipe_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun stringToIngredient(value: String?): List<Ingredient> {
        val listType: Type = object : TypeToken<List<Ingredient?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun ingredientToString(list: List<Ingredient?>?): String {
        val gson = Gson()
        val json = gson.toJson(list)
        return json
    }

    @TypeConverter
    fun stringToPrepPart(value: String?): List<PrepPart> {
        val listType: Type = object : TypeToken<List<PrepPart?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun prepPartToString(list: List<PrepPart?>?): String {
        val gson = Gson()
        val json = gson.toJson(list)
        return json
    }

    @TypeConverter
    fun stringToPrepStep(value: String?): List<PrepStep> {
        val listType: Type = object : TypeToken<List<PrepStep?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun prepStepToString(list: List<PrepStep?>?): String {
        val gson = Gson()
        val json = gson.toJson(list)
        return json
    }

    @TypeConverter
    fun stringToEquipment(value: String?): List<Equipment> {
        val listType: Type = object : TypeToken<List<Equipment?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun equipmentToString(list: List<Equipment?>?): String {
        val gson = Gson()
        val json = gson.toJson(list)
        return json
    }
}
