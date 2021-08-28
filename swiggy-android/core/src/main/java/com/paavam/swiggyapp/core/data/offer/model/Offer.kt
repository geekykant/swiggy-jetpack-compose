package com.paavam.swiggyapp.core.data.offer.model

data class Offer(
    val iconUrl: String? = null,
    val discountPercentage: Int,
    val upToLimit: Int?,
    val offerCode: String,
    val minLimit: Int,
    val offerType: OfferSnackType = OfferSnackType.BASIC
) {
    fun offerMessage(): String {
        upToLimit?.let {
            return "$discountPercentage% off upto ₹$it".uppercase()
        }
        return "$discountPercentage% off | No upper limit".uppercase()
    }

    fun shortCodeInfo(): String {
        return "Use $offerCode  | Above ₹$minLimit".uppercase()
    }
}

enum class OfferSnackType {
    FLAT_DEAL, BASIC, INVERT_BASIC
}