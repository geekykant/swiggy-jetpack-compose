package com.paavam.model.response

import com.paavam.data.model.Offer
import kotlinx.serialization.Serializable

/**
 * Responses for list of offers.
 */
@Serializable
data class OffersListResponse(
    override val status: State,
    override val message: String,
    val offers: List<Offer>? = null
) : Response {
    companion object {
        fun success(offers: List<Offer>) = OffersListResponse(
            State.SUCCESS,
            "Task successful",
            offers
        )

        fun notFound(message: String) = OffersListResponse(
            State.NOT_FOUND,
            message
        )

        fun unauthorized(message: String) = OffersListResponse(
            State.UNAUTHORIZED,
            message
        )

        fun failed(message: String) = OffersListResponse(
            State.FAILED,
            message
        )
    }
}

/**
 * Responses for single offer.
 */
@Serializable
data class OffersResponse(
    override val status: State,
    override val message: String,
    val offer: Offer? = null
) : Response {
    companion object {
        fun success(offer: Offer) = OffersResponse(
            State.SUCCESS,
            "Task successful",
            offer
        )

        fun notFound(message: String) = OffersResponse(
            State.NOT_FOUND,
            message
        )

        fun unauthorized(message: String) = OffersResponse(
            State.UNAUTHORIZED,
            message
        )

        fun failed(message: String) = OffersResponse(
            State.FAILED,
            message
        )
    }
}

/**
 * CRUD operation responses.
 */
@Serializable
data class OfferResponse(
    override val status: State,
    override val message: String,
    val offerId: String? = null
) : Response {
    companion object {
        fun unauthorized(message: String) = OfferResponse(
            State.UNAUTHORIZED,
            message
        )

        fun failed(message: String) = OfferResponse(
            State.FAILED,
            message
        )

        fun notFound(message: String) = OfferResponse(
            State.NOT_FOUND,
            message
        )

        fun success(offerId: String) = OfferResponse(
            State.SUCCESS,
            "Task successful",
            offerId
        )
    }
}