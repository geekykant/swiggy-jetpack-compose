package com.paavam.swiggy.data.database.tables

import com.paavam.swiggy.data.database.tables.enums.OfferSnackTypes
import org.jetbrains.exposed.dao.id.LongIdTable
import java.awt.SystemColor.text
import java.util.Collections.enumeration

object Restaurants : LongIdTable("restaurants", columnName = "restaurant_id") {
    val name = text("restaurant")
    val dishTagline = text("dish_tagline")
    val location = text("location")
    val distance = text("distance").nullable()
    val rating = float("rating").nullable()
    val distanceTimeMinutes = integer("distance_time_minutes").nullable()
    val averagePricingForTwo = integer("average_pricing_for_two")
    val imageUrl = text("image_url").nullable()
    val isBestSafety = bool("is_best_safety")
    val isShopClosed = bool("is_shop_closed")
    val offerSnackType = enumeration("offer_snack_type", OfferSnackTypes::class).default(OfferSnackTypes.BASIC)
}