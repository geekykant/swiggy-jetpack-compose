package com.paavam.data.database

import com.paavam.data.database.tables.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun initDatabase(
    host: String,
    port: String,
    databaseName: String,
    user: String,
    password: String
) {
    val tables = arrayOf(Users, Offers, Restaurants, RestaurantWithOffers, Foods);

    Database.connect(
        url = "jdbc:postgresql://$host:$port/$databaseName",
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(*tables)
        addLogger(StdOutSqlLogger)
    }
}