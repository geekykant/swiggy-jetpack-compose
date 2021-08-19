package com.paavam.swiggy.data.entity

import com.paavam.swiggy.data.database.tables.Users
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EntityUser(user_id: EntityID<Long>) : LongEntity(user_id) {
    companion object : LongEntityClass<EntityUser>(Users)

    var mobileNo by Users.mobileNo
    var password by Users.password
}