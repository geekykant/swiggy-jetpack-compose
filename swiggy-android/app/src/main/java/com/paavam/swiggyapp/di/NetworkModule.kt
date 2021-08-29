package com.paavam.swiggyapp.di

import com.paavam.swiggyapp.core.utils.moshi
import com.paavam.swiggyapp.data.remote.Constant
import com.paavam.swiggyapp.data.remote.api.SwiggyAuthService
import com.paavam.swiggyapp.data.remote.api.SwiggyService
import com.paavam.swiggyapp.data.remote.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val baseRetrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(Constant.API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))


    private val okHttpClientBuilder: OkHttpClient.Builder =
        OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)

    @Provides
    @Singleton
    fun provideSwiggyService(authInterceptor: AuthInterceptor): SwiggyService {
        return baseRetrofitBuilder
            .client(okHttpClientBuilder.addInterceptor(authInterceptor).build())
            .build()
            .create(SwiggyService::class.java)
    }

    @Provides
    @Singleton
    fun provideSwiggyAuthService(): SwiggyAuthService {
        return baseRetrofitBuilder
            .client(okHttpClientBuilder.build())
            .build()
            .create(SwiggyAuthService::class.java)
    }
}
