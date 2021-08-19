package com.paavam.swiggy.data.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import java.awt.SystemColor.text

object Users : LongIdTable("users", columnName = "user_id") {
    val mobileNo = text("mobile_no").uniqueIndex()
    val password = text("password")
}