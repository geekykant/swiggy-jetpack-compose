package com.paavam.swiggyapp.core.data.cart.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.food.model.Food
import javax.inject.Singleton

@Singleton
interface SwiggyCartRepository {

    suspend fun fetchUsersCartFoods(): ResponseResult<List<Food>>

}