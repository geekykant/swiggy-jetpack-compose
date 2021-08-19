package com.paavam.swiggy.data.database.tables

import com.paavam.swiggy.data.database.tables.enums.FoodTypes
import org.jetbrains.exposed.dao.id.LongIdTable
import java.awt.SystemColor.text
import java.util.Collections.enumeration

object Foods : LongIdTable("foods", columnName = "food_id") {
    val name = text("name")
    val foodType = enumeration("food_type", FoodTypes::class)
    val starText = text("star_text").nullable()
    val price = float("price")
    val foodContents = text("food_contents").nullable()
    val imageUrl = text("image_url").nullable()
}