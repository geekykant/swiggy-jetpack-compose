package com.paavam.controller

import com.paavam.auth.SwiggyJWT
import com.paavam.data.dao.UserDao
import com.paavam.exception.BadRequestException
import com.paavam.exception.UnauthorizedException
import com.paavam.model.response.AuthResponse
import com.paavam.utils.hash
import com.paavam.utils.isMobileNumber
import javax.inject.Inject

/**
 * Users data authentication controller.
 */
class AuthController @Inject constructor(
    private val userDao: UserDao
) {
    private val jwt = SwiggyJWT.instance

    fun register(mobileNo: String, password: String): AuthResponse {
        return try {
            validateCredentialsOrThrowException(mobileNo, password)

            if (!userDao.isUsersExists(mobileNo)) {
                throw BadRequestException("Username not available")
            }

            val user = userDao.addUser(mobileNo, hash(password))
            AuthResponse.success(jwt.sign(user.user_id), "Registration successful")
        } catch (badEx: BadRequestException) {
            AuthResponse.failed(badEx.message)
        }
    }

    fun loginWithUsername(mobileNo: String, password: String): AuthResponse {
        return try {
            validateCredentialsOrThrowException(mobileNo, password)

            val user = userDao.getUserByMobileNoAndPassword(mobileNo, password)
                ?: throw UnauthorizedException("Invalid Credentials")

            AuthResponse.success(jwt.sign(user.user_id), "Login successful")
        } catch (badEx: BadRequestException) {
            AuthResponse.failed(badEx.message)
        } catch (authEx: UnauthorizedException) {
            AuthResponse.unauthorized(authEx.message)
        }
    }

    private fun validateCredentialsOrThrowException(mobileNo: String, password: String) {
        val message = when {
            (mobileNo.isBlank() or password.isBlank()) -> "Mobile No. or password should not be blank"
            (!mobileNo.isMobileNumber()) -> "Mobile number should be only numeric"
            else -> return
        }
        throw BadRequestException(message)
    }

}