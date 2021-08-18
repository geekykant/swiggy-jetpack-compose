package com.paavam.model.response

import com.paavam.data.model.Offer
import kotlinx.serialization.Serializable

/**
 * Responses for offers list
 */
@Serializable
data class RestaurantWithOffersResponse(
    override val status: State,
    override val message: String,
    val offerList: List<Offer>? = null
) : Response {
    companion object {
        fun success(offers: List<Offer>) = RestaurantWithOffersResponse(
            State.SUCCESS,
            "Task successful",
            offers
        )

        fun notFound(message: String) = RestaurantWithOffersResponse(
            State.NOT_FOUND,
            message
        )

        fun unauthorized(message: String) = RestaurantWithOffersResponse(
            State.UNAUTHORIZED,
            message
        )

        fun failed(message: String) = RestaurantWithOffersResponse(
            State.FAILED,
            message
        )
    }
}

@Serializable
data class RestaurantWithOfferResponse(
    override val status: State,
    override val message: String,
    val restWithOfferId: String? = null
) : Response {
    companion object {
        fun success(restWithOfferId: String) = RestaurantWithOfferResponse(
            State.SUCCESS,
            "Task successful",
            restWithOfferId
        )

        fun notFound(message: String) = RestaurantWithOfferResponse(
            State.NOT_FOUND,
            message
        )

        fun unauthorized(message: String) = RestaurantWithOfferResponse(
            State.UNAUTHORIZED,
            message
        )

        fun failed(message: String) = RestaurantWithOfferResponse(
            State.FAILED,
            message
        )
    }
}