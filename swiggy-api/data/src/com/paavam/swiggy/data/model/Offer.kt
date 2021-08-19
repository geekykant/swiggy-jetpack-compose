package com.paavam.swiggy.data.model

import com.paavam.swiggy.data.entity.EntityOffer
import kotlinx.serialization.Serializable

@Serializable
data class Offer(
    val offer_id: String? = null,
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

        fun isValidEntityIDType(offerId: String): Boolean = offerId.toLongOrNull() != null
    }
}
