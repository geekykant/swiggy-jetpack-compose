package com.paavam.swiggyapp.core.data.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Cuisine
import com.paavam.swiggyapp.core.data.model.HelloBar
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface SwiggyCuisineRepository {

    fun fetchPopularCuisines(): Flow<ResponseResult<List<Cuisine>>>

}