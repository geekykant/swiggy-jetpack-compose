package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.cuisine.model.Cuisine
import com.paavam.swiggyapp.core.data.food.model.Food
import com.paavam.swiggyapp.core.data.message.model.HelloBar
import com.paavam.swiggyapp.core.data.message.model.QuickTile
import com.paavam.swiggyapp.core.data.restaurant.model.Restaurant
import com.paavam.swiggyapp.core.data.restaurant.model.RestaurantFoodModel
import com.paavam.swiggyapp.core.data.user.model.UserAddress
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

    fun fetchUsersAddress(): ResponseResult<List<UserAddress>> =
        ResponseResult.success(PreviewData.prepareUsersAddresses())
}