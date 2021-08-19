package com.paavam

import com.paavam.controller.RestaurantWithOffersController
import com.paavam.data.dao.OffersDao
import com.paavam.data.dao.RestaurantWithOffersDao
import com.paavam.data.dao.RestaurantsDao
import com.paavam.data.database.initDatabase
import kotlin.test.Test

class ApplicationTest {

    @Test
    fun insertFewValues() {
        val DATABASE_HOST = "localhost"// config.property("db_host").getString()
        val DATABASE_PORT = "5432" //config.property("dB_port").getString()
        val DATABASE_NAME = "swiggy_db" //config.property("db_name").getString()
        val DATABASE_USER = "sreekant" //config.property("db_user").getString()
        val DATABASE_PASSWORD = ""

        initDatabase(
            host = DATABASE_HOST,
            port = DATABASE_PORT,
            databaseName = DATABASE_NAME,
            user = DATABASE_USER,
            password = DATABASE_PASSWORD
        )

        val test = RestaurantWithOffersController(RestaurantWithOffersDao(), RestaurantsDao(), OffersDao())
//            .getAllRestaurantsWithOffers()
            .getRestaurantWithOffers("1")
        val test2 = RestaurantWithOffersController(RestaurantWithOffersDao(), RestaurantsDao(), OffersDao())
//            .getAllRestaurantsWithOffers()
            .getRestaurantWithOffers("2")

        println(test)
        println(test2)

//        val offer1 = transaction {
//            EntityOffer.new {
//                offerCode = "OKSMAINA"
//                discountPercentage = 30
//            }
//        }
//        val offer2 = transaction {
//            EntityOffer.new {
//                offerCode = "OKSMAINA"
//                discountPercentage = 30
//            }
//        }
//
//        transaction {
//            RestaurantWithOffers.insert {
//                it[restaurant] = 1L
//                it[offer] = 2L
//            }
//        }

//        val restaurant = Restaurant(
//            restaurant_id = null,
//            "Pizza House",
//            "South Indian, Chineese, Arabian, North India",
//            "Kakkanad",
//            "7.2 kms",
//            4.2f,
//            53,
//            400,
//            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_200,h_220,c_fill/jmkzdtpvr6njj3wvokrj",
//            isBestSafety = true,
//            isShopClosed = false,
//            offerSnackType = OfferSnackTypes.BASIC
//        )
//
//        val test = RestaurantsDao().addNewRestaurant(restaurant)
//        println(test)
    }
}