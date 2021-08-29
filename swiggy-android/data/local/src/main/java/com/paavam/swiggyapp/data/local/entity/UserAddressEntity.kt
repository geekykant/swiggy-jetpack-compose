package com.paavam.swiggyapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "UserAddress", primaryKeys = ["address_id"])
data class UserAddressEntity(
    @ColumnInfo(name = "address_id")
    val addressId: Int = 0,

    @ColumnInfo(name = "full_address")
    val fullAddress: String,

    @ColumnInfo(name = "address_type")
    val type: EntityAddressType,

    @ColumnInfo(name = "friendly_label")
    val friendlyLabel: String,

    @ColumnInfo(name = "landmark")
    val landmark: String? = null
)

enum class EntityAddressType {
    HOME, OTHER
}