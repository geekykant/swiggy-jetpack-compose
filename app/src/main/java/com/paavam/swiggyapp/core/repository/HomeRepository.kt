package com.paavam.swiggyapp.core.repository

import com.paavam.swiggyapp.data.Cuisine
import com.paavam.swiggyapp.data.HelloBar
import com.paavam.swiggyapp.data.QuickTile
import com.paavam.swiggyapp.data.Restaurant
import javax.inject.Singleton

/**
 * Repository for Home Screen
 */
@Singleton
interface HomeRepository {
    suspend fun fetchTilesContent(): ResponseResult<List<QuickTile>> =
        SwiggyService.fetchTilesContent()

    suspend fun fetchHelloBarContent(): ResponseResult<List<HelloBar>> =
        SwiggyService.fetchHelloBarContent()

    suspend fun fetchRestaurantsList(): ResponseResult<List<Restaurant>> =
        SwiggyService.fetchRestaurantsList()

    suspend fun fetchPopularCuisines(): ResponseResult<List<Cuisine>> =
        SwiggyService.preparePopularCuisines()
}