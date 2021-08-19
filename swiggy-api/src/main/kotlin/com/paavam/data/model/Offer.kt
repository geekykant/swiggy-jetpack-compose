package com.paavam.data.model

import com.paavam.data.entity.EntityOffer
import kotlinx.serialization.Serializable

@Serializable
data class Offer(
    val offer_id: String,
    val iconUrl: String?,
    val discountPercentage: Int,
    var upToLimit: Int?,
    val offerCode: String,
    val minLimit: Int?
) {
    fun isFieldsBlank(): Boolean {
        return offerCode.isBlank()
    }

    companion object {
        fun fromEntity(entity: EntityOffer): Offer = Offer(
            entity.id.value.toString(),
            entity.iconUrl,
            entity.discountPercentage,
            entity.upToLimit,
            entity.offerCode,
            entity.minLimit
        )

        fun checkEntityIDType(offerId: String): Boolean = offerId.toLongOrNull() != null
    }
}
