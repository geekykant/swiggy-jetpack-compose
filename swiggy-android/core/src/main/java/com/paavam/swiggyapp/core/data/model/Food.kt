package com.paavam.swiggyapp.core.data.model

data class Food(
    val foodId: Long,
    val name: String,
    val foodType: FoodType,
    val starText: String?,
    val price: Int,
    val foodContents: String?,
    val imageUrl: String?,
    var quantityInCart: Int = 0,
    var hasCustomizationAvailable: Boolean = false,
) {
    fun hasFoodInCart(): Boolean = equals(quantityInCart)
}

enum class FoodType {
    VEG, NON_VEG
}