package com.paavam.route

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.basicRouting() {

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello Swiggy World!")
        }
    }
}
