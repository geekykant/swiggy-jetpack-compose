package com.paavam.swiggyapp.core.data.user.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.user.model.UserAddress
import kotlinx.coroutines.flow.Flow

//@Singleton
interface SwiggyUserAddressRepository {

    suspend fun getAddressById(addressId: Int): Flow<ResponseResult<UserAddress>>

    suspend fun getAllAddresses(): Flow<ResponseResult<List<UserAddress>>>

    suspend fun addAddress(userAddress: UserAddress): ResponseResult<String>

    suspend fun deleteAddressById(addressId: Int): ResponseResult<String>

    suspend fun deleteAllAddresses(): ResponseResult<String>

    suspend fun updateAddressById(
        addressId: String,
        userAddress: UserAddress
    ): ResponseResult<String>
}