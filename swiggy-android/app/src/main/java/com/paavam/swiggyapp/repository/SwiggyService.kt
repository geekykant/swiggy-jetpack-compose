package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.model.*
import javax.inject.Inject

/**
 * Retrofit interface to server URLs
 */
class SwiggyService @Inject constructor() {

    //    @GET("/")
    suspend fun fetchThisRestaurant(): ResponseResult<Restaurant> =
        ResponseResult.success(com.paavam.swiggyapp.PreviewData.prepareARestaurant())

    suspend fun fetchThisRestaurantFoods(): ResponseResult<RestaurantFoodModel> =
        ResponseResult.success(com.paavam.swiggyapp.PreviewData.prepareAllRestaurantFoods())

    suspend fun fetchTilesContent(): ResponseResult<List<QuickTile>> =
        ResponseResult.success(com.paavam.swiggyapp.PreviewData.prepareTilesContent())

    suspend fun fetchHelloBarContent(): ResponseResult<List<HelloBar>> =
        ResponseResult.success(com.paavam.swiggyapp.PreviewData.prepareHelloBarContent())

    suspend fun fetchRestaurantsList(): ResponseResult<List<Restaurant>> =
        ResponseResult.success(com.paavam.swiggyapp.PreviewData.prepareRestaurants())

    suspend fun preparePopularCuisines(): ResponseResult<List<Cuisine>> =
        ResponseResult.success(com.paavam.swiggyapp.PreviewData.preparePopularCurations())
}