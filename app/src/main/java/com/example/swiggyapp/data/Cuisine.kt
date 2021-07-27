package com.example.swiggyapp.data

data class Cuisine(
    val name: String,
    val imageUrl: String
)

fun prepareSearchCuisines() = listOf(
    Cuisine("Biriyani", "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/oywmb7ianhtutwcgnieg.png"),
    Cuisine("Pizzas", "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/b592fde4c60335141c86c5bd1756b7fd_l6gpcz.png"),
    Cuisine("Cakes & Desserts", "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/cakes_desserts_kj7nzw.png"),
    Cuisine("North Indian", "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/pngwing.com_soubv1.png"),
)

fun preparePopularCurations() = listOf(
    Cuisine("Pizzas", "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/b592fde4c60335141c86c5bd1756b7fd_l6gpcz.png"),
    Cuisine("Healthy Diet", "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/healthy_diet_xbjbmk.png"),
    Cuisine("Biryani", "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/oywmb7ianhtutwcgnieg.png"),
    Cuisine("Coffee & Beverages", "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/croissant_uyk3zk.png"),
    Cuisine("Burgers", "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/burger_tkjka1.png"),
    Cuisine("Cakes & Desserts", "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/cakes_desserts_kj7nzw.png"),
    Cuisine("Chinese & Noodles", "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/pngwing.com_1_a4rsgy.png"),
    Cuisine("North Indian", "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,w_200,h_200,c_fill/r_max/pngwing.com_soubv1.png"),
)