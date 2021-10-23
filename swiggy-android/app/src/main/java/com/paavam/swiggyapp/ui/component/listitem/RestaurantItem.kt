package com.paavam.swiggyapp.ui.component.listitem

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.Restaurant
import com.paavam.swiggyapp.core.data.offer.model.OfferSnackType
import com.paavam.swiggyapp.ui.component.BasicOfferSnackComposable
import com.paavam.swiggyapp.ui.component.FlatDealOfferSnackComposable
import com.paavam.swiggyapp.ui.component.image.ImageWithPlaceholder
import com.paavam.swiggyapp.ui.theme.Typography

@Composable
fun RestaurantItem(
    r: Restaurant,
    modifier: Modifier = Modifier,
    onRestaurantClick: () -> Unit
) {
    val height = 120.dp
    Row(
        modifier = modifier
            .clickable { onRestaurantClick() }
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .heightIn(0.dp, height)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 0.dp)
                .width(90.dp)
                .height(height),
        ) {
            r.imageUrl?.let {
                ImageWithPlaceholder(
                    imageUrl = it,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(110.dp)
                        .width(90.dp)
                        .clip(RoundedCornerShape(5.dp))
                    //TODO: if swiggymodel shows closed -> colorFilter = if (r.isShopClosed) UiUtils.shopClosedBlackFilter else null,
                )
            }

            val snackModifier = Modifier.align(Alignment.BottomCenter)
            val maxOffer = r.getMaxOffer()
            when (maxOffer?.offerType) {
                OfferSnackType.BASIC ->
                    BasicOfferSnackComposable(
                        message = r.getMaxOfferSnackMessage(),
                        invert = false,
                        modifier = snackModifier
                    )
                OfferSnackType.INVERT_BASIC ->
                    BasicOfferSnackComposable(
                        message = r.getMaxOfferSnackMessage(),
                        invert = true,
                        modifier = snackModifier
                    )
                OfferSnackType.FLAT_DEAL ->
                    FlatDealOfferSnackComposable(
                        message = r.getMaxOfferSnackMessage(),
                        modifier = snackModifier
                    )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 15.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = r.name,
                    style = Typography.h2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (r.isBestSafety) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_best_safety),
                        contentDescription = null,
                        modifier = Modifier
                            .width(85.dp)
                    )
                }
            }
            Text(
                text = r.dishTagline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = r.location, maxLines = 1)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_rating),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(
                    text = r.rating.toString(),
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.dp)
                )
                Text(
                    text = ".",
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.dp)
                )
                Text(
                    text = "₹${r.averagePricingForTwo} for two",
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.dp)
                )
            }

            if (!r.allOffers.isNullOrEmpty()) {
                Divider()
            }

            LazyColumn {
                if (!r.allOffers.isNullOrEmpty()) {
                    items(items = r.allOffers!!) { offer ->
                        Row {
                            Icon(
                                painterResource(id = R.drawable.ic_offers_filled),
                                contentDescription = null,
                                modifier = Modifier.padding(end = 5.dp),
                                tint = Color(0xFFFA5520)
                            )
                            Text(
                                text = offer.offerMessage(),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun RestaurantItemLarge(
    r: Restaurant,
    modifier: Modifier = Modifier,
    onRestaurantClick: () -> Unit
) {
    val height = 100.dp
    Column(
        modifier = modifier
            .clickable { onRestaurantClick() }
            .padding(horizontal = 0.dp, vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 0.dp)
                .fillMaxWidth()
                .sizeIn(maxHeight = height)
        ) {
            r.imageUrl?.let {
                ImageWithPlaceholder(
                    imageUrl = it,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(5.dp))
                    //TODO: if swiggymodel shows closed -> colorFilter = if (r.isShopClosed) UiUtils.shopClosedBlackFilter else null,
                )
            }

            val snackModifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 5.dp)
            r.getMaxOffer()?.run {
                BasicOfferSnackComposable(
                    message = r.getMaxOfferSnackMessage(),
                    invert = true,
                    textSize = 13.sp,
                    modifier = snackModifier
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 3.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = r.name,
                style = Typography.h2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_rating),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(
                    text = r.rating.toString(),
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.dp)
                )
                Text(
                    text = ".",
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.dp)
                )
                Text(
                    text = "41 mins",
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRestaurantItem() {
    RestaurantItem(r = PreviewData.prepareARestaurant(), onRestaurantClick = {

    })
}