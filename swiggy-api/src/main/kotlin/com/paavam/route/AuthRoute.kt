package com.paavam.route

import com.paavam.controller.AuthController
import com.paavam.exception.BadRequestException
import com.paavam.exception.FailureMessages
import com.paavam.model.request.AuthRequest
import com.paavam.model.response.generateHttpResponse
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.junit.internal.runners.statements.Fail

fun Route.auth(authController: AuthController) {

    route("/auth") {
        post("/register") {
            val authRequest = runCatching { call.receive<AuthRequest>() }.getOrElse {
                throw BadRequestException(FailureMessages.MESSAGE_MISSING_CREDENTIALS)
            }

            val authResponse = authController.register(authRequest.mobileNo, authRequest.password)
            val response = generateHttpResponse(authResponse)
            call.respond(response.code, response.body)
        }

        post("/login") {
            val authRequest = runCatching { call.receive<AuthRequest>() }.getOrElse {
                throw BadRequestException(FailureMessages.MESSAGE_MISSING_CREDENTIALS)
            }

            val authResponse = authController.loginWithMobileNo(authRequest.mobileNo, authRequest.password)
            val response = generateHttpResponse(authResponse)
            call.respond(response.code, response.body)
        }
    }
}