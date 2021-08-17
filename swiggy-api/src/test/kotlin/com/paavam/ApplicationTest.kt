package com.paavam

import com.paavam.data.dao.RestaurantsDao
import com.paavam.data.database.initDatabase
import com.paavam.data.model.Restaurant
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

//        val offer1 = transaction {
//            Offer.new {
//                offerCode = "OKSMAINA"
//                discountPercentage = 30
//            }
//        }
//        val offer2 = transaction {
//            Offer.new {
//                offerCode = "OKSMAINA"
//                discountPercentage = 30
//            }
//        }
//
//        val myrest = transaction {
//            Restaurant.new {
//                name = "Aryas"
//                location = "Edapally"
//            }
//        }
//
//        transaction {
//            myrest.allOffer = SizedCollection(listOf(offer1, offer2))
//        }

        val restaurant = Restaurant(
            restaurant_id = null,
            "Pizza House",
            "South Indian, Chineese, Arabian, North India",
            "Kakkanad",
            "7.2 kms",
            4.2f,
            53,
            400,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_200,h_220,c_fill/jmkzdtpvr6njj3wvokrj",
            isBestSafety = true,
            isShopClosed = false
        )

        val test = RestaurantsDao().addNewRestaurant(restaurant)
        println(test.toString())
    }
}