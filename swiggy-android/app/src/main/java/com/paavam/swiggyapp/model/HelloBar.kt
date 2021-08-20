package com.paavam.swiggyapp.model

data class HelloBar(
    val message: String,
    val isClickable: Boolean = false,
    val navTarget: Boolean = false
)