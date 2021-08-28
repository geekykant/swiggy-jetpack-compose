package com.paavam.swiggyapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.paavam.swiggyapp.data.local.entity.UserAddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserAddressDao {

    @Query("SELECT * FROM UserAddress")
    abstract fun getAllAddresses(): Flow<List<UserAddressEntity>>

    @Query("SELECT * FROM UserAddress WHERE address_id = :addressId")
    abstract fun getAddressById(addressId: Int): Flow<UserAddressEntity>

    @Insert
    abstract fun addAddress(userAddress: UserAddressEntity)

    @Delete
    abstract fun deleteAddressById(addressId: Int)

    @Query("DELETE FROM UserAddress")
    abstract fun deleteAllAddresses()

    @Query("DELETE FROM UserAddress")
    abstract fun updateAddressById(addressId: String, userAddress: UserAddressEntity)
}