package com.paavam.swiggyapp.core.session

import javax.inject.Singleton

@Singleton
interface SessionManager {
    fun saveToken(token: String?)
    fun getToken(): String?
}