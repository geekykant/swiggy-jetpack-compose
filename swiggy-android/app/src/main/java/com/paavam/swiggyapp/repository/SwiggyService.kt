package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.PreviewData
import com.paavam.swiggyapp.model.*
import javax.inject.Inject

/**
 * Retrofit interface to server URLs
 */
class SwiggyService @Inject constructor() {

    //    @GET("/")
    suspend fun fetchThisRestaurant(): ResponseResult<Restaurant> =
        ResponseResult.success(PreviewData.prepareARestaurant())

    suspend fun fetchThisRestaurantFoods(): ResponseResult<RestaurantFoodModel> =
        ResponseResult.success(PreviewData.prepareAllRestaurantFoods())

    suspend fun fetchUsersCartFoods(): ResponseResult<List<Food>> =
        ResponseResult.success(PreviewData.prepareCartFoods())

    suspend fun fetchTilesContent(): ResponseResult<List<QuickTile>> =
        ResponseResult.success(PreviewData.prepareTilesContent())

    suspend fun fetchHelloBarContent(): ResponseResult<List<HelloBar>> =
        ResponseResult.success(PreviewData.prepareHelloBarContent())

    suspend fun fetchRestaurantsList(): ResponseResult<List<Restaurant>> =
        ResponseResult.success(PreviewData.prepareRestaurants())

    suspend fun preparePopularCuisines(): ResponseResult<List<Cuisine>> =
        ResponseResult.success(PreviewData.preparePopularCurations())
}