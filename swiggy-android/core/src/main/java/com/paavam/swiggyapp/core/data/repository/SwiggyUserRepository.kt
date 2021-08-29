package com.paavam.swiggyapp.core.data.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.AuthCredential
import com.paavam.swiggyapp.core.data.model.AuthUser

//@Singleton
interface SwiggyUserRepository {

    suspend fun addUser(authUser: AuthUser): ResponseResult<AuthCredential>

    suspend fun loginUser(authUser: AuthUser): ResponseResult<AuthCredential>
}