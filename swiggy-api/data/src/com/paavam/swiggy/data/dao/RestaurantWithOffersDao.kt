package com.paavam.swiggy.data.dao

import com.paavam.swiggy.data.database.tables.Offers
import com.paavam.swiggy.data.database.tables.RestaurantWithOffers
import com.paavam.swiggy.data.database.tables.Restaurants
import com.paavam.swiggy.data.entity.EntityOffer
import com.paavam.swiggy.data.entity.EntityRestaurant
import com.paavam.swiggy.data.entity.EntityRestaurantWithOffer
import com.paavam.swiggy.data.model.Offer
import com.paavam.swiggy.data.model.Restaurant
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

    fun getAllRestaurantsWithOffers() = transaction {
        EntityRestaurant
            .all()
            .map { Restaurant.fromEntity(it) }
            .onEach {
                it.offersList = getAllOffersOfRestaurant(it.restaurant_id!!)
            }
    }

    fun getRestaurantWithOffers(restaurantId: String) = transaction {
        EntityRestaurant
            .findById(restaurantId.toLong())
            ?.apply {
                val restaurant = Restaurant.fromEntity(this)
                restaurant.offersList = getAllOffersOfRestaurant(restaurantId)
                return@transaction restaurant
            }
        return@transaction null
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

    fun isExist(offerId: String): Boolean = transaction {
        EntityRestaurantWithOffer.findById(offerId.toLong()) != null
    }

}
