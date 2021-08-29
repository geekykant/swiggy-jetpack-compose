package com.paavam.swiggyapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Cart", primaryKeys = ["cart_food_id"])
data class CartEntity(
    @ColumnInfo(name = "cart_food_id")
    val foodId: Long = 0,

    @ColumnInfo(name = "food_name")
    val foodName: String,

    @ColumnInfo(name = "food_type")
    val foodType: EntityFoodType,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "food_contents")
    val foodContents: String?,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,

    @ColumnInfo(name = "quantity")
    var quantityInCart: Int = 0
)

enum class EntityFoodType {
    VEG, NON_VEG
}