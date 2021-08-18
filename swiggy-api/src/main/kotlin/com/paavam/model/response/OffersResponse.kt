package com.paavam.model.response

import com.paavam.data.model.Offer
import kotlinx.serialization.Serializable

/**
 * Responses for single offer entity.
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