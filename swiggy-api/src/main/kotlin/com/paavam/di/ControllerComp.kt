package com.paavam.di

import com.paavam.controller.AuthController
import com.paavam.controller.OffersController
import com.paavam.controller.RestaurantWithOffersController
import com.paavam.controller.RestaurantsController
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