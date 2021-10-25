package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Cuisine
import com.paavam.swiggyapp.core.data.repository.SwiggyCuisineRepository
import com.paavam.swiggyapp.data.remote.api.SwiggyCuisineService
import com.paavam.swiggyapp.data.remote.model.response.State
import com.paavam.swiggyapp.data.remote.util.getResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SwiggyRemoteCuisineRepository @Inject internal constructor(
    private val swiggyCuisineService: SwiggyCuisineService
) : SwiggyCuisineRepository {

    override fun fetchPopularCuisines(): Flow<ResponseResult<List<Cuisine>>> = flow {
        val cartResponse = swiggyCuisineService.fetchPopularCuisines().getResponse()
        val state = when (cartResponse.status) {
            State.SUCCESS -> ResponseResult.success(cartResponse.data)
            else -> ResponseResult.error(cartResponse.message)
        }
        emit(state)
    }.catch { emit(ResponseResult.error("Unable to fetch cuisines list")) }
}