package com.paavam.swiggyapp.data.remote.model.response

interface BaseResponse {
    val status: State
    val message: String
}

enum class State {
    SUCCESS, FAILED, UNAUTHORIZED, NOT_FOUND
}