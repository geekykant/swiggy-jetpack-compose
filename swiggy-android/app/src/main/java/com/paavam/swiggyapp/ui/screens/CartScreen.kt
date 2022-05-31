package com.paavam.swiggyapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.core.data.model.Food
import com.paavam.swiggyapp.core.data.model.FoodType
import com.paavam.swiggyapp.core.data.model.Restaurant
import com.paavam.swiggyapp.core.ui.UiState
import com.paavam.swiggyapp.ui.component.GrayDivider
import com.paavam.swiggyapp.ui.component.InputMessageField
import com.paavam.swiggyapp.ui.component.PlaceholderMessageOutlinedButtonScreen
import com.paavam.swiggyapp.ui.component.image.ImageWithPlaceholder
import com.paavam.swiggyapp.ui.component.listitem.CartFoodItem
import com.paavam.swiggyapp.ui.component.text.DashedHoverText
import com.paavam.swiggyapp.ui.navigation.NavScreen
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.addShimmer
import com.paavam.swiggyapp.ui.utils.addTopBarBottomLine
import com.paavam.swiggyapp.viewmodel.CartUiState
import com.paavam.swiggyapp.viewmodel.CartViewModel
import java.util.*
import kotlin.math.min
import kotlin.math.roundToInt

@ExperimentalFoundationApi
@Composable
fun CartScreen(
    navController: NavController,
    outerPadding: PaddingValues
) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartViewState = cartViewModel.state.collectAsState()

    val uiLoadingState = cartViewState.value.uiLoadingState
    ProvideWindowInsets {
        when (uiLoadingState) {
            is UiState.Loading -> LoadingCartScreen()
            is UiState.Failed -> ErrorCartScreen(
                outerPaddingValues = outerPadding,
                onRetryClick = {
                    /* Check for internet/error again */
                })
            is UiState.Success -> SuccessCartScreen(
                navController = navController,
                outerPaddingValues = outerPadding
            )
        }
    }
}

@Composable
fun LoadingCartScreen() {
    //implement later
    val restaurant = Restaurant(0L, "", "", "", "", 0f, 0, 0, "", emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 15.dp)
    ) {
        CartRestaurantDetails(
            restaurant,
            Modifier.padding(horizontal = 15.dp),
            isLoading = true
        )
        Spacer(modifier = Modifier.height(20.dp))
        CartFoodItem(
            food = Food(0L, "", FoodType.VEG, null, 0, "", ""),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 15.dp),
            isLoading = true,
            onQuantityChange = { },
            onCustomizeClick = { }
        )
        GrayDivider(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
    }
}

@Composable
fun ErrorCartScreen(
    outerPaddingValues: PaddingValues,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PlaceholderMessageOutlinedButtonScreen(
        title = "Connection Error",
        subHeading = "Something's not right. Please try checking both wifi/4G or try again later.",
        buttonText = "Retry",
        outlinedButtonClick = onRetryClick,
        outerPaddingValues = outerPaddingValues,
        modifier = modifier
    )
}

