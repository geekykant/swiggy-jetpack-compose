package com.paavam.swiggyapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.model.Food
import com.paavam.swiggyapp.model.FoodType
import com.paavam.swiggyapp.model.Restaurant
import com.paavam.swiggyapp.ui.component.AddToCartComposable
import com.paavam.swiggyapp.ui.component.GrayDivider
import com.paavam.swiggyapp.ui.navigation.NavScreen
import com.paavam.swiggyapp.ui.theme.Prox
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.UiUtils
import com.paavam.swiggyapp.viewmodel.CartViewModel
import com.paavam.swiggyapp.viewmodel.CartViewState
import com.skydoves.landscapist.glide.GlideImage
import java.util.*

@Composable
fun CartScreen(
    navController: NavController,
    outerPaddingValues: PaddingValues
) {
    val viewModel: CartViewModel = hiltViewModel()
    val viewState = viewModel.state.value

    when (viewState.cartFoodList.isEmpty()) {
        true -> NoItemsInCart(navController, outerPaddingValues)
        else -> ShowItemsInCart(viewState, navController, outerPaddingValues)
    }
}

@Composable
fun ShowItemsInCart(
    viewState: CartViewState,
    navController: NavController,
    outerPaddingValues: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier
            .padding(
                top = outerPaddingValues.calculateTopPadding(),
                bottom = outerPaddingValues.calculateBottomPadding()
            )
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 15.dp)
    ) {
        item {
            viewState.mainRestaurant?.let {
                BasicRestaurantDetails(it, Modifier.padding(horizontal = 15.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(items = viewState.cartFoodList) {
            CartFoodItem(
                food = it,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 15.dp)
            )
        }

        item {
            Divider(
                thickness = 0.75.dp,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
            )
        }

        item {
            OrderingSuggestions()
            GrayDivider(heightDp = 15.dp)
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 15.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_offers),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(0.2f)
                        .size(28.dp)
                )
                Text(
                    text = "APPLY COUPON",
                    maxLines = 1,
                    style = Typography.h2,
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 20.dp),
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(0.2f)
                        .size(28.dp),
                    tint = Color.LightGray
                )
            }
            GrayDivider(heightDp = 15.dp)
        }

        item {
            CartBillDetails(viewState)
        }

        item {
            GrayDivider(heightDp = 130.dp)
        }

    }
}

@Composable
fun CartBillDetails(viewState: CartViewState, modifier: Modifier = Modifier) {

}

@Composable
fun OrderingSuggestions(modifier: Modifier = Modifier) {
    val searchTextStyle = TextStyle(
        fontFamily = Prox,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )

    var searchText by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 15.dp)
    ) {
        Image(
            Icons.Outlined.List,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier,
            placeholder = {
                Text(
                    "Any restaurant request? We will try our best to convey it",
                    modifier = Modifier.fillMaxHeight(),
                    textAlign = TextAlign.Center,
                    style = searchTextStyle,
                    maxLines = 1
                )
            },
            textStyle = searchTextStyle,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            maxLines = 1
        )
    }
}

@Composable
fun CartFoodItem(food: Food, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_foodtype),
            contentDescription = null,
            tint = when (food.foodType) {
                FoodType.VEG -> Color(0xFF008000)
                FoodType.NON_VEG -> Color(0xFF008000)
            },
            modifier = Modifier.padding(end = 5.dp)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .weight(2f)
        ) {
            Text(
                text = food.name,
                style = Typography.h3,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
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
                modifier = Modifier
                    .weight(1f)
            )
        }

        Text(
            text = "₹${(food.price * food.quantityInCart)}",
            style = Typography.h4,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .weight(1f),
            textAlign = TextAlign.Right
        )
    }
}

@Composable
fun BasicRestaurantDetails(mainRestaurant: Restaurant, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        val placeholder = ImageBitmap.imageResource(UiUtils.fetchRandomPlaceholder())
        mainRestaurant.imageUrl?.let {
            GlideImage(
                imageModel = it,
                contentScale = ContentScale.FillWidth,
                placeHolder = placeholder,
                error = placeholder,
                modifier = Modifier
                    .size(60.dp, 60.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = mainRestaurant.name,
                    style = Typography.h2
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = mainRestaurant.location, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun NoItemsInCart(
    navController: NavController,
    outerPaddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(outerPaddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_no_food_in_cart),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(10.dp),
            contentScale = ContentScale.FillWidth
        )

        Text(
            "Good Food Is Always Cooking".uppercase(Locale.getDefault()),
            style = Typography.h4,
            modifier = Modifier
                .padding(vertical = 10.dp)
        )

        Text(
            "Your cart is empty.\nAdd something from the menu",
            modifier = Modifier.padding(vertical = 20.dp),
            textAlign = TextAlign.Center
        )

        OutlinedButton(
            onClick = {
                /* Open home page */
                navController.navigate(NavScreen.Home.route)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colors.primary
            ),
            contentPadding = PaddingValues(horizontal = 10.dp),
            border = BorderStroke(ButtonDefaults.OutlinedBorderSize, MaterialTheme.colors.primary),
        ) {
            Text(
                "Browse Restaurants".uppercase(Locale.getDefault()),
                style = Typography.body1,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }

    }
}

//@Preview("Search Area", showBackground = true)
//@Composable
//fun CartScreenPreview() {
//    SwiggyTheme {
//        CartScreen(rememberNavController(), outerPaddingValues = PaddingValues(10.dp))
//    }
//}
