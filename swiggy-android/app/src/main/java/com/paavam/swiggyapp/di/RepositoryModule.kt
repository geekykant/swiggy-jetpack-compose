package com.paavam.swiggyapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

//    @Binds
//    fun notyAuthRepository(notyAuthRepository: DefaultSwiggyUserRepository): SwiggyUserRepository

//    @ExperimentalCoroutinesApi
//    @Binds
//    @LocalRepository
//    fun notyLocalNoteRepository(localRepository: SwiggyLocalNoteRepository): NotyNoteRepository

//    @ExperimentalCoroutinesApi
//    @Binds
//    @RemoteRepository
//    fun notyRemoteNoteRepository(remoteRepository: NotyRemoteNoteRepository): NotyNoteRepository

//    @Binds
//    @RemoteRepository
//    fun swiggyRemoteCartRepository(impl: SwiggyRemoteCartRepository): SwiggyCartRepository
}

//@Qualifier
//@Retention(AnnotationRetention.RUNTIME)
//annotation class LocalRepository
//
//@Qualifier
//@Retention(AnnotationRetention.RUNTIME)
//annotation class RemoteRepository