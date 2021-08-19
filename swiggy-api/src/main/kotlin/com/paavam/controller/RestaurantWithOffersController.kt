package com.paavam.controller

import com.paavam.controller.RestaurantsController.Companion.validateRestaurantIdOrThrowException
import com.paavam.data.dao.OffersDao
import com.paavam.data.dao.RestaurantWithOffersDao
import com.paavam.data.dao.RestaurantsDao
import com.paavam.exception.BadRequestException
import com.paavam.exception.OfferNotFoundException
import com.paavam.exception.OfferRestaurantMissingLinkException
import com.paavam.exception.RestaurantNotFoundException
import com.paavam.model.response.RestaurantWithOfferResponse
import com.paavam.model.response.RestaurantWithOffersResponse
import com.paavam.model.response.RestaurantsListResponse
import com.paavam.model.response.RestaurantsResponse
import javax.inject.Inject

class RestaurantWithOffersController @Inject constructor(
    private val restaurantWithOffersDao: RestaurantWithOffersDao,
    private val restaurantsDao: RestaurantsDao,
    private val offersDao: OffersDao
) {

    fun getRestaurantWithOffers(restaurantId: String): RestaurantsResponse {
        return try {
            validateRestaurantIdOrThrowException(restaurantId)
            checkRestaurantIdExistsOrThrowExists(restaurantId)

            val restaurant = restaurantWithOffersDao.getRestaurantWithOffers(restaurantId)
                ?: throw BadRequestException("Error occurred!")
            RestaurantsResponse.success(restaurant)
        } catch (notFoundEx: RestaurantNotFoundException) {
            RestaurantsResponse.notFound(notFoundEx.message)
        } catch (badEx: BadRequestException) {
            RestaurantsResponse.failed(badEx.message)
        }
    }

    fun getAllRestaurantsWithOffers(): RestaurantsListResponse {
        return try {
            val restaurantsList = restaurantWithOffersDao.getAllRestaurantsWithOffers()
            RestaurantsListResponse.success(restaurantsList)
        } catch (notFoundEx: RestaurantNotFoundException) {
            RestaurantsListResponse.notFound(notFoundEx.message)
        } catch (badEx: BadRequestException) {
            RestaurantsListResponse.failed(badEx.message)
        }
    }

    fun getOffersListOfRestaurant(restaurantId: String): RestaurantWithOffersResponse {
        return try {
            validateRestaurantIdOrThrowException(restaurantId)
            checkRestaurantIdExistsOrThrowExists(restaurantId)

            val offersList = restaurantWithOffersDao.getAllOffersOfRestaurant(restaurantId)
            RestaurantWithOffersResponse.success(offersList)
        } catch (notFoundEx: RestaurantNotFoundException) {
            RestaurantWithOffersResponse.notFound(notFoundEx.message)
        } catch (badEx: BadRequestException) {
            RestaurantWithOffersResponse.failed(badEx.message)
        }
    }

    fun linkRestaurantWithOffer(restaurantId: String, offerId: String): RestaurantWithOfferResponse {
        return try {
            checkRestaurantIdExistsOrThrowExists(restaurantId)
            checkOfferIdExistsOrThrowExists(offerId)

            if (restaurantWithOffersDao.isOfferLinkedToRestaurant(restaurantId, offerId)) {
                RestaurantWithOfferResponse.success(offerId)
            } else {
                val id = restaurantWithOffersDao.addExistingOfferToRestaurant(restaurantId, offerId)
                RestaurantWithOfferResponse.success(id)
            }
        } catch (rNotFoundEx: RestaurantNotFoundException) {
            RestaurantWithOfferResponse.notFound(rNotFoundEx.message)
        } catch (oNotFoundEx: OfferNotFoundException) {
            RestaurantWithOfferResponse.notFound(oNotFoundEx.message)
        }
    }

    fun removeOfferFromRestaurant(restaurantId: String, offerId: String): RestaurantWithOfferResponse {
        return try {
            checkRestaurantIdExistsOrThrowExists(restaurantId)
            checkOfferIdExistsOrThrowExists(offerId)
            checkOfferExistsInBothTableOrThrowException(offerId)

            if (!restaurantWithOffersDao.isOfferLinkedToRestaurant(restaurantId, offerId)) {
                throw OfferRestaurantMissingLinkException("Error occurred")
            }

            if (restaurantWithOffersDao.deleteOfferOfRestaurant(offerId, restaurantId)) {
                RestaurantWithOfferResponse.success(offerId)
            } else {
                RestaurantWithOfferResponse.failed("Error occurred")
            }
        } catch (rNotFoundEx: RestaurantNotFoundException) {
            RestaurantWithOfferResponse.notFound(rNotFoundEx.message)
        } catch (oNotFoundEx: OfferNotFoundException) {
            RestaurantWithOfferResponse.notFound(oNotFoundEx.message)
        } catch (missingLinkEx: OfferRestaurantMissingLinkException) {
            RestaurantWithOfferResponse.notFound(missingLinkEx.message)
        }
    }

    fun removeAllOffersFromRestaurant(restaurantId: String): RestaurantWithOfferResponse {
        return try {
            checkRestaurantIdExistsOrThrowExists(restaurantId)

            restaurantWithOffersDao.deleteAllOffersOfRestaurant(restaurantId)
            RestaurantWithOfferResponse.success(restaurantId)

            //TODO: Check RestaurantWithOfferResponse.failed("Error occurred")

        } catch (rNotFoundEx: RestaurantNotFoundException) {
            RestaurantWithOfferResponse.notFound(rNotFoundEx.message)
        } catch (oNotFoundEx: OfferNotFoundException) {
            RestaurantWithOfferResponse.notFound(oNotFoundEx.message)
        } catch (missingLinkEx: OfferRestaurantMissingLinkException) {
            RestaurantWithOfferResponse.notFound(missingLinkEx.message)
        }
    }

    private fun checkRestaurantIdExistsOrThrowExists(restaurantId: String) {
        if (!restaurantsDao.isExist(restaurantId)) {
            throw RestaurantNotFoundException("Restaurant with ID $restaurantId not found")
        }
    }

    private fun checkOfferIdExistsOrThrowExists(offerId: String) {
        if (!offersDao.isExist(offerId)) {
            throw OfferNotFoundException("Offer with ID $offerId not found")
        }
    }

    private fun checkOfferExistsInBothTableOrThrowException(offerId: String) {
        val message = when {
            (!offersDao.isExist(offerId)) -> "Offer with ID $offerId details not found"
            (!restaurantWithOffersDao.isExist(offerId)) -> "Offer with ID $offerId missing link"
            else -> return
        }
        throw OfferNotFoundException(message)
    }
}