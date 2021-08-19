package com.paavam.swiggy.api

import com.paavam.swiggy.api.auth.SwiggyJWT
import com.paavam.swiggy.api.di.DaggerControllerComp
import com.paavam.swiggy.api.exception.BadRequestException
import com.paavam.swiggy.api.exception.FailureMessages
import com.paavam.swiggy.api.model.request.MobileNoPrincipal
import com.paavam.swiggy.api.model.response.FailureResponse
import com.paavam.swiggy.api.model.response.State
import com.paavam.swiggy.api.route.auth
import com.paavam.swiggy.api.route.basicRouting
import com.paavam.swiggy.api.route.offers
import com.paavam.swiggy.api.route.restaurants
import com.paavam.swiggy.data.dao.UserDao
import com.paavam.swiggy.data.database.initDatabase
import com.paavam.utils.KeyProvider
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    with(Config(environment.config)) {
        SwiggyJWT.initialize(JWT_SECRET_KEY)
        KeyProvider.initialize(HMAC_SECRET_KEY)
        initDatabase(
            host = DATABASE_HOST,
            port = DATABASE_PORT,
            databaseName = DATABASE_NAME,
            user = DATABASE_USER,
            password = DATABASE_PASSWORD
        )
    }

    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }

    install(Authentication) {
        jwt {
            verifier(SwiggyJWT.instance.verifier)
            validate {
                val claim = it.payload.getClaim(SwiggyJWT.CLAIM).asString()
                if (claim.let(UserDao()::isUsersExists)) {
                    MobileNoPrincipal(claim)
                } else {
                    null
                }
            }
        }
    }

    install(StatusPages) {
        exception<BadRequestException> {
            call.respond(HttpStatusCode.BadRequest, FailureResponse(State.FAILED, it.message ?: "Bad Request"))
        }

        status(HttpStatusCode.InternalServerError) {
            call.respond(it, FailureResponse(State.FAILED, FailureMessages.MESSAGE_FAILED))
        }

        status(HttpStatusCode.Unauthorized) {
            call.respond(it, FailureResponse(State.UNAUTHORIZED, FailureMessages.MESSAGE_ACCESS_DENIED))
        }
    }

    install(ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
            },
            contentType = ContentType.Application.Json
        )
    }

    val controller = DaggerControllerComp.create()

    routing {
        basicRouting()
        auth(controller.authController())
        restaurants(
            controller.restaurantsController(),
            controller.restaurantWithOffersController()
        )
        offers(controller.offersController())
    }
}
