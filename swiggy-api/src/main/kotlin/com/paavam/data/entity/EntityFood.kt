package com.paavam.data.entity

import com.paavam.data.database.tables.Foods
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EntityFood(food_id: EntityID<Long>) : LongEntity(food_id) {
    companion object : LongEntityClass<EntityFood>(Foods)

    val name by Foods.name
    val foodType by Foods.foodType
    val starText by Foods.starText
    val price by Foods.price
    val foodContents by Foods.foodContents
    val imageUrl by Foods.imageUrl
}