package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.cuisine.model.Cuisine
import com.paavam.swiggyapp.core.data.cuisine.repository.SwiggyCuisineRepository
import com.paavam.swiggyapp.data.remote.api.SwiggyCuisineService
import com.paavam.swiggyapp.data.remote.model.response.State
import com.paavam.swiggyapp.data.remote.util.getResponse
import javax.inject.Inject

class SwiggyRemoteCuisineRepository @Inject internal constructor(
    private val swiggyCuisineService: SwiggyCuisineService
) : SwiggyCuisineRepository {

    override suspend fun fetchPopularCuisines(): ResponseResult<List<Cuisine>> {
        return runCatching {
            val cartResponse = swiggyCuisineService.fetchPopularCuisines().getResponse()
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