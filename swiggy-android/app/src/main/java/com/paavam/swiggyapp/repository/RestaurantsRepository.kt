package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.restaurant.model.Restaurant
import com.paavam.swiggyapp.core.data.restaurant.model.RestaurantFoodModel
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