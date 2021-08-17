package com.paavam.model.response

/**
 * Response model to expose API response
 */
interface Response {
    val status: State
    val message: String
}

enum class State {
    SUCCESS, FAILED, NOT_FOUND, UNAUTHORIZED
}