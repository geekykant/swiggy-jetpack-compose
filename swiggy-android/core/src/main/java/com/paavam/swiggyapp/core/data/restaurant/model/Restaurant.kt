package com.paavam.swiggyapp.core.data.restaurant.model

import com.paavam.swiggyapp.core.data.offer.model.Offer

data class Restaurant(
    val restaurantId: Long,
    val name: String,
    val dishTagline: String,
    val location: String,
    val distance: String,
    val rating: Float,
    val distanceTimeMinutes: Int,
    val averagePricingForTwo: Int,
    val imageUrl: String?,
    val allOffers: List<Offer>?,
    val isBestSafety: Boolean = false,
    val isShopClosed: Boolean = false
) {
    fun getLocationTagline(): String = "$location | $distance kms"

    fun getMaxOffer(): Offer? = allOffers?.maxByOrNull { it.discountPercentage }
    fun getMaxOfferSnackMessage(): String = "${getMaxOffer()?.discountPercentage}% OFF"
}


