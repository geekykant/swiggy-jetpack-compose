package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.HelloBar
import com.paavam.swiggyapp.core.data.model.QuickTile
import com.paavam.swiggyapp.core.data.repository.SwiggyPropsRepository
import com.paavam.swiggyapp.data.remote.api.SwiggyPropsService
import com.paavam.swiggyapp.data.remote.model.response.State
import com.paavam.swiggyapp.data.remote.util.getResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SwiggyRemotePropsRepository @Inject internal constructor(
    private val swiggyPropsService: SwiggyPropsService
) : SwiggyPropsRepository {

    override fun fetchTilesContent(): Flow<ResponseResult<List<QuickTile>>> = flow {
        val cartResponse = swiggyPropsService.fetchTilesContent().getResponse()
        val state = when (cartResponse.status) {
            State.SUCCESS -> ResponseResult.success(cartResponse.data)
            else -> ResponseResult.error(cartResponse.message)
        }
        emit(state)
    }.catch { emit(ResponseResult.error("Unable to fetch quicktiles list")) }

    override fun fetchHelloBarContent(): Flow<ResponseResult<List<HelloBar>>> = flow {
        val cartResponse = swiggyPropsService.fetchHelloBarContent().getResponse()
        val state = when (cartResponse.status) {
            State.SUCCESS -> ResponseResult.success(cartResponse.data)
            else -> ResponseResult.error(cartResponse.message)
        }
        emit(state)
    }.catch { emit(ResponseResult.error("Unable to fetch hellobar list")) }
}