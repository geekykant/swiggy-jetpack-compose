package com.paavam.swiggyapp.core.repository

import com.paavam.swiggyapp.data.Restaurant
import com.paavam.swiggyapp.ui.restaurant.RestaurantFoodModel
import javax.inject.Inject

/**
 * Repository for Restaurant Screen.
 */
class RestaurantsRepository @Inject constructor(
    private val swiggyService: SwiggyService
) {
    suspend fun fetchThisRestaurant(): ResponseResult<Restaurant> =
        swiggyService.fetchThisRestaurant()

    suspend fun fetchThisRestaurantFoods(): ResponseResult<RestaurantFoodModel> =
        swiggyService.fetchThisRestaurantFoods()
}