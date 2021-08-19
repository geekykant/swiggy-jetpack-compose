package com.paavam.swiggy.data.entity

import com.paavam.swiggy.data.database.tables.RestaurantWithOffers
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EntityRestaurantWithOffer(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<EntityRestaurantWithOffer>(RestaurantWithOffers)

    var restaurant by EntityRestaurant referencedOn RestaurantWithOffers.restaurant
    var offer by EntityOffer referencedOn RestaurantWithOffers.offer
}