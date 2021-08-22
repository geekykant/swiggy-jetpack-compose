package com.paavam.swiggyapp.ui.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.noRippleClickable

@Composable
fun DashedHoverText(
    heading: String,
    showHoverMessage: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Color(0xFF5790BB),
    style: TextStyle = Typography.h5
) {
    Text(
        text = heading,
        modifier = modifier
            .noRippleClickable { showHoverMessage() }
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, this.size.height + 10f),
                    end = Offset(this.size.width, this.size.height + 10f),
                    strokeWidth = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                )
            },
        color = textColor,
        style = style
    )
}