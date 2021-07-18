package com.example.swiggyapp.data

data class QuickTile(
    val title: String,
    val tagLine: String,
    val imageUrl: String
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