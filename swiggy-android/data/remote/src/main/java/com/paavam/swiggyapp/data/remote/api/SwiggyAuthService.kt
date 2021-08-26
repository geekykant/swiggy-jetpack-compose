package com.paavam.swiggyapp.data.remote.api

import com.paavam.swiggyapp.data.remote.model.request.AuthRequest
import com.paavam.swiggyapp.data.remote.model.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SwiggyAuthService {

    @POST("/auth/register")
    suspend fun register(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST("/auth/login")
    suspend fun login(@Body authRequest: AuthRequest): Response<AuthResponse>
}