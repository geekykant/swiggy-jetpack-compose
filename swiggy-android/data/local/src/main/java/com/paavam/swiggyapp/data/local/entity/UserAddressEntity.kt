package com.paavam.swiggyapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserAddress")
data class UserAddressEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "address_id")
    val addressId: Int,

    @ColumnInfo(name = "address_type")
    val type: AddressType,

    @ColumnInfo(name = "friendly_label")
    val friendlyLabel: String,

    @ColumnInfo(name = "full_address")
    val fullAddress: String,

    @ColumnInfo(name = "landmark")
    val landmark: String? = null
)

enum class AddressType {
    HOME, OTHER
}