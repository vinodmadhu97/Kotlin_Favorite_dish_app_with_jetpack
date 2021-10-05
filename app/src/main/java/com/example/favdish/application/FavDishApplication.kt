package com.example.favdish.application

import android.app.Application
import com.example.favdish.model.databse.FavDishRepository
import com.example.favdish.model.databse.FavDishRoomDatabase

class FavDishApplication: Application() {

    private val database by lazy {
        FavDishRoomDatabase.getDatabase(this )
    }

    val repository by lazy {
        FavDishRepository(database.favDishDao())
    }
}