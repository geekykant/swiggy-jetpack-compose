package com.paavam.swiggy.api.route

import com.paavam.swiggy.api.controller.OffersController
import com.paavam.swiggy.api.exception.BadRequestException
import com.paavam.swiggy.api.exception.FailureMessages
import com.paavam.swiggy.api.exception.UnauthorizedException
import com.paavam.swiggy.api.model.request.MobileNoPrincipal
import com.paavam.swiggy.api.model.response.generateHttpResponse
import com.paavam.swiggy.data.model.Offer
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.offers(offersController: OffersController) {

    authenticate {
        get("/all_offers") {
            val principal = call.principal<MobileNoPrincipal>()
                ?: throw UnauthorizedException(FailureMessages.MESSAGE_ACCESS_DENIED)

            val offerList = offersController.getAllOffers()
            val response = generateHttpResponse(offerList)

            call.respond(response.code, response.body)
        }

        route("/offer") {
            post("/new") {
                val offerRequest = kotlin.runCatching { call.receive<Offer>() }.getOrElse {
                    throw BadRequestException(FailureMessages.MESSAGE_MISSING_FIELDS)
                }

                val principal = call.principal<MobileNoPrincipal>()
                    ?: throw UnauthorizedException(FailureMessages.MESSAGE_ACCESS_DENIED)

                val offerResponse = offersController.addNewOffer(offerRequest)
                val response = generateHttpResponse(offerResponse)
                call.respond(response.code, response.body)
            }

        }
    }

}