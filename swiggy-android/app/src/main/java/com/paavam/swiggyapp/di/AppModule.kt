package com.paavam.swiggyapp.di

import android.app.Application
import androidx.work.WorkManager
import com.paavam.swiggyapp.core.session.SessionManager
import com.paavam.swiggyapp.core.session.SessionManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesSessionManger(application: Application): SessionManager {
        return SessionManagerImpl(application)
    }

    @Provides
    @Singleton
    fun providesWorkManager(application: Application): WorkManager {
        return WorkManager.getInstance(application)
    }
}