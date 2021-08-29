package com.paavam.swiggyapp.core.data.model


class RestaurantFoodModel(
    val mainFoodSections: List<Any?>
)

data class MainSectionFoods(
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

data class SubSectionsFoods(
    val subSectionId: Int,
    val subSectionName: String,
    val foodList: List<Food>
) {
    fun getSubName() = "$subSectionName (${foodList.size})"
}