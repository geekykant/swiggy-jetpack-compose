package com.paavam.swiggy.data.model

import com.paavam.swiggy.data.database.tables.enums.FoodTypes
import com.paavam.swiggy.data.entity.EntityFood
import kotlinx.serialization.Serializable

@Serializable
data class Food(
    val food_id: String? = null,
    val name: String,
    val foodType: FoodTypes,
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

        fun isValidEntityIDType(foodId: String): Boolean = foodId.toLongOrNull() != null
    }
}