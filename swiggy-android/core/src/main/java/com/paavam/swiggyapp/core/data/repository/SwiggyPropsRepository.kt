package com.paavam.swiggyapp.core.data.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.HelloBar
import com.paavam.swiggyapp.core.data.model.QuickTile
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface SwiggyPropsRepository {

    fun fetchTilesContent(): Flow<ResponseResult<List<QuickTile>>>

    fun fetchHelloBarContent(): Flow<ResponseResult<List<HelloBar>>>

}