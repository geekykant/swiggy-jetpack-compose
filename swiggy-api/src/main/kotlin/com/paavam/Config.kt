package com.paavam

import io.ktor.config.*

class Config constructor(config: ApplicationConfig) {
    val JWT_SECRET_KEY =  "YsOke" //config.property("jwt_secret_key").getString()
    val HMAC_SECRET_KEY =  "MPokeNs" //config.property("jwt_secret_key").getString()

    val DATABASE_HOST = "localhost"// config.property("db_host").getString()
    val DATABASE_PORT = "5432" //config.property("dB_port").getString()
    val DATABASE_NAME =  "swiggy_db" //config.property("db_name").getString()
    val DATABASE_USER = "sreekant" //config.property("db_user").getString()
    val DATABASE_PASSWORD = "" // config.property("db_password").getString()
}