package com.paavam.swiggyapp.data.remote.model.response

data class CuisineResponse<T>(
    override val status: State,
    override val message: String,
    val data: T,
) : BaseResponse