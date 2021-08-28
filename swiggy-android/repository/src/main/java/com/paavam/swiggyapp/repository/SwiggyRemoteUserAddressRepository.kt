package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.user.model.UserAddress
import com.paavam.swiggyapp.core.data.user.repository.SwiggyUserAddressRepository
import com.paavam.swiggyapp.data.remote.api.SwiggyUserAddressService
import com.paavam.swiggyapp.data.remote.model.response.State
import com.paavam.swiggyapp.data.remote.util.getResponse
import com.paavam.swiggyapp.repository.mapper.UserAddressMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SwiggyRemoteUserAddressRepository @Inject internal constructor(
    private val userAddressService: SwiggyUserAddressService
) : SwiggyUserAddressRepository {

    override suspend fun addAddress(userAddress: UserAddress): ResponseResult<String> {
        return runCatching {
            val addressResponse =
                userAddressService.addAddress(UserAddressMapper.mapUserAddressToRequest(userAddress))
                    .getResponse()
            when (addressResponse.status) {
                State.SUCCESS -> ResponseResult.success(addressResponse.data.addressId.toString())
                else -> ResponseResult.error(addressResponse.message)
            }

        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }

    override suspend fun getAddressById(addressId: Int): Flow<ResponseResult<UserAddress>> = flow {
        val addressResponse = userAddressService.getAddressById(addressId).getResponse()
        val state = when (addressResponse.status) {
            State.SUCCESS -> ResponseResult.success(addressResponse.data)
            else -> ResponseResult.error(addressResponse.message)
        }
        emit(state)
    }.catch { emit(ResponseResult.error("Unable to get address by id")) }

    override suspend fun getAllAddresses(): Flow<ResponseResult<List<UserAddress>>> = flow {
        val addressResponse = userAddressService.getAllAddresses().getResponse()
        val state = when (addressResponse.status) {
            State.SUCCESS -> ResponseResult.success(addressResponse.data)
            else -> ResponseResult.error(addressResponse.message)
        }
        emit(state)
    }.catch { emit(ResponseResult.error("Unable to fetch all addresse")) }

    override suspend fun deleteAddressById(addressId: Int): ResponseResult<String> {
        return runCatching {
            val addressResponse = userAddressService.deleteAddressById(addressId).getResponse()
            when (addressResponse.status) {
                State.SUCCESS -> ResponseResult.success("Success")
                else -> ResponseResult.error(addressResponse.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }

    override suspend fun deleteAllAddresses(): ResponseResult<String> {
        return runCatching {
            val addressResponse = userAddressService.deleteAllAddresses().getResponse()
            when (addressResponse.status) {
                State.SUCCESS -> ResponseResult.success("Success")
                else -> ResponseResult.error(addressResponse.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }

    override suspend fun updateAddressById(
        addressId: String,
        userAddress: UserAddress
    ): ResponseResult<String> {
        return runCatching {
            val addressResponse =
                userAddressService.updateAddressById(
                    addressId.toInt(),
                    UserAddressMapper.mapUserAddressToRequest(userAddress)
                ).getResponse()
            when (addressResponse.status) {
                State.SUCCESS -> ResponseResult.success("Success")
                else -> ResponseResult.error(addressResponse.message)
            }
        }.getOrElse {
            it.printStackTrace()
            ResponseResult.error("Something went wrong!")
        }
    }
}