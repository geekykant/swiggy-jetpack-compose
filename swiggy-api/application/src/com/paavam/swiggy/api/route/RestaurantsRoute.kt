package com.paavam.swiggy.api.route

import com.paavam.swiggy.api.controller.RestaurantWithOffersController
import com.paavam.swiggy.api.controller.RestaurantsController
import com.paavam.swiggy.api.exception.BadRequestException
import com.paavam.swiggy.api.exception.FailureMessages
import com.paavam.swiggy.api.exception.UnauthorizedException
import com.paavam.swiggy.api.model.request.MobileNoPrincipal
import com.paavam.swiggy.api.model.response.generateHttpResponse
import com.paavam.swiggy.data.model.Restaurant
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.restaurants(
    restaurantsController: RestaurantsController,
    restaurantWithOffersController: RestaurantWithOffersController
) {

    authenticate {
        get("/nearby_restaurants") {
            val principal = call.principal<MobileNoPrincipal>()
                ?: throw UnauthorizedException(FailureMessages.MESSAGE_ACCESS_DENIED)

            val resultList = restaurantWithOffersController.getAllRestaurantsWithOffers()
            val response = generateHttpResponse(resultList)

            call.respond(response.code, response.body)
        }

        get("/spotlight_restaurants") {
            val principal = call.principal<MobileNoPrincipal>()
                ?: throw UnauthorizedException(FailureMessages.MESSAGE_ACCESS_DENIED)

            val resultList = restaurantWithOffersController.getAllRestaurantsWithOffers()
            val response = generateHttpResponse(resultList)

            call.respond(response.code, response.body)
        }

        route("/restaurant") {
            post("/new") {
                val restaurantRequest = kotlin.runCatching { call.receive<Restaurant>() }.getOrElse {
                    throw BadRequestException(FailureMessages.MESSAGE_MISSING_FIELDS)
                }

                val principal = call.principal<MobileNoPrincipal>()
                    ?: throw UnauthorizedException(FailureMessages.MESSAGE_ACCESS_DENIED)

                val restResponse = restaurantsController.addNewRestaurant(restaurantRequest)
                val response = generateHttpResponse(restResponse)
                call.respond(response.code, response.body)
            }

        }
    }

}