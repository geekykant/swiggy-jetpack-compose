package com.paavam.swiggyapp.core.repository

sealed class ResponseResult<T> {
    data class Success<T>(val data: T): ResponseResult<T>()
    data class Error<T>(val errorMessage: String): ResponseResult<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(errorMessage: String) = Error<T>(errorMessage)
    }
}