package com.paavam.swiggyapp.core.data.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Food
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface SwiggyCartRepository {

    suspend fun fetchUsersCartFoods(): Flow<ResponseResult<List<Food>>>

    suspend fun fetchUsersCartFoodById(foodId: String): Flow<ResponseResult<Food>>

    suspend fun addUsersCartFood(food: Food): ResponseResult<String>

    suspend fun updateUsersCartFood(foodId: Long, food: Food): ResponseResult<String>

    suspend fun addUsersCartFoods(foods: List<Food>): ResponseResult<String>

    suspend fun deleteCartItem(foodId: Long): ResponseResult<String>

}