package com.paavam.swiggyapp.data.remote.api

import com.paavam.swiggyapp.core.data.model.UserAddress
import com.paavam.swiggyapp.data.remote.model.request.UserAddressRequest
import com.paavam.swiggyapp.data.remote.model.response.UserAddressResponse
import retrofit2.Response
import retrofit2.http.GET

interface SwiggyUserAddressService {

    @GET("")
    suspend fun addAddress(userAddress: UserAddressRequest): Response<UserAddressResponse<UserAddress>>

    @GET("")
    suspend fun getAddressById(addressId: Int): Response<UserAddressResponse<UserAddress>>

    @GET("")
    suspend fun getAllAddresses(): Response<UserAddressResponse<List<UserAddress>>>

    @GET("")
    suspend fun deleteAddressById(addressId: Int): Response<UserAddressResponse<UserAddress>>

    @GET("")
    suspend fun deleteAllAddresses(): Response<UserAddressResponse<UserAddress>>

    @GET("")
    suspend fun updateAddressById(
        userAddress: UserAddressRequest
    ): Response<UserAddressResponse<UserAddress>>

}