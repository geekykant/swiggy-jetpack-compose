package com.example.swiggyapp.data

import com.example.swiggyapp.R
import com.example.swiggyapp.ui.restaurant.MainSectionFoods
import com.example.swiggyapp.ui.restaurant.RestaurantFoodModel
import com.example.swiggyapp.ui.restaurant.SubSectionsFoods

object RestaurantRepository {
    fun prepareARestaurant() = Restaurant(
        "Aryaas",
        "South Indian, Chineese, Arabian, North India",
        "Kakkanad",
        "7.2 kms",
        4.2f,
        53,
        400,
        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_200,h_220,c_fill/jmkzdtpvr6njj3wvokrj",
        listOf(
            Offer(R.drawable.ic_offers_filled, 40, 80, "40METOO", 129),
            Offer(R.drawable.ic_offers_filled, 20, 180, "20OFFERPLOX", 600)
        ),
        OfferSnack("40% OFF", OfferSnackType.BASIC)
    )

    fun prepareAllRestaurantFoods() = RestaurantFoodModel(
        mainFoodSections = listOf(
            SubSectionsFoods(
                31,
                "Recommended",
                HomeRepository.prepareRestaurantFoods().subList(0, 2)
            ),
            MainSectionFoods(
                mainSectionId = 11,
                mainSectionName = "South India",
                subFoodSections = listOf(
                    SubSectionsFoods(12, "Dosas", HomeRepository.prepareRestaurantFoods()),
                    SubSectionsFoods(13, "Uttapams", HomeRepository.prepareRestaurantFoods()),
                    SubSectionsFoods(
                        14,
                        "Kerala Curries",
                        HomeRepository.prepareRestaurantFoods()
                    ),
                    SubSectionsFoods(15, "Appam", HomeRepository.prepareRestaurantFoods()),
                )
            ),
            MainSectionFoods(
                mainSectionId = 21,
                mainSectionName = "Quick Bites / Kerala Snacks",
                subFoodSections = listOf(
                    SubSectionsFoods(22, "Munchies", HomeRepository.prepareRestaurantFoods()),
                    SubSectionsFoods(23, "Chaats", HomeRepository.prepareRestaurantFoods()),
                    SubSectionsFoods(24, "Pav Bhaji", HomeRepository.prepareRestaurantFoods()),
                )
            )
        )
    )
}
