package com.paavam.data.model

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

//        fun toEntity(restaurant_id: String, restaurant: Restaurant): EntityRestaurant {
//            return EntityRestaurant(EntityID(restaurant_id.toLong(), Restaurants)).also {
//                it.name = restaurant.name
//                it.dishTagline = restaurant.dishTagline
//                it.location = restaurant.location
//                it.distance = restaurant.distance
//                it.rating = restaurant.rating
//                it.distanceTimeMinutes = restaurant.distanceTimeMinutes
//                it.averagePricingForTwo = restaurant.averagePricingForTwo
//                it.imageUrl = restaurant.imageUrl
//                it.isBestSafety = restaurant.isBestSafety
//                it.isShopClosed = restaurant.isShopClosed
//            }
//    }
    }

    fun isFieldsBlank(): Boolean {
        return (name.isBlank() or dishTagline.isBlank() or location.isBlank())
    }
}