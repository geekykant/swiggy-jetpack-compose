package com.paavam.swiggyapp.model

data class Food(
    val foodId: Long,
    val name: String,
    val foodType: FoodType,
    val starText: String?,
    val price: Float,
    val foodContents: String?,
    val imageUrl: String?,
    var quantityInCart: Int = 0
) {
    fun hasFoodInCart(): Boolean = equals(quantityInCart)
}

enum class FoodType {
    VEG, NON_VEG
}