package com.paavam.swiggyapp.di

import com.paavam.swiggyapp.core.data.repository.SwiggyCartRepository
import com.paavam.swiggyapp.core.data.repository.SwiggyCuisineRepository
import com.paavam.swiggyapp.core.data.repository.SwiggyPropsRepository
import com.paavam.swiggyapp.core.data.repository.SwiggyRestaurantRepository
import com.paavam.swiggyapp.repository.SwiggyRemoteCartRepository
import com.paavam.swiggyapp.repository.SwiggyRemoteCuisineRepository
import com.paavam.swiggyapp.repository.SwiggyRemotePropsRepository
import com.paavam.swiggyapp.repository.SwiggyRemoteRestaurantRepository
import com.paavam.swiggyapp.repository.local.SwiggyLocalCartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @RemoteRepository
    fun providesRestaurantRepository(impl: SwiggyRemoteRestaurantRepository): SwiggyRestaurantRepository

    @Binds
    @RemoteRepository
    fun providesPropsRepository(impl: SwiggyRemotePropsRepository): SwiggyPropsRepository

    @Binds
    @RemoteRepository
    fun providesCuisineRepository(impl: SwiggyRemoteCuisineRepository): SwiggyCuisineRepository

    @ExperimentalCoroutinesApi
    @Binds
    @LocalRepository
    fun swiggyLocalCartRepository(impl: SwiggyLocalCartRepository): SwiggyCartRepository

    @Binds
    @RemoteRepository
    fun swiggyRemoteCartRepository(impl: SwiggyRemoteCartRepository): SwiggyCartRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRepository

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteRepository