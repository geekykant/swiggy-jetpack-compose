package com.paavam.swiggyapp.ui.component.listitem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paavam.swiggyapp.core.data.model.Cuisine
import com.paavam.swiggyapp.ui.component.image.ImageWithPlaceholder

@Composable
fun CuisineItem(
    cuisine: Cuisine,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 13.sp,
    bottomTextPadding: PaddingValues = PaddingValues(top = 5.dp)
) {
    Column(
        modifier = modifier.padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ImageWithPlaceholder(
            imageUrl = cuisine.imageUrl,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .defaultMinSize(minHeight = 90.dp)
                .fillMaxWidth()
                .shadow(0.dp, CircleShape),
            colorFilter = null
        )
        Text(
            text = cuisine.name,
            modifier = Modifier
                .padding(bottomTextPadding)
                .height(30.dp),
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            overflow = TextOverflow.Ellipsis
        )
    }
}