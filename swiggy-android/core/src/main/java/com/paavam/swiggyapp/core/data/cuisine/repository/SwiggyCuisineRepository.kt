package com.paavam.swiggyapp.core.data.cuisine.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.cuisine.model.Cuisine
import javax.inject.Singleton

@Singleton
interface SwiggyCuisineRepository {

    suspend fun fetchPopularCuisines(): ResponseResult<List<Cuisine>>

}