package com.example.swiggyapp.data

import com.example.swiggyapp.R

data class Restaurant(
    val name: String,
    val dishTagline: String,
    val location: String,
    val distance: String,
    val rating: Float,
    val distanceTimeMinutes: Int,
    val averagePricingForTwo: Int,
    val imageUrl: String,
    val allOffers: List<Offer>?,
    val offerSnack: OfferSnack?,
    val isBestSafety: Boolean = false
){
    fun getLocationTagline() = "$location | $distance kms"
}

fun prepareRestaurants(): List<Restaurant> {
    var prepList = listOf(
        Restaurant(
            "Aryaas",
            "South Indian, Chineese, Arabian, North India",
            "Kakkanad",
            "7.2 kms",
            4.2f,
            53,
            400,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_200,h_220,c_fill/jmkzdtpvr6njj3wvokrj",
            listOf(Offer(R.drawable.ic_offers_filled, 40, 80, "40METOO", 129)),
            OfferSnack("40% OFF", OfferSnackType.BASIC)
        ), Restaurant(
            "McDonald's",
            "American, Continental, Fast Food, Desserts",
            "Edapally",
            "3.6 kms",
            3.2f,
            39,
            200,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_200,h_220,c_fill/ndxghfzqe4qc2dacxiwd",
            null,
            null
        ), Restaurant(
            "Hotel Matoshri",
            "Chinese, Fast Food",
            "Kaveri Hospital, Shingoli",
            "1.2 kms",
            3.8f,
            53,
            200,
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_200,h_220,c_fill/shxshuxficcjcwyixw0s",
            listOf(Offer(R.drawable.ic_offers_filled, 20, 180,"20OFFERPLOX", 600)),
            OfferSnack("20% OFF", OfferSnackType.INVERT_BASIC)
        )
    )

    for (i in 1..5) {
        prepList = prepList.plus(
            prepList[i % 3]
        )
    }
    return prepList
}


