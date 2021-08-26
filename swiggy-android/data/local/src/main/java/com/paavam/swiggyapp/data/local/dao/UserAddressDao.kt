package com.paavam.swiggyapp.data.local.dao

import androidx.room.*
import com.paavam.swiggyapp.data.local.entity.UserAddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAddressDao {

    @Query("SELECT * FROM UserAddress")
    suspend fun getAllAddresses(): List<UserAddressEntity>

    @Query("SELECT * FROM UserAddress WHERE address_id = :addressId")
    suspend fun getAddressById(addressId: Int): Flow<UserAddressEntity>

    @Insert
    suspend fun addAddress(userAddress: UserAddressEntity)

    @Delete
    suspend fun deleteAddressById(addressId: Int)

    @Query("DELETE FROM UserAddress")
    suspend fun deleteAllAddresses()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAddressById(addressId: String, userAddress: UserAddressEntity)
}