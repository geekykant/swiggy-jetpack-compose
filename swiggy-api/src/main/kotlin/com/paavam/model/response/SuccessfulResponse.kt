package com.paavam.model.response

data class SuccessfulResponse(
    override val status: State,
    override val message: String,
    val body: Any? = null
): Response {
    companion object{
        fun unauthorized(message: String) = SuccessfulResponse(
            State.UNAUTHORIZED,
            message
        )

        fun success(bodyData: Any?) = SuccessfulResponse(
            State.SUCCESS,
            " RESULT OK",
            bodyData
        )
    }
}