package com.paavam.swiggyapp.core.data.model

data class UserAddress(
    val addressId: Int,
    val fullAddress: String,
    val type: AddressType,
    val friendlyLabel: String,
    val landmark: String? = null
)

enum class AddressType {
    HOME, OTHER
}