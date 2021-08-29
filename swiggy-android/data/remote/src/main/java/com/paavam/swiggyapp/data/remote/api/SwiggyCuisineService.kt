package com.paavam.swiggyapp.data.remote.api

import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.Cuisine
import com.paavam.swiggyapp.data.remote.model.response.CuisineResponse
import com.paavam.swiggyapp.data.remote.model.response.State
import retrofit2.Response
import retrofit2.http.POST

interface SwiggyCuisineService{

    @POST("")
    suspend fun fetchPopularCuisines(): Response<CuisineResponse<List<Cuisine>>> =
        Response.success(CuisineResponse(State.SUCCESS, "Success", PreviewData.preparePopularCurations()))

}