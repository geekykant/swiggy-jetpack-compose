package com.example.swiggyapp.data

enum class OfferSnackType {
    FLAT_DEAL, BASIC, INVERT_BASIC
}

data class OfferSnack(
    val message: String,
    val offerType: OfferSnackType
)