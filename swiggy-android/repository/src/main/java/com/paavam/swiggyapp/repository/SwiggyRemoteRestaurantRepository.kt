package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.restaurant.model.Restaurant
import com.paavam.swiggyapp.core.data.restaurant.model.RestaurantFoodModel
import com.paavam.swiggyapp.core.data.restaurant.repository.SwiggyRestaurantRepository
import com.paavam.swiggyapp.data.remote.api.SwiggyRestaurantService
import com.paavam.swiggyapp.data.remote.model.response.State
import com.paavam.swiggyapp.data.remote.util.getResponse
import javax.inject.Inject

class SwiggyRemoteRestaurantRepository @Inject internal constructor(
    private val swiggyRestaurantService: SwiggyRestaurantService
) : SwiggyRestaurantRepository {

    override suspend fun fetchThisRestaurant(): ResponseResult<Restaurant> {
        return runCatching {
            val restaurantResponse = swiggyRestaurantService.fetchThisRestaurant().getResponse()
            when (restaurantResponse.status) {
                State.SUCCESS -> ResponseResult.success(restaurantResponse.data)
                else -> ResponseResult.error(restaurantResponse.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }

    override suspend fun fetchThisRestaurantFoods(): ResponseResult<RestaurantFoodModel> {
        return runCatching {
            val restaurantResponse =
                swiggyRestaurantService.fetchThisRestaurantFoods().getResponse()
            when (restaurantResponse.status) {
                State.SUCCESS -> ResponseResult.success(restaurantResponse.data)
                else -> ResponseResult.error(restaurantResponse.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }

    override suspend fun fetchRestaurantsList(): ResponseResult<List<Restaurant>> {
        return runCatching {
            val restaurantResponse =
                swiggyRestaurantService.fetchRestaurantsList().getResponse()
            when (restaurantResponse.status) {
                State.SUCCESS -> ResponseResult.success(restaurantResponse.data)
                else -> ResponseResult.error(restaurantResponse.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }
}