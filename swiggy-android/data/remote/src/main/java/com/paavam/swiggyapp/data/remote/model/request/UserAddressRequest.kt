package com.paavam.swiggyapp.data.remote.model.request

data class UserAddressRequest(
    val id: String? = null,
    val fullAddress: String,
    val type: AddressType,
    val friendlyLabel: String,
    val landmark: String? = null
)

enum class AddressType {
    HOME, OTHER
}