package com.example.swiggyapp.ui.restaurant

data class FoodModel(
    val name: String,
    val foodType: FoodType,
    val starText: String?,
    val price: Float,
    val foodContents: String?,
    val imageUrl: String
)

enum class FoodType {
    VEG, NON_VEG
}

fun prepareRestaurantFoods() = listOf(
    FoodModel(
        "Veggie Delite Sub ( 15 cm, 6 Inch )",
        FoodType.VEG,
        "Bestseller",
        172f,
        "Delicious combination of garden fresh lettuce, tomatoes, green peppers, onions, olives and pickles. Served on freshly baked bread. 97% Fat Free.",
        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/awlvilhyeecsyubydsn5"
    ),
    FoodModel(
        "Aloo Patty Sub ( 15 cm, 6 Inch )",
        FoodType.VEG,
        null,
        196f,
        "The traditional aloo patty seasoned with special herbs and spices with your choice of crisp fresh veggies, on freshly baked bread. New bread featured - Flatbread.",
        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/ge1oc1o4i1agi8pawbvt"
    ),
    FoodModel(
        "Tandoori Chicken Tikka Sub ( 15 cm, 6 Inch )",
        FoodType.NON_VEG,
        null,
        224f,
        "Tandoori flavored chicken tikka",
        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/ixdq8qrujoxmcnvai65x"
    ),
    FoodModel(
        "Cheesy Aloo Patty Sub (15 cm, 6 Inch)+ Cheesy aloo patty Sub (15 cm, 6 Inch)",
        FoodType.NON_VEG,
        "Bestseller",
        515f,
        "Price shown is after 10% discount. Aloo patty + four cheese slices.",
        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/xe0slbhk3podrwqufs5f"
    )
)

data class RestaurantFoodModel(
    val recommendedFoods: MainSectionFoods?,
    val mainFoodSections: List<MainSectionFoods>?
)

data class MainSectionFoods(
    val mainSectionId: Int,
    val mainSectionName: String,
    val mainFoodSections: List<SubSectionsFoods>?,
    val isExpandable: Boolean = false,
    val subSectionsFoods: List<FoodModel>? = null
) {
    fun getMainName(): String {
        subSectionsFoods?.let {
            return "$mainSectionName (${subSectionsFoods.size})"
        }
        mainFoodSections?.let {
            return "$mainSectionName (${mainFoodSections.sumOf { it.foodList.size }})"
        }
        return mainSectionName
    }

}

data class SubSectionsFoods(
    val subSectionId: Int,
    val subSectionName: String,
    val foodList: List<FoodModel>
) {
    fun getSubName() = "$subSectionName (${foodList.size})"
}

fun prepareAllRestaurantFoods() = RestaurantFoodModel(
    recommendedFoods = MainSectionFoods(
        31,
        "Recommended",
        null,
        isExpandable = true,
        subSectionsFoods = prepareRestaurantFoods().subList(0, 2)
    ),
    mainFoodSections = listOf(
        MainSectionFoods(
            mainSectionId = 11,
            mainSectionName = "South India",
            mainFoodSections = listOf(
                SubSectionsFoods(12, "Dosas", prepareRestaurantFoods()),
                SubSectionsFoods(13, "Uttapams", prepareRestaurantFoods()),
                SubSectionsFoods(14, "Kerala Curries", prepareRestaurantFoods()),
                SubSectionsFoods(15, "Appam", prepareRestaurantFoods()),
            )
        ),
        MainSectionFoods(
            mainSectionId = 21,
            mainSectionName = "Quick Bites / Kerala Snacks",
            mainFoodSections = listOf(
                SubSectionsFoods(22, "Munchies", prepareRestaurantFoods()),
                SubSectionsFoods(23, "Chaats", prepareRestaurantFoods()),
                SubSectionsFoods(24, "Pav Bhaji", prepareRestaurantFoods()),
            )
        )
    )
)