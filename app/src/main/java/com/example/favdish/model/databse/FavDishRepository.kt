package com.example.favdish.model.databse

import androidx.annotation.WorkerThread
import com.example.favdish.model.entities.FavDish
import kotlinx.coroutines.flow.Flow

class FavDishRepository(private val favDishDao: FavDishDao) {

    @WorkerThread
    suspend fun insertFavDishData(favDish: FavDish){
        favDishDao.insertFavDishesDetails(favDish)
    }

    val allDishesList : Flow<List<FavDish>> = favDishDao.getAllDishesList()

    @WorkerThread
    suspend fun updateFavDishData(favDish: FavDish){
        favDishDao.updateFavDishDetails(favDish)
    }

    val favoriteDishes : Flow<List<FavDish>> = favDishDao.getFavoriteDIshList()

    @WorkerThread
    suspend fun deleteFavDishData(favDish: FavDish){
        favDishDao.deleteFavDishDetails(favDish)
    }

    fun filteredListDish(value : String) : Flow<List<FavDish>> = favDishDao.getFilteredDIshList(value)

}