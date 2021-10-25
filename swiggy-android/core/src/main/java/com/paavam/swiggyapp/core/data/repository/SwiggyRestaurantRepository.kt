package com.paavam.swiggyapp.core.data.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Restaurant
import com.paavam.swiggyapp.core.data.model.RestaurantFoodModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

/**
 * Repository for Restaurant Screen.
 */
@Singleton
interface SwiggyRestaurantRepository {
    suspend fun fetchThisRestaurant(): ResponseResult<Restaurant>

    suspend fun fetchThisRestaurantFoods(): ResponseResult<RestaurantFoodModel>

    fun fetchRestaurantsList(): Flow<ResponseResult<List<Restaurant>>>
}