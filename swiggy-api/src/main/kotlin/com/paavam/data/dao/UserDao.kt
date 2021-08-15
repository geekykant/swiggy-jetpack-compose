package com.paavam.data.dao

import com.paavam.data.database.tables.Users
import com.paavam.data.entity.EntityUser
import com.paavam.data.model.User
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class UserDao @Inject constructor() {
    fun addUser(mobileNo: String, password: String): User = transaction {
        EntityUser.new {
            this.mobileNo = mobileNo
            this.password = password
        }
    }.let { User.fromEntity(it) }

    fun getUserById(user_id: Long): User? = transaction {
        EntityUser.findById(user_id)
    }?.let { User.fromEntity(it) }

    fun isUsersExists(mobileNo: String): Boolean = transaction {
        EntityUser.find {
            Users.mobileNo eq mobileNo
        }.firstOrNull() != null
    }

    fun getUserByMobileNoAndPassword(mobileNo: String, password: String): User? = transaction {
        EntityUser.find {
            (Users.mobileNo eq mobileNo) and (Users.password eq password)
        }.firstOrNull()
    }?.let { User.fromEntity(it) }

    fun getUserByMobileNo(mobileNo: String): User? = transaction {
        EntityUser.find {
            Users.mobileNo eq mobileNo
        }.firstOrNull()
    }?.let { User.fromEntity(it) }
}