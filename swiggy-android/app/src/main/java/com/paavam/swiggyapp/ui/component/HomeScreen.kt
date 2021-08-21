package com.paavam.swiggyapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paavam.swiggyapp.ui.theme.Typography

@Composable
fun FlatDealOfferSnackComposable(
    message: String,
    modifier: Modifier,
    roundShape: RoundedCornerShape = RoundedCornerShape(5.dp)
) {
    Text(
        text = message,
        maxLines = 1,
        style = Typography.h1,
        modifier = modifier
            .shadow(8.dp, roundShape)
            .background(Color.White, roundShape)
            .padding(horizontal = 10.dp, vertical = 3.dp),
        color = Color(0xFFFF5722),
        fontSize = 18.sp
    )
}

@Composable
fun BasicOfferSnackComposable(
    message: String,
    invert: Boolean,
    onClick: () -> Unit = {},
    modifier: Modifier
) {
    val roundShape = RoundedCornerShape(5.dp)
    var textColor = Color.White
    var backgroundColor = MaterialTheme.colors.primary
    var borderColor = Color(0x0)
    var fontWeight = FontWeight.Bold

    if (invert) {
        backgroundColor = Color.White
        textColor = MaterialTheme.colors.primary
        fontWeight = FontWeight.ExtraBold
        borderColor = Color(0x1A000000)
    }

    Text(
        text = message,
        maxLines = 1,
        style = Typography.h1,
        modifier = modifier
            .clickable { onClick() }
            .shadow(8.dp, roundShape)
            .background(backgroundColor, roundShape)
            .border(1.dp, borderColor, shape = roundShape)
            .padding(horizontal = 10.dp, vertical = 3.dp),
        color = textColor,
        fontSize = 15.sp,
        fontWeight = fontWeight
    )
}