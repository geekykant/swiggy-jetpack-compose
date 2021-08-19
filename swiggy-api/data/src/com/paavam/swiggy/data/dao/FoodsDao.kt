package com.paavam.swiggy.data.dao

import com.paavam.swiggy.data.entity.EntityFood
import com.paavam.swiggy.data.model.Food
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class FoodsDao @Inject constructor() {
    fun addNewFood(food: Food): String = transaction {
        EntityFood.new {
            this.name = food.name
            this.foodType = food.foodType
            this.starText = food.starText
            this.price = food.price
            this.foodContents = food.foodContents
            this.imageUrl = food.imageUrl
        }.id.toString()
    }

    fun updateFood(foodId: String, food: Food): String = transaction {
        EntityFood[foodId.toLong()].apply {
            this.name = food.name
            this.foodType = food.foodType
            this.starText = food.starText
            this.price = food.price
            this.foodContents = food.foodContents
            this.imageUrl = food.imageUrl
        }.id.toString()
    }

    fun isExist(foodId: String): Boolean = transaction {
        EntityFood.findById(foodId.toLong()) != null
    }

    fun deleteFood(foodId: String): Boolean = transaction {
        val curFood = EntityFood.findById(foodId.toLong())
        curFood?.run {
            delete()
            return@transaction true
        }
        return@transaction false
    }
}