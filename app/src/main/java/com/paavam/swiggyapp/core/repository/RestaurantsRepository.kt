package com.paavam.swiggyapp.core.repository

import com.paavam.swiggyapp.data.Restaurant
import com.paavam.swiggyapp.ui.restaurant.RestaurantFoodModel
import javax.inject.Singleton

/**
 * Repository for Restaurant Screen.
 */
@Singleton
interface RestaurantsRepository {
    suspend fun fetchThisRestaurant(): ResponseResult<Restaurant> =
        SwiggyService.fetchThisRestaurant()

    suspend fun fetchThisRestaurantFoods(): ResponseResult<RestaurantFoodModel> =
        SwiggyService.fetchThisRestaurantFoods()
}