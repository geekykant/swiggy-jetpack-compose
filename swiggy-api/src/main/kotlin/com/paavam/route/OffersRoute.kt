package com.paavam.route

import com.paavam.controller.OffersController
import com.paavam.data.model.Offer
import com.paavam.exception.BadRequestException
import com.paavam.exception.FailureMessages
import com.paavam.exception.UnauthorizedException
import com.paavam.model.request.MobileNoPrincipal
import com.paavam.model.response.generateHttpResponse
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