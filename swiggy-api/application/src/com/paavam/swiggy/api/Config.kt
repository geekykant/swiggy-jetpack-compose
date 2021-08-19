package com.paavam.swiggy.api

import io.ktor.config.*

class Config constructor(config: ApplicationConfig) {
    val JWT_SECRET_KEY = config.property("key.jwt_secret").getString()
    val HMAC_SECRET_KEY = config.property("key.hmac_secret").getString()

    val DATABASE_HOST = config.property("database.host").getString()
    val DATABASE_PORT = config.property("database.port").getString()
    val DATABASE_NAME = config.property("database.name").getString()
    val DATABASE_USER = config.property("database.user").getString()
    val DATABASE_PASSWORD = config.property("database.password").getString()
}