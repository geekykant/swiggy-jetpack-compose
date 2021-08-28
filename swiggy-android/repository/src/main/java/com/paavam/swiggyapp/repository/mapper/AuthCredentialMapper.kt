package com.paavam.swiggyapp.repository.mapper

import com.paavam.swiggyapp.core.data.user.model.AuthUser
import com.paavam.swiggyapp.data.remote.model.request.AuthRequest

object AuthCredentialMapper {

    fun mapAuthCredentialToRequest(type: AuthUser): AuthRequest =
        AuthRequest(
            mobileNo = type.mobileNo,
            password = type.password
        )

}