package com.paavam.swiggyapp.ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
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

fun Modifier.gesturesDisabled(disabled: Boolean = true) =
    pointerInput(disabled) {
        // if gestures enabled, we don't need to interrupt
        if (!disabled) return@pointerInput

        awaitPointerEventScope {
            // we should wait for all new pointer events
            while (true) {
                awaitPointerEvent(pass = PointerEventPass.Initial)
                    .changes
                    .forEach(PointerInputChange::consumeAllChanges)
            }
        }
    }