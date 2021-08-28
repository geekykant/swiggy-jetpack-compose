package com.paavam.swiggyapp.data.remote.util

import com.paavam.swiggyapp.core.utils.fromJson
import retrofit2.Response

inline fun <reified T> Response<T>.getResponse(): T {
    val body = body()
    return if (this.isSuccessful && body != null) {
        body
    } else {
        fromJson<T>(errorBody()!!.string())!!
    }
}