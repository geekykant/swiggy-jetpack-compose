package com.paavam.swiggyapp.core.di

import com.paavam.swiggyapp.core.repository.HomeRepository
import com.paavam.swiggyapp.core.repository.RestaurantsRepository
import com.paavam.swiggyapp.core.repository.SwiggyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

//@Module
//@InstallIn(ViewModelComponent::class)
//class RepositoryModule {
//    @Provides
//    @ViewModelScoped
//    fun provideHomeRepository(swiggyService: SwiggyService): HomeRepository {
//        return HomeRepository(swiggyService)
//    }
//
//    @Provides
//    @ViewModelScoped
//    fun provideRestaurantRepository(swiggyService: SwiggyService): RestaurantsRepository {
//        return RestaurantsRepository(swiggyService)
//    }
//}