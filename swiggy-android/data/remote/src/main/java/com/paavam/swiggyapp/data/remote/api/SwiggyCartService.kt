package com.paavam.swiggyapp.data.remote.api

import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.food.model.Food
import com.paavam.swiggyapp.data.remote.model.response.CartResponse
import com.paavam.swiggyapp.data.remote.model.response.State
import retrofit2.Response
import retrofit2.http.POST

interface SwiggyCartService{

    @POST("")
    suspend fun fetchUsersCartFoods(): Response<CartResponse<List<Food>>> =
        Response.success(CartResponse(State.SUCCESS, "Success", PreviewData.prepareCartFoods()))

}