@ExperimentalFoundationApi
@Composable
fun SuccessCartScreen(
    navController: NavController,
    outerPaddingValues: PaddingValues
) {
    val cartViewModel: CartViewModel = hiltViewModel()

    val cartUiState = cartViewModel.state.collectAsState()
    when(cartUiState.value){
        is CartUiState.NoItemsInCart -> {
            PlaceholderMessageOutlinedButtonScreen(
                title = "Good Food Is Always Cooking",
                subHeading = "Your cart is empty.\nAdd something from the menu",
                outlinedButtonClick = {
                    /* Open home page */
                    navController.navigate(NavScreen.Home.route){
                        launchSingleTop = true
                    }
                },
                buttonText = "Browse Restaurants",
                outerPaddingValues = outerPaddingValues
            )
        }
        is CartUiState.HasItemsInCart -> {
            // Scrolling state
            val scrollState = rememberLazyListState()
            val isScrollStateChanged by remember {
                derivedStateOf {
                    scrollState.firstVisibleItemIndex != 0
                }
            }

            val cartValues = cartUiState.value as CartUiState.HasItemsInCart
            val position by animateFloatAsState(if (isScrollStateChanged) 0f else -45f)

            Box(Modifier.fillMaxSize()) {
                ShowItemsInCart(
                    viewState = cartUiState.value as CartUiState.HasItemsInCart,
                    scrollState = scrollState
                )
                CartTopAppBar(
                    viewState = cartValues,
                    onBackClick = {
                        navController.navigate(NavScreen.Home.route) {
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = min(1f, 1 + (position / 45f))
                            translationY = (position)
                        }
                        .navigationBarsPadding(bottom = false)
                )
            }
        }
    }
}

@Composable
fun CartTopAppBar(
    viewState: CartUiState.HasItemsInCart,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val restaurant = viewState.mainRestaurant
    val foods = viewState.cartFoodList
    val roundedOfMoneyToPay = viewState.totalAmount?.roundToInt()

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    restaurant?.let { r ->
                        Text(
                            text = r.name.uppercase(Locale.getDefault()),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )

                        Text(
                            text = if (r.isShopClosed) "Closed for delivery"
                            else
                                "${foods.sumOf { it.quantityInCart }} items, To pay: ₹${roundedOfMoneyToPay}",
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (r.isShopClosed) Color.Red else Color(0xD9000000)
                        )
                    }
                }
            }
        },
        backgroundColor = Color.White,
        modifier = modifier.addTopBarBottomLine(),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(painterResource(id = R.drawable.ic_left_arrow), null)
            }
        },
        elevation = 4.dp,
    )
}

