package com.paavam.swiggyapp.repository

import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.model.AuthCredential
import com.paavam.swiggyapp.core.data.model.AuthUser
import com.paavam.swiggyapp.core.data.repository.SwiggyUserRepository
import com.paavam.swiggyapp.data.remote.api.SwiggyAuthService
import com.paavam.swiggyapp.data.remote.model.response.State
import com.paavam.swiggyapp.data.remote.util.getResponse
import com.paavam.swiggyapp.repository.mapper.AuthCredentialMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultSwiggyUserRepository @Inject internal constructor(
    private val authService: SwiggyAuthService
) : SwiggyUserRepository {

    override suspend fun addUser(authUser: AuthUser): ResponseResult<AuthCredential> {
        return runCatching {
            val authResponse =
                authService.register(
                    AuthCredentialMapper.mapAuthCredentialToRequest(authUser)
                ).getResponse()

            when (authResponse.status) {
                State.SUCCESS -> ResponseResult.success(AuthCredential(authResponse.token!!))
                else -> ResponseResult.error(authResponse.message)
            }
        }.getOrDefault(ResponseResult.error("Something went wrong!"))
    }

    override suspend fun loginUser(authUser: AuthUser): ResponseResult<AuthCredential> {
        return runCatching {
            val authResponse =
                authService.login(AuthCredentialMapper.mapAuthCredentialToRequest(authUser))
                    .getResponse()

            when (authResponse.status) {
                State.SUCCESS -> ResponseResult.success(AuthCredential(authResponse.token!!))
                else -> ResponseResult.error(authResponse.message)
            }
        }.getOrDefault(ResponseResult.error("Something went wrong!"))
    }
}