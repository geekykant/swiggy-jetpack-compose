package com.paavam.data.model

import com.paavam.data.database.tables.FoodType
import com.paavam.data.entity.EntityFood

data class Food(
    val food_id: String,
    val name: String,
    val foodType: FoodType,
    val starText: String?,
    val price: Float,
    val foodContents: String?,
    val imageUrl: String?
) {
    fun hasEmptyFields(): Boolean {
        return name.isBlank()
    }

    companion object {
        fun fromEntity(entity: EntityFood): Food =
            Food(
                entity.id.value.toString(),
                entity.name,
                entity.foodType,
                entity.starText,
                entity.price,
                entity.foodContents,
                entity.imageUrl
            )
    }
}