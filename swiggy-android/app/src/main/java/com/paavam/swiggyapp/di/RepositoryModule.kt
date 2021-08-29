package com.paavam.swiggyapp.di

import com.paavam.swiggyapp.core.data.repository.SwiggyCartRepository
import com.paavam.swiggyapp.repository.SwiggyRemoteCartRepository
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

//    @Binds
//    fun notyAuthRepository(notyAuthRepository: DefaultSwiggyUserRepository): SwiggyUserRepository

//    @ExperimentalCoroutinesApi
//    @Binds
//    @LocalRepository
//    fun notyLocalNoteRepository(localRepository: SwiggyLocalNoteRepository): NotyNoteRepository

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