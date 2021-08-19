package com.paavam.swiggy.data.database.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import java.awt.SystemColor.text

object Offers : LongIdTable("offers", columnName = "offer_id") {
    val iconUrl = text("icon_url").nullable()
    val discountPercentage = integer("discount_percentage")
    var upToLimit = integer("upto_limit").nullable()
    val offerCode = text("offer_code")
    val minLimit = integer("min_limit").nullable()
}