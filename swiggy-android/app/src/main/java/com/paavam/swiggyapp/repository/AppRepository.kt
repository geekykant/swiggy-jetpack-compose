package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.user.model.UserAddress
import javax.inject.Inject

/**
 * Repository - App Wide
 */
class AppRepository @Inject constructor(
    private val swiggyService: SwiggyService
) {
    suspend fun fetchUsersAddress(): ResponseResult<List<UserAddress>> =
        swiggyService.fetchUsersAddress()
}