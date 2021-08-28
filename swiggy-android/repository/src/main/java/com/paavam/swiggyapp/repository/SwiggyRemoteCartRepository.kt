package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.cart.repository.SwiggyCartRepository
import com.paavam.swiggyapp.core.data.food.model.Food
import com.paavam.swiggyapp.data.remote.api.SwiggyCartService
import com.paavam.swiggyapp.data.remote.model.response.State
import com.paavam.swiggyapp.data.remote.util.getResponse
import javax.inject.Inject

class SwiggyRemoteCartRepository @Inject internal constructor(
    private val swiggyCartService: SwiggyCartService
) : SwiggyCartRepository {
    override suspend fun fetchUsersCartFoods(): ResponseResult<List<Food>> {
        return runCatching {
            val cartResponse = swiggyCartService.fetchUsersCartFoods().getResponse()
            when (cartResponse.status) {
                State.SUCCESS -> ResponseResult.success(cartResponse.data)
                else -> ResponseResult.error(cartResponse.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }
}