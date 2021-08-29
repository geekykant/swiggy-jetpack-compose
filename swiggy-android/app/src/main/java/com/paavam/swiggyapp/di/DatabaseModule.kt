package com.paavam.swiggyapp.di

import android.app.Application
import com.paavam.swiggyapp.data.local.SwiggyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = SwiggyDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideUserAddressDao(database: SwiggyDatabase) = database.getUserAddressDao()

    @Singleton
    @Provides
    fun provideCartDao(database: SwiggyDatabase) = database.getFoodCartDao()
}