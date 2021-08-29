package com.paavam.swiggyapp.core.data.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Cuisine
import javax.inject.Singleton

@Singleton
interface SwiggyCuisineRepository {

    suspend fun fetchPopularCuisines(): ResponseResult<List<Cuisine>>

}