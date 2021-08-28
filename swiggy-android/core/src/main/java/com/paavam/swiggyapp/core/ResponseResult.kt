package com.paavam.swiggyapp.core

sealed class ResponseResult<T> {
    data class Success<T>(val data: T) : ResponseResult<T>()
    data class Error<T>(val message: String) : ResponseResult<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String) = Error<T>(message)
    }
}