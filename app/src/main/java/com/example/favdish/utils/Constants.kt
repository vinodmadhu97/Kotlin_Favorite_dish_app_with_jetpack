package com.example.favdish.utils

object Constants {
    const val DISH_TYPE = "DishType"
    const val DISH_CATEGORY = "DishCategory"
    const val DISH_COOKING_TIME = "DishCookingTime"
    const val ALL_DISHES = "All Dishes"

    const val DISH_IMAGE_SOURCE_LOCAL = "Local"
    const val DISH_IMAGE_SOURCE_ONLINE = "Online"

    const val EXTRA_DISH_DETAILS = "dishDetails"

    const val API_END_POINT = "/recipes/random"
    const val API_KEY = "apiKey"
    const val LIMIT_LICENSE = "limitLicense"
    const val TAGS = "tags"
    const val NUMBER = "number"

    const val BASE_URL = "https://api.spoonacular.com/"
    const val API_KEY_VALUE = "1d18da579a424add80c151e1ac28dfd2"
    const val LIMIT_LICENSE_VALUE = true
    const val TAGS_VALUE = "vegetarian,dessert"
    const val NUMBER_VALUE = 1

    const val NOTIFICATION_ID = "FavDish_notification_id"
    const val NOTIFICATION_NAME = "FavDish"
    const val NOTIFICATION_CHANNEL = "FavDish_channel_1"


    fun dishTypes():ArrayList<String>{

        val list = ArrayList<String>()
        list.add("Breakfast")
        list.add("Lunch")
        list.add("Dinner")
        list.add("Snacks")
        list.add("Dinner")
        list.add("Salad")
        list.add("Side dish")
        list.add("Dessert")
        list.add("other")

        return list
    }

    fun dishCategories(): ArrayList<String>{
        val list = ArrayList<String>()

        list.add("Pizza")
        list.add("BBQ")
        list.add("bakery")
        list.add("Burger")
        list.add("Cafe")
        list.add("Chicken")
        list.add("Dessert")
        list.add("Drink")
        list.add("Hot Dogs")
        list.add("Juices")
        list.add("Sandwich")
        list.add("Tea & Coffee")
        list.add("Wraps")
        list.add("Other")

        return list
    }

    fun dishCookingTime():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("10")
        list.add("15")
        list.add("20")
        list.add("30")
        list.add("45")
        list.add("50")
        list.add("60")
        list.add("90")
        list.add("120")
        list.add("150")
        list.add("180")

        return list
    }
}