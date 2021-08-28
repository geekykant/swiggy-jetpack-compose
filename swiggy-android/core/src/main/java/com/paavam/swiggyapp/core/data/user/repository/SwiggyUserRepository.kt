package com.paavam.swiggyapp.core.data.user.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.user.model.AuthCredential
import com.paavam.swiggyapp.core.data.user.model.AuthUser

//@Singleton
interface SwiggyUserRepository {

    suspend fun addUser(authUser: AuthUser): ResponseResult<AuthCredential>

    suspend fun loginUser(authUser: AuthUser): ResponseResult<AuthCredential>
}