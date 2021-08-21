package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.model.Food
import javax.inject.Inject

/**
 * Repository for Users Cart
 */
class CartRepository @Inject constructor(
    private val swiggyService: SwiggyService
) {
    suspend fun fetchUsersCart(): ResponseResult<List<Food>> =
        swiggyService.fetchUsersCartFoods()
}