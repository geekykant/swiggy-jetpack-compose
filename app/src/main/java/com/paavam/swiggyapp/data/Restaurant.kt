package com.paavam.swiggyapp.data

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
    val offerSnack: OfferSnack?,
    val isBestSafety: Boolean = false,
    val isShopClosed: Boolean = false
){
    fun getLocationTagline() = "$location | $distance kms"
}


