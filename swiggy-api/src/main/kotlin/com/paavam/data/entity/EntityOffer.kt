package com.paavam.data.entity

import com.paavam.data.database.tables.Offers
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EntityOffer(foodId: EntityID<Long>) : LongEntity(foodId) {
    companion object : LongEntityClass<EntityOffer>(Offers)

    var iconUrl by Offers.iconUrl
    var discountPercentage by Offers.discountPercentage
    var upToLimit by Offers.upToLimit
    var offerCode by Offers.offerCode
    var minLimit by Offers.minLimit
}