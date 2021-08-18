package com.paavam.data.dao

import com.paavam.data.database.tables.Offers
import com.paavam.data.database.tables.RestaurantWithOffers
import com.paavam.data.database.tables.Restaurants
import com.paavam.data.entity.EntityOffer
import com.paavam.data.entity.EntityRestaurant
import com.paavam.data.entity.EntityRestaurantWithOffer
import com.paavam.data.model.Offer
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class RestaurantWithOffersDao @Inject constructor() {
    fun addExistingOfferToRestaurant(restaurantId: String, offerId: String) = transaction {
        EntityRestaurantWithOffer.new {
            this.restaurant = EntityRestaurant(EntityID(restaurantId.toLong(), Restaurants))
            this.offer = EntityOffer(EntityID(offerId.toLong(), Offers))
        }.id.toString()
    }

    fun addOfferToRestaurantUsingEntity(entityRestaurant: EntityRestaurant, entityOffer: EntityOffer) = transaction {
        EntityRestaurantWithOffer.new {
            this.restaurant = entityRestaurant
            this.offer = entityOffer
        }.id.toString()
    }

    fun getAllOffersOfRestaurant(restaurantId: String): List<Offer> = transaction {
        EntityRestaurantWithOffer.find {
            (RestaurantWithOffers.restaurant eq restaurantId.toLong())
        }.toList().map { Offer.fromEntity(it.offer) }
    }

    fun deleteOfferOfRestaurant(restaurantId: String, offerId: String): Boolean = transaction {
        val id = EntityRestaurantWithOffer.find {
            (RestaurantWithOffers.restaurant eq restaurantId.toLong()) and (RestaurantWithOffers.offer eq offerId.toLong())
        }.firstOrNull()

        id?.run {
            delete()
            return@transaction true
        }
        return@transaction false
    }

    fun deleteAllOffersOfRestaurant(restaurantId: String) = transaction {
//        EntityRestaurantWithOffer.find {
//            (RestaurantWithOffers.restaurant eq restaurantId.toLong())
//        }.toList().forEach { it.delete() }

        RestaurantWithOffers.deleteWhere {
            (RestaurantWithOffers.restaurant eq restaurantId.toLong())
        }
        return@transaction true
    }

    fun isOfferLinkedToRestaurant(restaurantId: String, offerId: String): Boolean {
        return EntityRestaurantWithOffer.find {
            (RestaurantWithOffers.restaurant eq restaurantId.toLong()) and (RestaurantWithOffers.offer eq offerId.toLong())
        }.firstOrNull() != null
    }

    fun isExist(offerId: String): Boolean {
        return EntityRestaurantWithOffer.findById(offerId.toLong()) != null
    }

}
