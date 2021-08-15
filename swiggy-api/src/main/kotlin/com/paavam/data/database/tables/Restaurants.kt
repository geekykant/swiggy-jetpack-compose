package com.paavam.data.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object Restaurants : LongIdTable("restaurants", columnName = "restaurant_id") {
    val name = text("restaurant")
    val dishTagline = text("dish_tagline")
    val location = text("location")
    val distance = text("distance")
    val rating = float("rating").nullable()
    val distanceTimeMinutes = integer("distance_time_minutes")
    val averagePricingForTwo = integer("average_pricing_for_two")
    val imageUrl = text("image_url")
    val isBestSafety = bool("is_best_safety")
    val isShopClosed = bool("is_shop_closed")
}