package com.paavam.swiggyapp.data

data class Food(
    val name: String,
    val foodType: FoodType,
    val starText: String?,
    val price: Float,
    val foodContents: String?,
    val imageUrl: String?
)

enum class FoodType {
    VEG, NON_VEG
}