package com.paavam.swiggy.api.controller

import com.paavam.swiggy.api.exception.BadRequestException
import com.paavam.swiggy.api.exception.RestaurantNotFoundException
import com.paavam.swiggy.api.model.response.RestaurantResponse
import com.paavam.swiggy.api.model.response.RestaurantsResponse
import com.paavam.swiggy.data.dao.RestaurantsDao
import com.paavam.swiggy.data.model.Restaurant
import javax.inject.Inject

class RestaurantsController @Inject constructor(
    private val restaurantsDao: RestaurantsDao
) {

    fun addNewRestaurant(restaurant: Restaurant): RestaurantResponse {
        return try {
            validateRestaurantDetailsOrThrowException(restaurant)

            val newId = restaurantsDao.addNewRestaurant(restaurant)
            RestaurantResponse.success(newId)
        } catch (badEx: BadRequestException) {
            RestaurantResponse.failed(badEx.message)
        }
    }

    fun getRestaurant(restaurantId: String): RestaurantsResponse {
        return try {
            validateRestaurantIdOrThrowException(restaurantId)
            checkRestaurantExistsOrThrowException(restaurantId)

            val restaurant = restaurantsDao.getRestaurantById(restaurantId)
                ?: throw BadRequestException("Failed fetching Restaurant")
            RestaurantsResponse.success(restaurant)
        } catch (notFoundEx: RestaurantNotFoundException) {
            RestaurantsResponse.notFound(notFoundEx.message)
        } catch (badEx: BadRequestException) {
            RestaurantsResponse.failed(badEx.message)
        }
    }

    fun updateRestaurant(restaurantId: String, restaurant: Restaurant): RestaurantResponse {
        return try {
            validateRestaurantIdOrThrowException(restaurantId)
            validateRestaurantDetailsOrThrowException(restaurant)

            val newID = restaurantsDao.updateRestaurant(restaurantId, restaurant)
            RestaurantResponse.success(newID)
        } catch (notFoundEx: RestaurantNotFoundException) {
            RestaurantResponse.notFound(notFoundEx.message)
        } catch (badEx: BadRequestException) {
            RestaurantResponse.failed(badEx.message)
        }
    }

    fun deleteRestaurant(restaurantId: String): RestaurantResponse {
        return try {
            validateRestaurantIdOrThrowException(restaurantId)
            checkRestaurantExistsOrThrowException(restaurantId)

            if (restaurantsDao.deleteRestaurant(restaurantId)) {
                RestaurantResponse.success(restaurantId)
            } else {
                RestaurantResponse.failed("Error Occurred!")
            }
        } catch (notFoundEx: RestaurantNotFoundException) {
            RestaurantResponse.notFound(notFoundEx.message)
        } catch (badEx: BadRequestException) {
            RestaurantResponse.failed(badEx.message)
        }
    }

    private fun checkRestaurantExistsOrThrowException(restaurantId: String) {
        if (!restaurantsDao.isExist(restaurantId)) {
            throw RestaurantNotFoundException("Restaurant with ID $restaurantId not found")
        }
    }

    companion object {
        fun validateRestaurantDetailsOrThrowException(restaurant: Restaurant) {
            val message = when {
                (restaurant.isFieldsBlank()) -> "Restaurant fields cannot be blank"
                else -> return
            }
            throw BadRequestException(message)
        }

        fun validateRestaurantIdOrThrowException(restaurantId: String) {
            val message = when {
                (!Restaurant.isValidEntityIDType(restaurantId)) -> "Restaurant ID invalid exception"
                else -> return
            }
            throw BadRequestException(message)
        }
    }

}