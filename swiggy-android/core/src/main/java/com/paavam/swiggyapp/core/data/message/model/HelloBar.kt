package com.paavam.swiggyapp.core.data.message.model

data class HelloBar(
    val message: String,
    val isClickable: Boolean = false,
    val navTarget: Boolean = false
)