package com.paavam.swiggy.api.controller

import com.paavam.swiggy.api.exception.BadRequestException
import com.paavam.swiggy.api.exception.UnauthorizedException
import com.paavam.swiggy.api.model.response.AuthResponse
import com.paavam.swiggy.api.auth.SwiggyJWT
import com.paavam.swiggy.data.dao.UserDao
import com.paavam.swiggy.data.model.User
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

            if (userDao.isUsersExists(mobileNo)) {
                throw BadRequestException("This mobile No. is already registered")
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