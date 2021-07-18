package com.example.swiggyapp.data

data class HelloBar(
    val message: String,
    val isClickable: Boolean = false,
    val navTarget: Boolean = false
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