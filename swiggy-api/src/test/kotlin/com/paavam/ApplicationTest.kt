package com.paavam

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

    }
}