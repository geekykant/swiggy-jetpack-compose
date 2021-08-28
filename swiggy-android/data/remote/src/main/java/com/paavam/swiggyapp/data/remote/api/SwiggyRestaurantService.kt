package com.paavam.swiggyapp.data.remote.api

import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.restaurant.model.Restaurant
import com.paavam.swiggyapp.core.data.restaurant.model.RestaurantFoodModel
import com.paavam.swiggyapp.data.remote.model.response.RestaurantResponse
import com.paavam.swiggyapp.data.remote.model.response.State
import retrofit2.Response
import retrofit2.http.GET

interface SwiggyRestaurantService {

    @GET("")
    suspend fun fetchThisRestaurant(): Response<RestaurantResponse<Restaurant>> =
        Response.success(RestaurantResponse(State.SUCCESS, "", PreviewData.prepareARestaurant()))

    @GET("")
    suspend fun fetchThisRestaurantFoods(): Response<RestaurantResponse<RestaurantFoodModel>> =
        Response.success(
            RestaurantResponse(
                State.SUCCESS,
                "",
                PreviewData.prepareAllRestaurantFoods()
            )
        )

    @GET("")
    suspend fun fetchRestaurantsList(): Response<RestaurantResponse<List<Restaurant>>> =
        Response.success(
            RestaurantResponse(
                State.SUCCESS,
                "",
                PreviewData.prepareRestaurants()
            )
        )
}