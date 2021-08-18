package com.paavam.data.database.tables

import com.paavam.data.database.tables.enums.FoodTypes
import org.jetbrains.exposed.dao.id.LongIdTable

object Foods : LongIdTable("foods", columnName = "food_id") {
    val name = text("name")
    val foodType = enumeration("food_type", FoodTypes::class)
    val starText = text("star_text").nullable()
    val price = float("price")
    val foodContents = text("food_contents").nullable()
    val imageUrl = text("image_url").nullable()
}