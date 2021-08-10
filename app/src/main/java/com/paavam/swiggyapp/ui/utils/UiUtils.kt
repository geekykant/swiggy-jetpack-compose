package com.paavam.swiggyapp.ui.utils

import com.paavam.swiggyapp.R
import javax.inject.Singleton

@Singleton
object UiUtils {
    private val placeholderImagesList = listOf(
        R.drawable.red_placeholder,
        R.drawable.brown_placeholder,
        R.drawable.green_placeholder,
        R.drawable.blue_placeholder,
    )

    fun fetchRandomPlaceholder() = placeholderImagesList.random()
}