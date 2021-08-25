package com.paavam.swiggyapp.ui.utils

import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
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

    val shopClosedBlackFilter = ColorFilter.colorMatrix(
        ColorMatrix(
            floatArrayOf(
                0.33f, 0.33f, 0.33f, 0f, 0f,
                0.33f, 0.33f, 0.33f, 0f, 0f,
                0.33f, 0.33f, 0.33f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
    )
}