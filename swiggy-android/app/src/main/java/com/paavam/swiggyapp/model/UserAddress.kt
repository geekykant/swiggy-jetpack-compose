package com.paavam.swiggyapp.model

data class UserAddress(
    val id: Int,
    val fullAddress: String,
    val type: AddressType,
    val friendlyLabel: String,
    val landmark: String? = null
)

enum class AddressType {
    HOME, OTHER
}