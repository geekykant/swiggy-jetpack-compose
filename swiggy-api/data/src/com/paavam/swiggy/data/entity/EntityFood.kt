package com.paavam.swiggy.data.entity

import com.paavam.swiggy.data.database.tables.Foods
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EntityFood(food_id: EntityID<Long>) : LongEntity(food_id) {
    companion object : LongEntityClass<EntityFood>(Foods)

    var name by Foods.name
    var foodType by Foods.foodType
    var starText by Foods.starText
    var price by Foods.price
    var foodContents by Foods.foodContents
    var imageUrl by Foods.imageUrl
}