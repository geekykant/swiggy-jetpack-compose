package com.paavam.swiggyapp.ui.component.listitem

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.core.data.model.Food
import com.paavam.swiggyapp.core.data.model.FoodType
import com.paavam.swiggyapp.ui.component.AddToCartComposable
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.addShimmer
import com.paavam.swiggyapp.ui.utils.noRippleClickable

@Composable
fun CartFoodItem(
    food: Food,
    isLoading: Boolean = false,
    onQuantityChange: (Int) -> Unit,
    onCustomizeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_foodtype),
            contentDescription = null,
            tint = when (food.foodType) {
                FoodType.VEG -> Color(0xFF008000)
                FoodType.NON_VEG -> Color(0xFF008000)
            },
            modifier = Modifier.alpha(if (isLoading) 0f else 1f)
        )

        Column {
            Text(
                text = food.name,
                style = Typography.h3,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .defaultMinSize(minWidth = 40.dp, minHeight = 2.dp)
                    .fillMaxWidth(0.45f)
                    .addShimmer(isLoading)
            )

            Text(
                "Pan Pizza",
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .defaultMinSize(minWidth = 20.dp, minHeight = 2.dp)
                    .addShimmer(isLoading)
            )

            if (food.hasCustomizationAvailable) {
                Row(
                    modifier = Modifier
                        .alpha(if (isLoading) 0f else 1f)
                        .run {
                            if (!isLoading) noRippleClickable(onCustomizeClick)
                            else this
                        }
                ) {
                    Text(
                        "CUSTOMIZE",
                    )
                    Icon(
                        painterResource(id = R.drawable.ic_expand),
                        null,
                        modifier = Modifier
                            .padding(horizontal = 3.dp)
                            .align(Alignment.CenterVertically)
                            .size(15.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }

        if (food.quantityInCart != 0 || isLoading) {
            AddToCartComposable(
                cartQuantity = if (isLoading) 0 else food.quantityInCart,
                onQuantityChange = {
                    onQuantityChange(it)
                },
                modifier = Modifier.padding(horizontal = 2.dp),
                isLoading = isLoading
            )
        }
        Text(
            text = "₹${(food.price * food.quantityInCart)}",
            style = Typography.h3,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(top = 5.dp)
                .defaultMinSize(minWidth = 15.dp, minHeight = 2.dp)
                .addShimmer(isLoading),
            textAlign = TextAlign.Right
        )
    }
}