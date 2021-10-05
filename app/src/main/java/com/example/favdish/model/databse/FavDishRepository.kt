package com.example.favdish.model.databse

import androidx.annotation.WorkerThread
import com.example.favdish.model.entities.FavDish

class FavDishRepository(private val favDishDao: FavDishDao) {

    @WorkerThread
    suspend fun insertFavDishData(favDish: FavDish){
        favDishDao.insertFavDishesDetails(favDish)
    }

}