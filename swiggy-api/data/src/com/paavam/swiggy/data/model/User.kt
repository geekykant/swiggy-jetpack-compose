package com.paavam.swiggy.data.model

import com.paavam.swiggy.data.entity.EntityUser
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val user_id: String? = null,
    val mobileNo: String,
    val password: String,
) {
    companion object {
        fun fromEntity(entity: EntityUser): User =
            User(entity.id.value.toString(), entity.mobileNo, entity.password)

        fun isValidEntityIDType(userId: String): Boolean = userId.toLongOrNull() != null
    }
}

