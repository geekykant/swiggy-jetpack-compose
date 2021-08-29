package com.paavam.swiggyapp.data.remote.api

import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.Food
import com.paavam.swiggyapp.data.remote.model.response.CartResponse
import com.paavam.swiggyapp.data.remote.model.response.State
import retrofit2.Response
import retrofit2.http.POST

interface SwiggyCartService {

    @POST("")
    suspend fun fetchUsersCartFoods(): Response<CartResponse<List<Food>>> =
        Response.success(CartResponse(State.SUCCESS, "Success", PreviewData.prepareCartFoods()))

    @POST("")
    suspend fun fetchUsersCartFoodById(foodId: String): Response<CartResponse<Food>>

    @POST("")
    suspend fun addUsersCartFood(food: Food): Response<CartResponse<String>>

    @POST("")
    suspend fun updateUsersCartFood(foodId: Long, food: Food): Response<CartResponse<String>>

    @POST("")
    suspend fun addUsersCartFoods(foods: List<Food>): Response<CartResponse<String>>

    @POST("")
    suspend fun deleteCartItem(foodId: Long): Response<CartResponse<String>>

}