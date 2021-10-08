package com.example.favdish.model.network

import com.example.favdish.model.entities.RandomDish
import com.example.favdish.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomDishApi {

    @GET(Constants.API_END_POINT)
    fun getRandomDishes(
        @Query(Constants.API_KEY) apikey : String,
        @Query(Constants.LIMIT_LICENSE) limitLicense : Boolean,
        @Query(Constants.TAGS) tags : String,
        @Query(Constants.API_KEY) number : Int
    ): Single<RandomDish.Recipes>
}