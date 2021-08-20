package com.paavam.swiggyapp.ui.component.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.paavam.swiggyapp.ui.theme.Typography

@Composable
fun AnnouncementHeading(message: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp, 10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .width(9.dp)
                .height(60.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp))
                .background(MaterialTheme.colors.primary)
        ) { }
        Text(
            text = message,
            style = Typography.h1,
            color = Color.Black,
            modifier = modifier
                .padding(start = 15.dp, end = 3.dp)
                .align(Alignment.CenterVertically)
        )
    }
}