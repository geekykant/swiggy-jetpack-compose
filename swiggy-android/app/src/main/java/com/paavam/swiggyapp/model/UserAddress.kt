package com.paavam.swiggyapp.model

data class UserAddress(
    val id: Int,
    val label: String,
    val fullAddress: String,
    val type: AddressType
)

enum class AddressType {
    HOME, OTHER
}