package com.paavam.swiggyapp.ui.component.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.ui.utils.UiUtils
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImageWithPlaceholder(
    imageUrl: String,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
) {
    val placeholder = ImageBitmap.imageResource(UiUtils.fetchRandomPlaceholder())
    GlideImage(
        imageModel = imageUrl,
        contentScale = contentScale,
        placeHolder = placeholder,
        error = placeholder,
        modifier = modifier,
        colorFilter = colorFilter
    )
}

@Composable
fun OfferIconWithPlaceholder(
    imageUrl: String?,
    contentScale: ContentScale,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
) {
    val offerPlaceholderIcon = ImageVector.vectorResource(id = R.drawable.ic_offers_filled)
    GlideImage(
        imageModel = imageUrl ?: offerPlaceholderIcon,
        contentScale = contentScale,
        placeHolder = offerPlaceholderIcon,
        error = offerPlaceholderIcon,
        modifier = modifier,
        colorFilter = colorFilter
    )
}