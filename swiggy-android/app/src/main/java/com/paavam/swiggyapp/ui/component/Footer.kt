package com.paavam.swiggyapp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.UiUtils
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FooterStaticImage(
    modifier: Modifier = Modifier,
    imageUrl: String = "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto/footer_graphic_vxojqs",
) {
    val placeholder = ImageBitmap.imageResource(UiUtils.fetchRandomPlaceholder())
    GlideImage(
        imageModel = imageUrl,
        contentScale = ContentScale.FillWidth,
        placeHolder = placeholder,
        error = placeholder,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun FooterLicenseInfo(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(bottom = 130.dp)
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        val washedGray = Color(0x4D000000)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_fssai_logo),
                contentDescription = null,
                modifier = modifier.width(50.dp),
                tint = washedGray
            )
            Text(
                "License No. 11316007000189",
                style = Typography.h2,
                modifier = Modifier.padding(horizontal = 10.dp),
                color = washedGray
            )
        }

        Divider(
            thickness = 1.dp,
            modifier = Modifier
                .padding(vertical = 15.dp)
                .fillMaxWidth()
        )

        Text(
            "Pizza Hut",
            style = Typography.h4,
            color = washedGray
        )
        Text("(Outlet:Lulu Mall)")
        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_location),
                contentDescription = null,
                modifier = Modifier
                    .width(12.dp),
                tint = washedGray
            )
            Text(
                "Pizza hut, Lulu Shopping Mall, 3rd Floor, Edapally, Cochin - 24",
                color = washedGray,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
    }
}