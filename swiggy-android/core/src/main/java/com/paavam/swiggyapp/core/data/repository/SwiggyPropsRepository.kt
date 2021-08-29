package com.paavam.swiggyapp.core.data.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.HelloBar
import com.paavam.swiggyapp.core.data.model.QuickTile
import javax.inject.Singleton

@Singleton
interface SwiggyPropsRepository {

    suspend fun fetchTilesContent(): ResponseResult<List<QuickTile>>

    suspend fun fetchHelloBarContent(): ResponseResult<List<HelloBar>>

}