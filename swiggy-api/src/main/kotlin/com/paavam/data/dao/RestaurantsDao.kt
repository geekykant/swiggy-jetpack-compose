package com.paavam.data.dao

import com.paavam.data.entity.EntityRestaurant
import com.paavam.data.model.Restaurant
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class RestaurantsDao @Inject constructor() {
    fun addNewRestaurant(restaurant: Restaurant): String = transaction {
        EntityRestaurant.new {
            this.name = restaurant.name
            this.dishTagline = restaurant.dishTagline
            this.location = restaurant.location
            this.distance = restaurant.distance
            this.rating = restaurant.rating
            this.distanceTimeMinutes = restaurant.distanceTimeMinutes
            this.averagePricingForTwo = restaurant.averagePricingForTwo
            this.imageUrl = restaurant.imageUrl
            this.isBestSafety = restaurant.isBestSafety
            this.isShopClosed = restaurant.isShopClosed
            this.offerSnackType = restaurant.offerSnackType
        }.id.toString()
    }

    fun getRestaurantById(restaurantId: String): Restaurant? = transaction {
        EntityRestaurant.findById(restaurantId.toLong())
    }?.let { Restaurant.fromEntity(it) }

    fun updateRestaurant(restaurantId: String, restaurant: Restaurant): String = transaction {
        EntityRestaurant[restaurantId.toLong()].apply {
            this.name = restaurant.name
            this.dishTagline = restaurant.dishTagline
            this.location = restaurant.location
            this.distance = restaurant.distance
            this.rating = restaurant.rating
            this.distanceTimeMinutes = restaurant.distanceTimeMinutes
            this.averagePricingForTwo = restaurant.averagePricingForTwo
            this.imageUrl = restaurant.imageUrl
            this.isBestSafety = restaurant.isBestSafety
            this.isShopClosed = restaurant.isShopClosed
            this.offerSnackType = restaurant.offerSnackType
        }.id.toString()
    }

    fun isExist(restaurantId: String): Boolean = transaction {
        EntityRestaurant.findById(restaurantId.toLong()) != null
    }

    fun deleteRestaurant(restaurantId: String): Boolean = transaction {
        val curRestaurant = EntityRestaurant.findById(restaurantId.toLong())
        curRestaurant?.run {
            delete()
            return@transaction true
        }
        return@transaction false
    }
}