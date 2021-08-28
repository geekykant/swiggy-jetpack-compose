package com.paavam.swiggyapp.core.data.restaurant.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.restaurant.model.Restaurant
import com.paavam.swiggyapp.core.data.restaurant.model.RestaurantFoodModel
import javax.inject.Singleton

/**
 * Repository for Restaurant Screen.
 */
@Singleton
interface SwiggyRestaurantRepository {
    suspend fun fetchThisRestaurant(): ResponseResult<Restaurant>

    suspend fun fetchThisRestaurantFoods(): ResponseResult<RestaurantFoodModel>

    suspend fun fetchRestaurantsList(): ResponseResult<List<Restaurant>>
}