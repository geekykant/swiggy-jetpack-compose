package com.paavam.swiggyapp.data

data class Food(
    val foodId: Long,
    val name: String,
    val foodType: FoodType,
    val starText: String?,
    val price: Float,
    val foodContents: String?,
    val imageUrl: String?,
    val quantityInCart: Int = 0
) {
    fun hasFoodInCart(): Boolean = equals(quantityInCart)
}

enum class FoodType {
    VEG, NON_VEG
}