package com.example.swiggyapp.ui.restaurant

data class FoodItem(
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
    FoodItem(
        "Veggie Delite Sub ( 15 cm, 6 Inch )",
        FoodType.VEG,
        "Bestseller",
        172f,
        "Delicious combination of garden fresh lettuce, tomatoes, green peppers, onions, olives and pickles. Served on freshly baked bread. 97% Fat Free.",
        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/awlvilhyeecsyubydsn5"
    ),
    FoodItem(
        "Aloo Patty Sub ( 15 cm, 6 Inch )",
        FoodType.VEG,
        null,
        196f,
        "The traditional aloo patty seasoned with special herbs and spices with your choice of crisp fresh veggies, on freshly baked bread. New bread featured - Flatbread.",
        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/ge1oc1o4i1agi8pawbvt"
    ),
    FoodItem(
        "Tandoori Chicken Tikka Sub ( 15 cm, 6 Inch )",
        FoodType.NON_VEG,
        null,
        224f,
        "Tandoori flavored chicken tikka",
        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/ixdq8qrujoxmcnvai65x"
    ),
    FoodItem(
        "Cheesy Aloo Patty Sub (15 cm, 6 Inch)+ Cheesy aloo patty Sub (15 cm, 6 Inch)",
        FoodType.NON_VEG,
        "Bestseller",
        515f,
        "Price shown is after 10% discount. Aloo patty + four cheese slices.",
        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/xe0slbhk3podrwqufs5f"
    )
)