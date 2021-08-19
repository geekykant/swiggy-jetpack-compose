package com.paavam.swiggy.api.route

import com.paavam.swiggy.api.controller.AuthController
import com.paavam.swiggy.api.exception.BadRequestException
import com.paavam.swiggy.api.exception.FailureMessages
import com.paavam.swiggy.api.model.response.generateHttpResponse
import com.paavam.swiggy.data.model.User
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.junit.internal.runners.statements.Fail

fun Route.auth(authController: AuthController) {

    route("/auth") {
        post("/register") {

            val authRequest = runCatching { call.receive<User>() }.getOrElse {
                throw BadRequestException(FailureMessages.MESSAGE_MISSING_CREDENTIALS)
            }

            val authResponse = authController.register(authRequest.mobileNo, authRequest.password)
            val response = generateHttpResponse(authResponse)
            call.respond(response.code, response.body)
        }

        post("/login") {
            val authRequest = runCatching { call.receive<User>() }.getOrElse {
                throw BadRequestException(FailureMessages.MESSAGE_MISSING_CREDENTIALS)
            }

            val authResponse = authController.loginWithMobileNo(authRequest.mobileNo, authRequest.password)
            val response = generateHttpResponse(authResponse)
            call.respond(response.code, response.body)
        }
    }
}