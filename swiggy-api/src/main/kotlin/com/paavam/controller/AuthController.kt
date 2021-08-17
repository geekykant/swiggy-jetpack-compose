package com.paavam.controller

import com.paavam.auth.SwiggyJWT
import com.paavam.data.dao.UserDao
import com.paavam.data.model.User
import com.paavam.exception.BadRequestException
import com.paavam.exception.UnauthorizedException
import com.paavam.model.response.AuthResponse
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
        val user = User(mobileNo = mobileNo, password = password)
        return try {
            validateCredentialsOrThrowException(user)

            if (!userDao.isUsersExists(mobileNo)) {
                throw BadRequestException("Username not available")
            }

            val newUser = userDao.addUser(user)
            AuthResponse.success(jwt.sign(newUser.mobileNo), "Registration successful")
        } catch (badEx: BadRequestException) {
            AuthResponse.failed(badEx.message)
        }
    }

    fun loginWithMobileNo(mobileNo: String, password: String): AuthResponse {
        val user = User(mobileNo = mobileNo, password = password)
        return try {
            validateCredentialsOrThrowException(user)

            val newUser = userDao.getUserByMobileNoAndPassword(mobileNo, password)
                ?: throw UnauthorizedException("Invalid Credentials")

            AuthResponse.success(jwt.sign(newUser.mobileNo), "Login successful")
        } catch (badEx: BadRequestException) {
            AuthResponse.failed(badEx.message)
        } catch (authEx: UnauthorizedException) {
            AuthResponse.unauthorized(authEx.message)
        }
    }

    private fun validateCredentialsOrThrowException(user: User) {
        val message = when {
            (user.mobileNo.isBlank() or user.password.isBlank()) -> "Mobile No. or password should not be blank"
            (!user.mobileNo.isMobileNumber()) -> "Mobile number should be only numeric"
            else -> return
        }
        throw BadRequestException(message)
    }

}