@Composable
fun CartBillDetails(
    viewState: CartUiState.HasItemsInCart,
    modifier: Modifier = Modifier
) {
    val cartAmount = viewState.cartAmount

    cartAmount?.let {
        Column(
            modifier = modifier.padding(15.dp)
        ) {
            Text(
                text = "Bill Details",
                style = Typography.h4
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Item Total", style = Typography.h5)
                Text("₹${cartAmount.itemTotal}", style = Typography.h5)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(vertical = 0.dp)
                    .fillMaxWidth()
            ) {
                val paddingVertical = 10.dp
                Column(
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    DashedHoverText(
                        "Delivery Partner Fee for 9.70 kms",
                        showHoverMessage = {

                        },
                        modifier = Modifier.padding(vertical = paddingVertical)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text("Your Delivery Partner is travelling long distance to deliver your order")
                }
                Text(
                    "₹${cartAmount.deliveryFee}",
                    style = Typography.h5,
                    modifier = Modifier.padding(vertical = paddingVertical)
                )
            }

            Divider(
                thickness = 0.75.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Delivery Tip", style = Typography.h5)
                if (cartAmount.deliveryFee.equals(0f)) {
                    Text(
                        "Add tip",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.primary,
                        style = Typography.h5
                    )
                } else {
                    Text(
                        "₹${cartAmount.deliveryFee}",
                        style = Typography.h5
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 0.dp)
                    .fillMaxWidth()
            ) {
                Box {
                    DashedHoverText(
                        "Taxes and Charges",
                        showHoverMessage = {

                        },
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                }
                Text(
                    "₹${cartAmount.getTotalTaxCharges()}",
                    style = Typography.h5
                )
            }

            Divider(
                thickness = 0.75.dp,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "To Pay",
                    style = Typography.h4
                )
                Text(
                    "₹${cartAmount.toPayTotal()}",
                    style = Typography.h4
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ShowItemsInCart(
    viewState: CartUiState.HasItemsInCart,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val cartFoodList = viewState.cartFoodList
    val cartViewModel : CartViewModel = hiltViewModel()
    val mainRestaurant = viewState.mainRestaurant ?: throw Exception("Main Restaurant not found")

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 15.dp),
        state = scrollState
    ) {
        item {
            CartRestaurantDetails(mainRestaurant, Modifier.padding(horizontal = 15.dp))
            Spacer(modifier = Modifier.height(20.dp))
        }

        items(items = cartFoodList) { food ->
            CartFoodItem(
                food = food,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                onQuantityChange = { newQuantity ->
                    cartViewModel.notifyCartItemChange(food, newQuantity)
                },
                onCustomizeClick = { }
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
            var inputMessage by remember { mutableStateOf(viewState.customerOptionalMessageToShopkeeper ?: "") }
            InputMessageField(
                inputText = inputMessage,
                onTextChange = { newMessage ->
                    cartViewModel.setNewCustomerToShopKeeperMessage(newMessage)
                    inputMessage = newMessage
                },
                placeholderText = "Any restaurant request? We will try our best to convey it.",
            )
            GrayDivider(heightDp = 15.dp)
        }

        item {
            ApplyCouponButton()
            GrayDivider(heightDp = 15.dp)
        }

        item {
            CartBillDetails(viewState)
            GrayDivider(heightDp = 15.dp)
        }

        item {
            CancellationPolicy()
            GrayDivider(heightDp = 130.dp)
        }

    }
}

@Composable
fun ApplyCouponButton() {
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
            style = Typography.h4,
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
}

@Composable
fun CancellationPolicy(modifier: Modifier = Modifier) {
    var expandOverview by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(15.dp)
    ) {
        Row(
            modifier = modifier.padding(horizontal = 0.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                Icons.Default.List,
                contentDescription = null,
                modifier = Modifier
                    .size(26.dp)
            )
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    "Review your order and address details to avoid cancellations",
                    style = Typography.h4
                )

                if (!expandOverview) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append("Note: ")
                            }
                            append("If you choose to cancel, you can do it within 60 seconds after placing order. Post which you will be charged 100% cancellation fee.")
                        },
                        style = Typography.h5
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (expandOverview) {
            val messagesToIcons = linkedMapOf(
                "If you choose to cancel, you can do it within 60 seconds after placing the order" to Icons.Outlined.CheckCircle,
                "Post 60 seconds, you will be charged a 100% cancellation fee" to Icons.Outlined.Info,
                "However, in the event of an unusual delay of your order, you will not be charged a cancellation fee" to Icons.Outlined.Share,
                "This policy helps us avoid food wastage and compensate restaurants / delivery partners for their efforts." to Icons.Outlined.ThumbUp
            )
            messagesToIcons.toList().forEach {
                Row(
                    modifier = modifier.padding(horizontal = 0.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        it.second,
                        contentDescription = null,
                        modifier = Modifier
                            .size(26.dp),
                        tint = MaterialTheme.colors.primary.copy(0.7f)
                    )
                    Column(
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text(
                            it.first,
                            style = Typography.h5
                        )
                    }
                }
            }
        }

        Row(
            modifier = modifier.padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                Icons.Default.List,
                contentDescription = null,
                modifier = Modifier
                    .alpha(0f)
                    .size(26.dp)
            )
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Row {
                    val semiBoldStyle = Typography.h5.copy(fontWeight = FontWeight.SemiBold)

                    if (!expandOverview) {
                        DashedHoverText(
                            heading = "Overview",
                            showHoverMessage = { expandOverview = !expandOverview },
                            textColor = Color(0xFF3884BD),
                            style = semiBoldStyle
                        )

                        Spacer(modifier = Modifier.width(25.dp))
                    }

                    DashedHoverText(
                        heading = "Read Policy",
                        showHoverMessage = { /*TODO*/ },
                        textColor = Color(0xFF3884BD),
                        style = semiBoldStyle
                    )
                }
            }
        }
    }
}

@Composable
fun CartRestaurantDetails(
    mainRestaurant: Restaurant,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        mainRestaurant.imageUrl?.let {
            ImageWithPlaceholder(
                imageUrl = it,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(60.dp)
                    .addShimmer(isLoading)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = mainRestaurant.name,
                    style = Typography.h2,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 180.dp, minHeight = 8.dp)
                        .addShimmer(isLoading)
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = mainRestaurant.location,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 30.dp, minHeight = 5.dp)
                        .addShimmer(isLoading)
                )
            }
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
