package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.cuisine.model.Cuisine
import com.paavam.swiggyapp.core.data.message.model.HelloBar
import com.paavam.swiggyapp.core.data.message.model.QuickTile
import com.paavam.swiggyapp.core.data.restaurant.model.Restaurant
import javax.inject.Inject

/**
 * Repository for Home Screen
 */
class HomeRepository @Inject constructor(
    private val swiggyService: SwiggyService
) {
    suspend fun fetchTilesContent(): ResponseResult<List<QuickTile>> =
        swiggyService.fetchTilesContent()

    suspend fun fetchHelloBarContent(): ResponseResult<List<HelloBar>> =
        swiggyService.fetchHelloBarContent()

    suspend fun fetchRestaurantsList(): ResponseResult<List<Restaurant>> =
        swiggyService.fetchRestaurantsList()

    suspend fun fetchPopularCuisines(): ResponseResult<List<Cuisine>> =
        swiggyService.preparePopularCuisines()
}