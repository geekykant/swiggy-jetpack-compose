package com.paavam.swiggyapp.ui.component.listitem

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.core.data.food.model.Food
import com.paavam.swiggyapp.core.data.food.model.FoodType
import com.paavam.swiggyapp.ui.component.AddToCartComposable
import com.paavam.swiggyapp.ui.theme.Typography

@Composable
fun CartFoodItem(food: Food, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_foodtype),
            contentDescription = null,
            tint = when (food.foodType) {
                FoodType.VEG -> Color(0xFF008000)
                FoodType.NON_VEG -> Color(0xFF008000)
            }
        )

        Column {
            Text(
                text = food.name,
                style = Typography.h3,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth(0.45f)
            )

            Text(
                "Pan Pizza",
                modifier = Modifier.padding(vertical = 5.dp)
            )

            Row {
                Text("CUSTOMIZE")
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

        if (food.quantityInCart != 0) {
            AddToCartComposable(
                cartQuantity = food.quantityInCart,
                onQuantityChange = {
                    food.quantityInCart = it
                },
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        }
        Text(
            text = "₹${(food.price * food.quantityInCart)}",
            style = Typography.h3,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(top = 5.dp),
            textAlign = TextAlign.Right
        )
    }
}