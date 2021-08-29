package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Food
import com.paavam.swiggyapp.core.data.repository.SwiggyCartRepository
import com.paavam.swiggyapp.data.remote.api.SwiggyCartService
import com.paavam.swiggyapp.data.remote.model.response.State
import com.paavam.swiggyapp.data.remote.util.getResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SwiggyRemoteCartRepository @Inject internal constructor(
    private val swiggyCartService: SwiggyCartService
) : SwiggyCartRepository {

    override fun fetchUsersCartFoods(): Flow<ResponseResult<List<Food>>> = flow {
        val cartResponse = swiggyCartService.fetchUsersCartFoods().getResponse()
        val state = when (cartResponse.status) {
            State.SUCCESS -> ResponseResult.success(cartResponse.data)
            else -> ResponseResult.error(cartResponse.message)
        }
        emit(state)
    }.catch { emit(ResponseResult.error("Unable to get users by id")) }

    override fun fetchUsersCartFoodById(foodId: String): Flow<ResponseResult<Food>> = flow {
        runCatching {
            val response = swiggyCartService.fetchUsersCartFoodById(foodId).getResponse()
            when (response.status) {
                State.SUCCESS -> ResponseResult.success(response.data)
                else -> ResponseResult.error(response.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }

    override suspend fun addUsersCartFood(food: Food): ResponseResult<String> {
        return runCatching {
            val response = swiggyCartService.addUsersCartFood(food).getResponse()
            when (response.status) {
                State.SUCCESS -> ResponseResult.success(response.data)
                else -> ResponseResult.error(response.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }

    override suspend fun updateUsersCartFood(foodId: Long, food: Food): ResponseResult<String> {
        return runCatching {
            val response = swiggyCartService.updateUsersCartFood(foodId, food).getResponse()
            when (response.status) {
                State.SUCCESS -> ResponseResult.success(response.data)
                else -> ResponseResult.error(response.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }

    override suspend fun addUsersCartFoods(foods: List<Food>): ResponseResult<String> {
        return runCatching {
            val response = swiggyCartService.addUsersCartFoods(foods).getResponse()
            when (response.status) {
                State.SUCCESS -> ResponseResult.success("Success")
                else -> ResponseResult.error(response.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }

    override suspend fun deleteCartItem(foodId: Long): ResponseResult<String> {
        return runCatching {
            val response = swiggyCartService.deleteCartItem(foodId).getResponse()
            when (response.status) {
                State.SUCCESS -> ResponseResult.success(foodId.toString())
                else -> ResponseResult.error(response.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }
}