package com.paavam.swiggyapp.di

import com.paavam.swiggyapp.core.task.SwiggyTaskManager
import com.paavam.swiggyapp.task.SwiggyTaskManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TaskManagerModule {

    @Binds
    fun swiggyTaskManager(swiggyTaskManager: SwiggyTaskManagerImpl): SwiggyTaskManager

}
