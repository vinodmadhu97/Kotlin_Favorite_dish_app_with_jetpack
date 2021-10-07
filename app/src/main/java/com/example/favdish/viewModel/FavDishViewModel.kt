package com.example.favdish.viewModel

import androidx.lifecycle.*
import com.example.favdish.model.databse.FavDishRepository
import com.example.favdish.model.entities.FavDish
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FavDishViewModel(private val repository: FavDishRepository): ViewModel() {

    fun insert(dish:FavDish) = viewModelScope.launch {
        repository.insertFavDishData(dish)
    }

    val allDishesList : LiveData<List<FavDish>> = repository.allDishesList.asLiveData()

    //UPDATE FAV DISH DATA
    fun update(dish: FavDish) = viewModelScope.launch {
        repository.updateFavDishData(dish)
    }

    val favoriteDishes : LiveData<List<FavDish>> = repository.favoriteDishes.asLiveData()

    fun delete(dish: FavDish) = viewModelScope.launch {
        repository.deleteFavDishData(dish)
    }

    fun getFilteredList(value : String): LiveData<List<FavDish>> = repository.filteredListDish(value).asLiveData()
}


class FavDishViewModelFactory(private val favDishRepository: FavDishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        if (modelClass.isAssignableFrom(FavDishViewModel::class.java)){

            @Suppress("UNCHECKED_CAST")
            return FavDishViewModel(favDishRepository) as T
        }
        throw IllegalArgumentException("unknown view model class")
    }



}