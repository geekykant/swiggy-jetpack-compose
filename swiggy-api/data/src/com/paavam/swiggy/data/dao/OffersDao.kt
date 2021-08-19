package com.paavam.swiggy.data.dao

import com.paavam.swiggy.data.entity.EntityOffer
import com.paavam.swiggy.data.model.Offer
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class OffersDao @Inject constructor() {
    fun addNewOffer(offer: Offer): String = transaction {
        EntityOffer.new {
            this.iconUrl = offer.iconUrl
            this.discountPercentage = offer.discountPercentage
            this.upToLimit = offer.upToLimit
            this.offerCode = offer.offerCode
            this.minLimit = offer.minLimit
        }.id.toString()
    }

    fun updateOffer(offerId: String, offer: Offer): String = transaction {
        EntityOffer[offerId.toLong()].apply {
            this.iconUrl = offer.iconUrl
            this.discountPercentage = offer.discountPercentage
            this.upToLimit = offer.upToLimit
            this.offerCode = offer.offerCode
            this.minLimit = offer.minLimit
        }.id.toString()
    }

    fun isExist(offerId: String): Boolean = transaction {
        EntityOffer.findById(offerId.toLong()) != null
    }

    fun getOfferById(offerId: String): Offer? = transaction {
        EntityOffer.findById(offerId.toLong())
    }?.let { Offer.fromEntity(it) }

    fun deleteOffer(offerId: String): Boolean = transaction {
        val curOffer = EntityOffer.findById(offerId.toLong())
        curOffer?.run {
            delete()
            return@transaction true
        }
        return@transaction false
    }

    fun getAllOffers(): List<Offer> = transaction {
        EntityOffer.all()
            .map { Offer.fromEntity(it) }.toList()
    }
}