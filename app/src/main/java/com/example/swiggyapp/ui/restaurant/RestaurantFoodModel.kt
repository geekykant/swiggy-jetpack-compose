package com.example.swiggyapp.ui.restaurant

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
    val foodList: List<FoodModel>
) {
    fun getSubName() = "$subSectionName (${foodList.size})"
}

fun prepareAllRestaurantFoods() = RestaurantFoodModel(
    mainFoodSections = listOf(
        SubSectionsFoods(
            31,
            "Recommended",
            prepareRestaurantFoods().subList(0, 2)
        ),
        MainSectionFoods(
            mainSectionId = 11,
            mainSectionName = "South India",
            subFoodSections = listOf(
                SubSectionsFoods(12, "Dosas", prepareRestaurantFoods()),
                SubSectionsFoods(13, "Uttapams", prepareRestaurantFoods()),
                SubSectionsFoods(14, "Kerala Curries", prepareRestaurantFoods()),
                SubSectionsFoods(15, "Appam", prepareRestaurantFoods()),
            )
        ),
        MainSectionFoods(
            mainSectionId = 21,
            mainSectionName = "Quick Bites / Kerala Snacks",
            subFoodSections = listOf(
                SubSectionsFoods(22, "Munchies", prepareRestaurantFoods()),
                SubSectionsFoods(23, "Chaats", prepareRestaurantFoods()),
                SubSectionsFoods(24, "Pav Bhaji", prepareRestaurantFoods()),
            )
        )
    )
)