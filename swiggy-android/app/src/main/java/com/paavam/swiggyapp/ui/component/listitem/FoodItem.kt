package com.paavam.swiggyapp.ui.component.listitem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.model.Food
import com.paavam.swiggyapp.model.FoodType
import com.paavam.swiggyapp.ui.component.AddToCartComposable
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.UiUtils
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FoodItem(
    food: Food,
    isShopClosed: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth(0.65f),
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_foodtype),
                    contentDescription = null,
                    tint = when (food.foodType) {
                        FoodType.VEG -> Color(0xFF008000)
                        FoodType.NON_VEG -> Color(0xFF008000)
                    },
                    modifier = Modifier.padding(end = 5.dp)
                )

                food.starText?.let {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_rating),
                        contentDescription = null,
                        tint = Color(0xFFFFC300)
                    )
                    Text(
                        text = it,
                        modifier = Modifier.padding(horizontal = 3.dp),
                        color = Color(0xFFFFC300)
                    )
                }
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = food.name,
                style = Typography.h2,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "₹${food.price}"
            )
            Spacer(modifier = Modifier.height(3.dp))
            food.foodContents?.let {
                Text(
                    text = it,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        val height = 120.dp
        Box(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 0.dp)
                .height(height),
            contentAlignment = Alignment.TopStart
        ) {
            val placeholder = ImageBitmap.imageResource(UiUtils.fetchRandomPlaceholder())
            food.imageUrl?.let {
                GlideImage(
                    imageModel = it,
                    contentScale = ContentScale.Crop,
                    placeHolder = placeholder,
                    error = placeholder,
                    modifier = androidx.compose.ui.Modifier
                        .height(110.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxWidth(),
                    colorFilter = if (isShopClosed) ColorFilter.colorMatrix(
                        ColorMatrix(
                            floatArrayOf(
                                0.33f, 0.33f, 0.33f, 0f, 0f,
                                0.33f, 0.33f, 0.33f, 0f, 0f,
                                0.33f, 0.33f, 0.33f, 0f, 0f,
                                0f, 0f, 0f, 1f, 0f
                            )
                        )
                    ) else null
                )
            }

            AddToCartComposable(
                cartQuantity = food.quantityInCart,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .align(Alignment.BottomCenter)
                    .alpha(if (isShopClosed) 0f else 1f),
                onQuantityChange = {
                    food.quantityInCart = it
                }
            )
        }
    }
    Divider(
        modifier = modifier.padding(horizontal = 15.dp),
        thickness = 0.5.dp
    )
}