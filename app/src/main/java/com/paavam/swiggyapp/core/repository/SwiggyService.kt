package com.paavam.swiggyapp.core.repository

import com.paavam.swiggyapp.data.Cuisine
import com.paavam.swiggyapp.data.HelloBar
import com.paavam.swiggyapp.data.QuickTile
import com.paavam.swiggyapp.data.Restaurant
import com.paavam.swiggyapp.ui.restaurant.RestaurantFoodModel

//import com.paavam.swiggyapp.ui.restaurant.RestaurantFoodModel

/**
 * Retrofit interface to server URLs
 */
object SwiggyService{

    //    @GET("/")
    suspend fun fetchThisRestaurant(): ResponseResult<Restaurant> =
        ResponseResult.success(PreviewData.prepareARestaurant())

    suspend fun fetchThisRestaurantFoods(): ResponseResult<RestaurantFoodModel> =
        ResponseResult.success(PreviewData.prepareAllRestaurantFoods())

    suspend fun fetchTilesContent(): ResponseResult<List<QuickTile>> =
        ResponseResult.success(PreviewData.prepareTilesContent())

    suspend fun fetchHelloBarContent(): ResponseResult<List<HelloBar>> =
        ResponseResult.success(PreviewData.prepareHelloBarContent())

    suspend fun fetchRestaurantsList(): ResponseResult<List<Restaurant>> =
        ResponseResult.success(PreviewData.prepareRestaurants())

    suspend fun preparePopularCuisines(): ResponseResult<List<Cuisine>> =
        ResponseResult.success(PreviewData.preparePopularCurations())
}