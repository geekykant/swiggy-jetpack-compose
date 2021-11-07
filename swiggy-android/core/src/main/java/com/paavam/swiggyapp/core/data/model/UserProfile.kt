package com.paavam.swiggyapp.core.data.model

data class UserProfile(
    val fullName: String,
    val mobileNo: String,
    val addressList: UserAddress,
    val emailId: String
)
