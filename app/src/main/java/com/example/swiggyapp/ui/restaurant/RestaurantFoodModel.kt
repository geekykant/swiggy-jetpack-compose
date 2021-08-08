package com.example.swiggyapp.ui.restaurant

import com.example.swiggyapp.data.Food

data class RestaurantFoodModel(
    val mainFoodSections: List<Any?>
)

internal data class MainSectionFoods(
    val mainSectionId: Int,
    val mainSectionName: String,
    val subFoodSections: List<SubSectionsFoods>?
) {
    fun getMainName(): String {
        subFoodSections?.let {
            return "$mainSectionName (${subFoodSections.sumOf { it.foodList.size }})"
        }
        return mainSectionName
    }

}

internal data class SubSectionsFoods(
    val subSectionId: Int,
    val subSectionName: String,
    val foodList: List<Food>
) {
    fun getSubName() = "$subSectionName (${foodList.size})"
}