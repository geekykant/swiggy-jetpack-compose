package com.paavam.swiggyapp.repository.local

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.Food
import com.paavam.swiggyapp.core.data.model.FoodType
import com.paavam.swiggyapp.core.data.repository.SwiggyCartRepository
import com.paavam.swiggyapp.data.local.dao.CartDao
import com.paavam.swiggyapp.data.local.entity.CartEntity
import com.paavam.swiggyapp.data.local.entity.EntityFoodType
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SwiggyLocalCartRepository @Inject constructor(
    private val cartDao: CartDao
) : SwiggyCartRepository {

    override suspend fun fetchUsersCartFoods(): Flow<ResponseResult<List<Food>>> {
        return cartDao
            .getAllCartItems()
            .map { foods ->
                foods.map {
                    Food(
                        it.foodId,
                        it.foodName,
                        FoodType.valueOf(it.foodType.toString()),
                        null,
                        it.price,
                        it.foodContents,
                        it.imageUrl,
                        it.quantityInCart
                    )
                }
            }.transform { foods -> emit(ResponseResult.success(foods)) }
            .catch { emit(ResponseResult.success(emptyList())) }
    }

    override suspend fun fetchUsersCartFoodById(foodId: String): Flow<ResponseResult<Food>> = flow {
         cartDao
            .getCartItemById(foodId.toLong())
            .map {
                Food(
                    it.foodId,
                    it.foodName,
                    FoodType.valueOf(it.foodType.toString()),
                    null,
                    it.price,
                    it.foodContents,
                    it.imageUrl,
                    it.quantityInCart
                )
            }.transform { foods -> emit(ResponseResult.success(foods)) }
    }

    override suspend fun addUsersCartFood(food: Food): ResponseResult<String> {
        return runCatching {
            cartDao.addCartItem(
                CartEntity(
                    food.foodId,
                    food.name,
                    EntityFoodType.valueOf(food.foodType.toString()),
                    food.price,
                    food.foodContents,
                    food.imageUrl,
                    food.quantityInCart
                )
            )
            ResponseResult.success(food.foodId.toString())
        }.getOrDefault(ResponseResult.error("Unable to delete address!"))
    }

    override suspend fun updateUsersCartFood(foodId: Long, food: Food): ResponseResult<String> {
        cartDao.updateCartItemById(
            CartEntity(
                food.foodId,
                food.name,
                EntityFoodType.valueOf(food.foodType.toString()),
                food.price,
                food.foodContents,
                food.imageUrl,
                food.quantityInCart
            )
        )
        return ResponseResult.success("Success")
    }

    override suspend fun addUsersCartFoods(foods: List<Food>): ResponseResult<String> {
        foods.map { food ->
            CartEntity(
                food.foodId,
                food.name,
                EntityFoodType.valueOf(food.foodType.toString()),
                food.price,
                food.foodContents,
                food.imageUrl,
                food.quantityInCart
            )
        }.map {
            cartDao.addCartItem(it)
        }
        return ResponseResult.success("Success")
    }

    override suspend fun deleteCartItem(foodId: Long): ResponseResult<String> {
        cartDao.deleteCartItemById(foodId)
        return ResponseResult.success(foodId.toString())
    }
}