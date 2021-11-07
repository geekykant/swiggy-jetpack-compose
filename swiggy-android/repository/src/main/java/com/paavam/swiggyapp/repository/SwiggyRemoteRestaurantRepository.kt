package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Restaurant
import com.paavam.swiggyapp.core.data.model.RestaurantFoodModel
import com.paavam.swiggyapp.core.data.repository.SwiggyRestaurantRepository
import com.paavam.swiggyapp.data.remote.api.SwiggyRestaurantService
import com.paavam.swiggyapp.data.remote.model.response.State
import com.paavam.swiggyapp.data.remote.util.getResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SwiggyRemoteRestaurantRepository @Inject internal constructor(
    private val swiggyRestaurantService: SwiggyRestaurantService
) : SwiggyRestaurantRepository {

    override fun fetchThisRestaurant(): Flow<ResponseResult<Restaurant>> = flow {
        val restaurantResponse = swiggyRestaurantService.fetchThisRestaurant().getResponse()
        val state = when (restaurantResponse.status) {
            State.SUCCESS -> ResponseResult.success(restaurantResponse.data)
            else -> ResponseResult.error(restaurantResponse.message)
        }
        emit(state)
    }.catch { emit(ResponseResult.error("Unable to fetch restaurant")) }

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

    override fun fetchRestaurantsList(): Flow<ResponseResult<List<Restaurant>>> = flow {
        val restaurantResponse =
            swiggyRestaurantService.fetchRestaurantsList().getResponse()
        val state = when (restaurantResponse.status) {
            State.SUCCESS -> ResponseResult.success(restaurantResponse.data)
            else -> ResponseResult.error(restaurantResponse.message)
        }
        emit(state)
    }.catch { emit(ResponseResult.error("Unable to fetch restaurants list")) }
}