package com.paavam.swiggy.data.model

import com.paavam.swiggy.data.database.tables.enums.OfferSnackTypes
import com.paavam.swiggy.data.entity.EntityRestaurant
import kotlinx.serialization.Serializable
import java.util.Collections.emptyList

@Serializable
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
    val offerSnackType: OfferSnackTypes = OfferSnackTypes.BASIC,
    var offersList: List<Offer> = emptyList()
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
            entity.offerSnackType,
            entity.allOffer.map { Offer.fromEntity(it) }.toList()
        )

        fun isValidEntityIDType(restaurantId: String): Boolean = restaurantId.toLongOrNull() != null
    }

    fun isFieldsBlank(): Boolean {
        return (name.isBlank() or dishTagline.isBlank() or location.isBlank()) or
                OfferSnackTypes.values().contains(offerSnackType)
    }
}