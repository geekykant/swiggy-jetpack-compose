package com.paavam.swiggyapp.data.remote.model.response

data class UserAddressResponse(
    override val status: State,
    override val message: String,
//    val data: List<>,
    val token: String?
) : BaseResponse