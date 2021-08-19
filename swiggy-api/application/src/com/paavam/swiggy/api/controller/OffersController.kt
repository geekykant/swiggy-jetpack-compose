package com.paavam.swiggy.api.controller

import com.paavam.swiggy.api.exception.BadRequestException
import com.paavam.swiggy.api.exception.OfferNotFoundException
import com.paavam.swiggy.api.exception.RestaurantNotFoundException
import com.paavam.swiggy.api.model.response.OfferResponse
import com.paavam.swiggy.api.model.response.OffersListResponse
import com.paavam.swiggy.api.model.response.OffersResponse
import com.paavam.swiggy.data.dao.OffersDao
import com.paavam.swiggy.data.model.Offer
import javax.inject.Inject

class OffersController @Inject constructor(
    private val offersDao: OffersDao
) {

    fun getAllOffers(): OffersListResponse {
        val allOffers = offersDao.getAllOffers()
        return OffersListResponse.success(allOffers)
    }

    fun addNewOffer(offer: Offer): OfferResponse {
        return try {
            validateOfferDetailsOrThrowException(offer)

            val newId = offersDao.addNewOffer(offer)
            OfferResponse.success(newId)
        } catch (badEx: BadRequestException) {
            OfferResponse.failed(badEx.message)
        }
    }

    fun updateOffer(offerId: String, offer: Offer): OfferResponse {
        return try {
            validateOfferIdOrThrowException(offerId)
            validateOfferDetailsOrThrowException(offer)

            val newID = offersDao.updateOffer(offerId, offer)
            OfferResponse.success(newID)
        } catch (notFoundEx: OfferNotFoundException) {
            OfferResponse.notFound(notFoundEx.message)
        } catch (badEx: BadRequestException) {
            OfferResponse.failed(badEx.message)
        }
    }

    fun getOffer(offerId: String): OffersResponse {
        return try {
            validateOfferIdOrThrowException(offerId)

            val offer = offersDao.getOfferById(offerId) ?: throw BadRequestException("Error occurred")
            OffersResponse.success(offer)
        } catch (notFoundEx: OfferNotFoundException) {
            OffersResponse.failed(notFoundEx.message)
        }
    }

    fun deleteOffer(offerId: String): OfferResponse {
        return try {
            validateOfferIdOrThrowException(offerId)
            checkOfferIdExistsOrThrowException(offerId)

            if (offersDao.deleteOffer(offerId)) {
                OfferResponse.success(offerId)
            } else {
                OfferResponse.failed("Error Occurred!")
            }
        } catch (notFoundEx: RestaurantNotFoundException) {
            OfferResponse.notFound(notFoundEx.message)
        } catch (badEx: BadRequestException) {
            OfferResponse.failed(badEx.message)
        }
    }

    private fun checkOfferIdExistsOrThrowException(offerId: String) {
        if (!offersDao.isExist(offerId)) {
            throw OfferNotFoundException("Offer with ID $offerId not found")
        }
    }

    private fun validateOfferDetailsOrThrowException(offer: Offer) {
        val message = when {
            (offer.isFieldsBlank()) -> "Required fields cannot be blank"
            (offer.discountPercentage == 0) -> "Discount % cannot be ZERO"
            (offer.minLimit == offer.upToLimit) -> "Min Limit & UpTo Limit cannot be same"
            else -> return
        }
        throw BadRequestException(message)
    }

    companion object {
        fun validateOfferIdOrThrowException(offerId: String) {
            val message = when {
                (!Offer.isValidEntityIDType(offerId)) -> "Offer ID invalid exception"
                else -> return
            }
            throw BadRequestException(message)
        }
    }

}