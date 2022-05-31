package com.paavam.swiggyapp.di

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.paavam.swiggyapp.ui.MainActivity
import com.paavam.swiggyapp.viewmodel.HomeViewModel
import com.paavam.swiggyapp.viewmodel.RestaurantViewModel
import dagger.hilt.android.EntryPointAccessors

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun restaurantViewModel(restaurantId: Long): RestaurantViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        MainActivity.ViewModelFactoryProvider::class.java
    ).restaurantViewModel()

    return viewModel(factory = RestaurantViewModel.provideFactory(factory, restaurantId))
}