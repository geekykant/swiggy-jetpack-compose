package com.paavam.model.response

import kotlinx.serialization.Serializable

@Serializable
data class FoodsResponse<T>(
    override val status: State,
    override val message: String,
    val data: T? = null
) : Response {
    companion object {
        fun <T> success(data: T) = FoodsResponse(
            State.SUCCESS,
            "Task successful",
            data
        )

        fun unauthorized(message: String) = FoodsResponse(
            State.UNAUTHORIZED,
            message,
            null
        )

        fun failed(message: String) = FoodsResponse(
            State.FAILED,
            message,
            null
        )

        fun notFound(message: String) = FoodsResponse(
            State.NOT_FOUND,
            message,
            null
        )
    }
}

@Serializable
data class FoodResponse(
    override val status: State,
    override val message: String,
    val foodId: String? = null
) : Response {
    companion object {
        fun success(foodId: String) = FoodResponse(
            State.SUCCESS,
            "Task successful",
            foodId
        )

        fun unauthorized(message: String) = FoodResponse(
            State.UNAUTHORIZED,
            message
        )

        fun failed(message: String) = FoodResponse(
            State.FAILED,
            message
        )

        fun notFound(message: String) = FoodResponse(
            State.NOT_FOUND,
            message
        )
    }
}