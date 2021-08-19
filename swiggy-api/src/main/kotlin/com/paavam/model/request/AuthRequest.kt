package com.paavam.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val mobileNo: String,
    val password: String
)