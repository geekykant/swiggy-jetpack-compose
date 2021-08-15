package com.paavam.data.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object Users : LongIdTable("users", columnName = "user_id") {
    val mobileNo = text("mobile_no").uniqueIndex()
    val password = text("password")
}