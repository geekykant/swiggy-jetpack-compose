package com.paavam.model.response

import com.paavam.data.model.Restaurant
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantsListResponse(
    override val status: State,
    override val message: String,
    val restaurants: List<Restaurant>? = null
) : Response {
    companion object {
        fun unauthorized(message: String) = RestaurantsListResponse(
            State.UNAUTHORIZED,
            message
        )

        fun failed(message: String) = RestaurantsListResponse(
            State.FAILED,
            message
        )

        fun notFound(message: String) = RestaurantsListResponse(
            State.NOT_FOUND,
            message
        )

        fun success(restaurants: List<Restaurant>) = RestaurantsListResponse(
            State.SUCCESS,
            "Task successful",
            restaurants
        )
    }
}

@Serializable
data class RestaurantsResponse(
    override val status: State,
    override val message: String,
    val restaurant: Restaurant? = null
) : Response {
    companion object {
        fun unauthorized(message: String) = RestaurantsResponse(
            State.UNAUTHORIZED,
            message
        )

        fun failed(message: String) = RestaurantsResponse(
            State.FAILED,
            message
        )

        fun notFound(message: String) = RestaurantsResponse(
            State.NOT_FOUND,
            message
        )

        fun success(restaurant: Restaurant) = RestaurantsResponse(
            State.SUCCESS,
            "Task successful",
            restaurant
        )
    }
}

@Serializable
data class RestaurantResponse(
    override val status: State,
    override val message: String,
    val restaurantId: String? = null
) : Response {
    companion object {
        fun unauthorized(message: String) = RestaurantResponse(
            State.UNAUTHORIZED,
            message
        )

        fun failed(message: String) = RestaurantResponse(
            State.FAILED,
            message
        )

        fun notFound(message: String) = RestaurantResponse(
            State.NOT_FOUND,
            message
        )

        fun success(restaurantId: String) = RestaurantResponse(
            State.SUCCESS,
            "Task successful",
            restaurantId
        )
    }
}