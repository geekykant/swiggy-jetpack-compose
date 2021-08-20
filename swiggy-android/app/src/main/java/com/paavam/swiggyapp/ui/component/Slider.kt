package com.paavam.swiggyapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paavam.swiggyapp.ui.utils.simpleHorizontalScrollbar

/**
 * Horizontal Slider  - tracks scroll on LazyRow.
 */
@Composable
fun HorizontalSliderUi(
    horizontalScrollState: LazyListState,
    modifier: Modifier = Modifier
) {

    val roundShape = RoundedCornerShape(5.dp)
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "",
            modifier = Modifier
                .height(3.5.dp)
                .simpleHorizontalScrollbar(horizontalScrollState)
                .background(Color(0x1A000000), roundShape)
                .fillMaxWidth(0.15f),
        )
    }
}