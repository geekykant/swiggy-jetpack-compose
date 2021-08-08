package com.example.swiggyapp.data

import com.example.swiggyapp.R

object HomeRepository {
    fun preparePopularCurations() = listOf(
        Cuisine(
            "Pizzas",
            "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/b592fde4c60335141c86c5bd1756b7fd_l6gpcz.png"
        ),
        Cuisine(
            "Healthy Diet",
            "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/healthy_diet_xbjbmk.png"
        ),
        Cuisine(
            "Biryani",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/oywmb7ianhtutwcgnieg.png"
        ),
        Cuisine(
            "Coffee & Beverages",
            "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/croissant_uyk3zk.png"
        ),
        Cuisine(
            "Burgers",
            "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/burger_tkjka1.png"
        ),
        Cuisine(
            "Cakes & Desserts",
            "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/cakes_desserts_kj7nzw.png"
        ),
        Cuisine(
            "Chinese & Noodles",
            "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/pngwing.com_1_a4rsgy.png"
        ),
        Cuisine(
            "North Indian",
            "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/pngwing.com_soubv1.png"
        ),
    )

    fun prepareRestaurantFoods() = listOf(
        Food(
            "Veggie Delite Sub ( 15 cm, 6 Inch )",
            FoodType.VEG,
            "Bestseller",
            172f,
            "Delicious combination of garden fresh lettuce, tomatoes, green peppers, onions, olives and pickles. Served on freshly baked bread. 97% Fat Free.",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/awlvilhyeecsyubydsn5"
        ),
        Food(
            "Aloo Patty Sub ( 15 cm, 6 Inch )",
            FoodType.VEG,
            null,
            196f,
            "The traditional aloo patty seasoned with special herbs and spices with your choice of crisp fresh veggies, on freshly baked bread. New bread featured - Flatbread.",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/ge1oc1o4i1agi8pawbvt"
        ),
        Food(
            "Tandoori Chicken Tikka Sub ( 15 cm, 6 Inch )",
            FoodType.NON_VEG,
            null,
            224f,
            "Tandoori flavored chicken tikka",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/ixdq8qrujoxmcnvai65x"
        ),
        Food(
            "Cheesy Aloo Patty Sub (15 cm, 6 Inch)+ Cheesy aloo patty Sub (15 cm, 6 Inch)",
            FoodType.NON_VEG,
            "Bestseller",
            515f,
            "Price shown is after 10% discount. Aloo patty + four cheese slices.",
            "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_208,h_208,c_fit/xe0slbhk3podrwqufs5f"
        )
    )

    fun prepareHelloBarContent(): List<HelloBar> =
        listOf(
            HelloBar("Rainy weather! Additional fee will apply to reward delivery partners for being out on the streets"),
            HelloBar(
                "Why not let us cover the delivery fee on your food orders?",
                isClickable = true,
                navTarget = true
            )
        )

    fun prepareTilesContent(): List<QuickTile> =
        listOf(
            QuickTile(
                "Restaurant", "Enjoy your favourite treats",
                "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_220,h_220,c_fill/yy09xti5d3buoklibtuc"
            ),
            QuickTile(
                "Genie", "Anything you need, delivered",
                "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,q_auto,w_220,h_220,c_fill/ic_deliveryman_fstjt2"
            ),
            QuickTile(
                "Meat", "Fresh meat & seafood",
                "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,q_auto,w_220,h_220/pexels-geraud-pfeiffer-6542791_my49hm.jpg"
            ),
            QuickTile(
                "Book Shops", "Delivery from Book Shops",
                "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,q_auto,w_220,h_220,c_fill/pexels-martin-de-arriba-7171398_mtyiv4.jpg"
            ),
            QuickTile(
                "Care Corner", "Find essentials & help loved ones",
                "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,q_auto,w_220,h_220,c_fill/pexels-karolina-grabowska-4226773_ju5e0w.jpg"
            )
        )

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
                listOf(Offer(R.drawable.ic_offers_filled, 20, 180, "20OFFERPLOX", 600)),
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
}