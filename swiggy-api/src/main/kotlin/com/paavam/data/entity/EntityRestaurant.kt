package com.paavam.data.entity

import com.paavam.data.database.tables.RestaurantWithOffers
import com.paavam.data.database.tables.Restaurants
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

    var allOffer by EntityOffer via RestaurantWithOffers
}