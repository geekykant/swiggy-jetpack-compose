package com.paavam.swiggyapp.core.ui

sealed class UiState<T> {
    class Loading<T> : UiState<T>()
    class Success<T>(val data: T) : UiState<T>()
    class Failed<T>(val message: String) : UiState<T>()

    val isLoading get() = this is Loading
    val isSuccess get() = this is Success
    val isFailed get() = this is Failed

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}