package com.paavam.swiggyapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.ui.theme.Typography

@Composable
fun AddToCartComposable(
    cartQuantity: Int,
    onQuantityChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val roundShape = RoundedCornerShape(3.dp)
    val greenColor = Color(0xFF81C784)
    val backgroundColor = Color.White
    val fontWeight = FontWeight.ExtraBold
    val borderColor = Color(0x1A000000)

    if (cartQuantity > 0) {
        Row(
            modifier = modifier
                .clip(roundShape)
                .shadow(8.dp, roundShape)
                .background(backgroundColor, roundShape)
                .border(1.dp, borderColor, shape = roundShape),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_minus),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onQuantityChange(cartQuantity - 1) }
                    .padding(horizontal = 5.dp, vertical = 5.dp),
                tint = Color.LightGray
            )
            Text(
                text = cartQuantity.toString(),
                style = Typography.h3,
                color = greenColor,
                fontWeight = fontWeight,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .defaultMinSize(minWidth = 35.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onQuantityChange(cartQuantity + 1) }
                    .padding(horizontal = 5.dp, vertical = 3.dp),
                tint = greenColor
            )
        }
    } else {
        Text(
            text = "ADD",
            maxLines = 1,
            style = Typography.h1,
            modifier = modifier
                .clip(roundShape)
                .shadow(8.dp, roundShape)
                .clickable { onQuantityChange(cartQuantity + 1) }
                .background(backgroundColor, roundShape)
                .border(1.dp, borderColor, shape = roundShape)
                .padding(horizontal = 10.dp, vertical = 5.dp),
            color = greenColor,
            fontSize = 15.sp,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    }
}