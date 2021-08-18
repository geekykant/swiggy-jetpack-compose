package com.paavam.data.model

import com.paavam.data.database.tables.enums.OfferSnackTypes
import com.paavam.data.entity.EntityRestaurant

data class Restaurant(
    val restaurant_id: String? = null,
    val name: String,
    val dishTagline: String,
    val location: String,
    val distance: String?,
    val rating: Float?,
    val distanceTimeMinutes: Int?,
    val averagePricingForTwo: Int,
    val imageUrl: String?,
    val isBestSafety: Boolean,
    val isShopClosed: Boolean,
    val offerSnackType: OfferSnackTypes
) {
    companion object {
        fun fromEntity(entity: EntityRestaurant): Restaurant = Restaurant(
            entity.id.value.toString(),
            entity.name,
            entity.dishTagline,
            entity.location,
            entity.distance,
            entity.rating,
            entity.distanceTimeMinutes,
            entity.averagePricingForTwo,
            entity.imageUrl,
            entity.isBestSafety,
            entity.isShopClosed,
            entity.offerSnackType
        )

        fun checkEntityIDType(restaurantId: String): Boolean = restaurantId.toLongOrNull() != null
    }

    fun isFieldsBlank(): Boolean {
        return (name.isBlank() or dishTagline.isBlank() or location.isBlank()) or
                OfferSnackTypes.values().contains(offerSnackType)
    }
}