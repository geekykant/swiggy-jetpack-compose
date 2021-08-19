package com.paavam.swiggy.api.di

import com.paavam.swiggy.api.controller.AuthController
import com.paavam.swiggy.api.controller.OffersController
import com.paavam.swiggy.api.controller.RestaurantWithOffersController
import com.paavam.swiggy.api.controller.RestaurantsController
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface ControllerComp {
    fun authController(): AuthController
    fun restaurantsController(): RestaurantsController
    fun offersController(): OffersController
    fun restaurantWithOffersController(): RestaurantWithOffersController
}