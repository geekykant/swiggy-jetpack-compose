package com.paavam.swiggyapp.network

import okhttp3.Interceptor
import okhttp3.Response

object RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .url(originalRequest.url)
            .build()
        return chain.proceed(request)
    }
}