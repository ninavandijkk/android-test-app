package com.example.testapplication

import android.app.Application
import com.example.testapplication.data.AppContainer
import com.example.testapplication.data.AppDataContainer

class RecipeApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
