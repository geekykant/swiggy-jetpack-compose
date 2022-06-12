package com.paavam.swiggyapp.di

import android.app.Application
import com.paavam.swiggyapp.data.local.SwiggyDatabase
import com.paavam.swiggyapp.data.local.dao.CartDao
import com.paavam.swiggyapp.data.local.dao.UserAddressDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(application: Application) : SwiggyDatabase =
        SwiggyDatabase.getInstance(application)

    @Provides
    fun provideUserAddressDao(database: SwiggyDatabase) : UserAddressDao =
        database.getUserAddressDao()

    @Provides
    fun provideCartDao(database: SwiggyDatabase): CartDao =
        database.getFoodCartDao()
}