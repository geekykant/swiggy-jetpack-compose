package com.paavam.swiggyapp.ui.component.image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.paavam.swiggyapp.ui.utils.UiUtils
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun BannerImage(imageUrl: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clickable { }
            .padding(horizontal = 15.dp, vertical = 0.dp)
            .fillMaxWidth()
    ) {
        val placeholder = ImageBitmap.imageResource(UiUtils.fetchRandomPlaceholder())
        GlideImage(
            imageModel = imageUrl,
            contentScale = ContentScale.Crop,
            placeHolder = placeholder,
            error = placeholder,
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)),
        )
    }
}