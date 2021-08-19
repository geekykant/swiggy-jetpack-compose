package com.paavam.swiggy.data.entity

import com.paavam.swiggy.data.database.tables.RestaurantWithOffers
import com.paavam.swiggy.data.database.tables.Restaurants
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EntityRestaurant(restaurantId: EntityID<Long>) : LongEntity(restaurantId) {
    companion object : LongEntityClass<EntityRestaurant>(Restaurants)

    var name by Restaurants.name
    var dishTagline by Restaurants.dishTagline
    var location by Restaurants.location
    var distance by Restaurants.distance
    var rating by Restaurants.rating
    var distanceTimeMinutes by Restaurants.distanceTimeMinutes
    var averagePricingForTwo by Restaurants.averagePricingForTwo
    var imageUrl by Restaurants.imageUrl
    var isBestSafety by Restaurants.isBestSafety
    var isShopClosed by Restaurants.isShopClosed
    var offerSnackType by Restaurants.offerSnackType

    var allOffer by EntityOffer via RestaurantWithOffers

//    fun toEntity(restaurant: Restaurant): EntityRestaurant = EntityRestaurant(
//        id
//    ).apply {
//        name = restaurant.name
//        dishTagline = restaurant.dishTagline
//        location = restaurant.location
//        distance = restaurant.distance
//        rating = restaurant.rating
//        distanceTimeMinutes = restaurant.distanceTimeMinutes
//        averagePricingForTwo = restaurant.averagePricingForTwo
//        imageUrl = restaurant.imageUrl
//        isBestSafety = restaurant.isBestSafety
//        isShopClosed = restaurant.isShopClosed
//    }
}