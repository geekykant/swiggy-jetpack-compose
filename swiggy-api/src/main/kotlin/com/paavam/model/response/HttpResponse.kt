package com.paavam.model.response

import io.ktor.http.*

sealed class HttpResponse<T : Response> {
    abstract val body: T
    abstract val code: HttpStatusCode

    data class Ok<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.OK
    }

    data class BadRequest<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.BadRequest
    }

    data class NotFound<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.NotFound
    }

    data class Unauthorized<T : Response>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.Unauthorized
    }

    companion object {
        fun <T : Response> ok(response: T) = Ok(body = response)
        fun <T : Response> badRequest(response: T) = BadRequest(body = response)
        fun <T : Response> unAuth(response: T) = Unauthorized(body = response)
    }
}

/**
 * Generates [HttpResponse] from [Response].
 */
fun generateHttpResponse(response: Response): HttpResponse<Response> {
    return when (response.status) {
        State.SUCCESS -> HttpResponse.ok(response)
        State.FAILED -> HttpResponse.badRequest(response)
        State.NOT_FOUND -> HttpResponse.NotFound(response)
        State.UNAUTHORIZED -> HttpResponse.unAuth(response)
    }
}