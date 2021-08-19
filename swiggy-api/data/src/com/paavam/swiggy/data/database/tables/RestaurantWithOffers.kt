package com.paavam.swiggy.data.database.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column

object RestaurantWithOffers : LongIdTable("restaurantWithOffers", columnName = "row_id") {
    val restaurant: Column<EntityID<Long>> = reference("restaurant_id", Restaurants).primaryKey(0)
    val offer: Column<EntityID<Long>> = reference("offer_id", Offers).primaryKey(1)
    override val primaryKey = PrimaryKey(restaurant, offer)
}

//class RestaurantWithOffer(id: EntityID<Long>) : LongEntity(id) {
//    companion object : LongEntityClass<RestaurantWithOffer>(RestaurantWithOffers)
//
//    var restaurant by RestaurantWithOffers.restaurant
//}