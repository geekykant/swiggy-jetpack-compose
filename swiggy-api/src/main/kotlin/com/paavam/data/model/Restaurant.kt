package com.paavam.data.model

import com.paavam.data.entity.EntityRestaurant

data class Restaurant(
    val restaurant_id: String,
    val name: String,
    val dishTagline: String,
    val location: String,
    val distance: String,
    val rating: Float?,
    val distanceTimeMinutes: Int,
    val averagePricingForTwo: Int,
    val imageUrl: String,
    val isBestSafety: Boolean,
    val isShopClosed: Boolean
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
            entity.isShopClosed
        )
    }
}