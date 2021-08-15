package com.paavam.data.model

import com.paavam.data.entity.EntityUser

data class User(
    val user_id: String,
    val mobileNo: String,
    val password: String
) {
    companion object {
        fun fromEntity(entity: EntityUser) =
            User(entity.id.value.toString(), entity.mobileNo, entity.password)
    }
}