package com.paavam.controller

import com.paavam.data.dao.FoodsDao
import com.paavam.data.model.Food
import com.paavam.exception.BadRequestException
import com.paavam.exception.FoodNotFoundException
import com.paavam.model.response.FoodResponse
import javax.inject.Inject

class FoodsController @Inject constructor(
    private val foodsDao: FoodsDao
) {

    fun addNewFood(food: Food): FoodResponse {
        return try {
            validateFoodFieldsOrThrowException(food)

            val newId = foodsDao.addNewFood(food)
            FoodResponse.success(newId)
        } catch (badEx: BadRequestException) {
            FoodResponse.failed(badEx.message)
        }
    }

    fun updateFood(foodId: String, food: Food): FoodResponse {
        return try {
            validateFoodIdOrThrowException(foodId)
            validateFoodFieldsOrThrowException(food)

            val id = foodsDao.updateFood(foodId, food)
            FoodResponse.success(id)
        } catch (notFoundEx: FoodNotFoundException) {
            FoodResponse.notFound(notFoundEx.message)
        } catch (badEx: BadRequestException) {
            FoodResponse.failed(badEx.message)
        }
    }

    fun deleteFood(foodId: String): FoodResponse {
        return try {
            validateFoodIdOrThrowException(foodId)

            if (foodsDao.deleteFood(foodId)) {
                FoodResponse.success(foodId)
            } else {
                FoodResponse.failed("Error Occurred")
            }
        } catch (notFoundEx: FoodNotFoundException) {
            FoodResponse.notFound(notFoundEx.message)
        }
    }

    private fun validateFoodIdOrThrowException(foodId: String) {
        if (!foodsDao.isExist(foodId)) {
            throw FoodNotFoundException("Food with ID $foodId not found")
        }
    }

    private fun validateFoodFieldsOrThrowException(food: Food) {
        val message = when {
            food.hasEmptyFields() -> "Required fields cannot be empty"
            food.price == 0f -> "Food  price cannot be 0.00"
            else -> return
        }
        throw BadRequestException(message)
    }
}