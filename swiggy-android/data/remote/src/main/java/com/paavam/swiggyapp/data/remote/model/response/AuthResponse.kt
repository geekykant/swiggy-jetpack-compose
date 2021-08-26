package com.paavam.swiggyapp.data.remote.model.response

data class AuthResponse(
    override val status: State,
    override val message: String,
    val token: String?
) : BaseResponse