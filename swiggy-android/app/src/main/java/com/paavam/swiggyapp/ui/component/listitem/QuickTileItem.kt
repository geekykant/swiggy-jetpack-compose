package com.paavam.swiggyapp.ui.component.listitem

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.paavam.swiggyapp.ui.component.image.ImageWithPlaceholder
import com.paavam.swiggyapp.ui.theme.Typography

@Composable
fun QuickTileItem(heading: String, tagline: String, imageUrl: String) {
    val roundShape = RoundedCornerShape(15.dp)
    Column(
        modifier = Modifier
            .width(118.dp)
            .padding(5.dp)
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .shadow(2.dp, roundShape)
                .border(0.7.dp, Color(0x1A000000), shape = roundShape),
        ) {
            Text(
                text = heading,
                modifier = Modifier
                    .zIndex(2f)
                    .padding(horizontal = 2.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Typography.h2,
                fontWeight = FontWeight.ExtraBold
            )

            ImageWithPlaceholder(
                imageUrl = imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(roundShape),
            )
        }
        Text(
            text = tagline,
            modifier = Modifier
                .padding(vertical = 2.dp),
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}