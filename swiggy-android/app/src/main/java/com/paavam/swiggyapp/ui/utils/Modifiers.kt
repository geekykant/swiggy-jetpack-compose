package com.paavam.swiggyapp.ui.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@SuppressLint("UnnecessaryComposedModifier")
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.simpleHorizontalScrollbar(
    state: LazyListState,
    height: Dp = 3.5.dp
): Modifier = composed {
    composed {
        drawWithContent {
            drawContent()
            val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

            // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
            if (firstVisibleElementIndex != null) {
                val elementWidth = this.size.width / state.layoutInfo.totalItemsCount
                val scrollbarOffsetX = firstVisibleElementIndex * elementWidth
                val scrollbarWidth = state.layoutInfo.visibleItemsInfo.size * elementWidth

                drawRoundRect(
                    color = Color.Red,
                    topLeft = Offset(scrollbarOffsetX, 0f),
                    size = Size(scrollbarWidth, height.toPx()),
                    cornerRadius = CornerRadius(15.dp.toPx(), 15.dp.toPx())
                )
            }
        }
    }
}

fun Modifier.addShimmer(visible: Boolean) = composed {
    placeholder(
        visible = visible,
        highlight = PlaceholderHighlight.shimmer(),
        color = Color(0x66DDDDDD)
    )
}