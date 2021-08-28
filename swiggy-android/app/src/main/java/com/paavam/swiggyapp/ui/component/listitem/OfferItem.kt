package com.paavam.swiggyapp.ui.component.listitem

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.offer.model.Offer
import com.paavam.swiggyapp.ui.component.image.OfferIconWithPlaceholder

@Composable
fun OfferWideItem(
    offer: Offer,
    modifier: Modifier = Modifier
) {
    val roundShape = RoundedCornerShape(5.dp)
    Column(
        modifier = modifier
            .padding(5.dp)
            .border(0.7.dp, Color(0x1A000000), shape = roundShape)
            .padding(8.dp),
    ) {
        Row(
            modifier = modifier.padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OfferIconWithPlaceholder(
                imageUrl = offer.iconUrl,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.size(16.dp, 16.dp)
            )
            Text(
                text = offer.offerMessage(),
                maxLines = 1,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    letterSpacing = (-0.5).sp,
                ),
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
        Text(
            text = offer.shortCodeInfo(),
            maxLines = 1,
            style = TextStyle(
                fontSize = 12.sp,
                letterSpacing = (-0.5).sp,
            ),
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOfferWideItem() {
    PreviewData.prepareARestaurant().getMaxOffer()?.let {
        OfferWideItem(offer = it)
    }
}