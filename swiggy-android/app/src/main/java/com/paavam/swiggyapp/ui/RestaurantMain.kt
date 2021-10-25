package com.paavam.swiggyapp.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.*
import com.paavam.swiggyapp.core.data.offer.model.Offer
import com.paavam.swiggyapp.ui.component.DashedDivider
import com.paavam.swiggyapp.ui.component.FooterLicenseInfo
import com.paavam.swiggyapp.ui.component.GrayDivider
import com.paavam.swiggyapp.ui.component.HorizontalSliderUi
import com.paavam.swiggyapp.ui.component.anim.AnimateTextUpDown
import com.paavam.swiggyapp.ui.component.listitem.FoodItem
import com.paavam.swiggyapp.ui.component.listitem.OfferWideItem
import com.paavam.swiggyapp.ui.component.text.SectionHeading
import com.paavam.swiggyapp.ui.screens.TopHelloBar
import com.paavam.swiggyapp.ui.theme.Prox
import com.paavam.swiggyapp.ui.theme.SwiggyTheme
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.addShimmer
import com.paavam.swiggyapp.ui.utils.noRippleClickable
import com.paavam.swiggyapp.viewmodel.RestaurantViewModel
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun RestaurantMain(
    restaurantId: Long,
    mainNavController: NavController
) {
    SwiggyTheme {
        val viewModel = RestaurantViewModel(restaurantId = restaurantId)
        val scrollState = rememberLazyListState()
        var isScrollStateChanged by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                isScrollStateChanged = scrollState.firstVisibleItemScrollOffset != 0
                RestaurantPageTopAppBar(
                    viewModel,
                    isScrollStateChanged,
                    onBackClick = {
                        mainNavController.navigateUp()
                    }
                )
            },
            floatingActionButton = { BottomQuickMenu() },
            floatingActionButtonPosition = FabPosition.Center
        ) { outerPadding ->
            RestaurantContent(
                viewModel,
                scrollState,
                outerPadding,
                Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun RestaurantContent(
    viewModel: RestaurantViewModel,
    scrollState: LazyListState, // Higher level is invoked, and reflected throughout
    outerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val viewState = viewModel.state.collectAsState()

    //implement it
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        contentPadding = PaddingValues(
            top = outerPadding.calculateTopPadding(),
            bottom = outerPadding.calculateBottomPadding()
        )
    ) {
        item {
            viewState.value.restaurant?.let { RestaurantBasicDetails(restaurant = it) }
            TopHelloBar(listOf(HelloBar("It's raining. To compensate your delivery partner, additional ₹12 delivery fee will apply")))
            DashedDivider(modifier = Modifier.padding(vertical = 5.dp))
        }

        item {
            viewState.value.restaurant?.let { ThreeSectionDetails(it) }
            BestSafetyCard()
            DashedDivider(modifier = Modifier.padding(vertical = 10.dp))
            viewState.value.restaurant?.allOffers?.let {
                RestaurantOffersRows(
                    rOffersList = it
                )
            }
            GrayDivider()
        }

        item {
            PureVegComposable()
        }

        item {
            FoodSectionListView(viewModel = viewModel)
        }

        item {
            GrayDivider()
        }

        item {
            FooterLicenseInfo(
                modifier = Modifier
                    .background(Color(0xD000000))
            )
        }
    }
}

@Composable
fun FoodSectionListView(
    viewModel: RestaurantViewModel,
) {
    val state = viewModel.state.collectAsState()
    val expandedState = state.value.expandedSectionIds.collectAsState()
    val restaurantFoods = state.value.restaurantFoods

    restaurantFoods?.mainFoodSections.let {
        it?.forEach { section ->
            when (section) {
                is SubSectionsFoods -> {
                    state.value.restaurant?.let { r ->
                        SectionFoodsComposable(
                            sectionTitle = section.getSubName(),
                            foodList = section.foodList,
                            onArrowClick = { viewModel.onSectionExpanded(section.subSectionId) },
                            expanded = expandedState.value.contains(section.subSectionId),
                            isShopClosed = r.isShopClosed,
                        )
                    }
                }

                is MainSectionFoods -> {
                    SectionHeading(
                        title = section.getMainName(),
                        showSeeAllText = false,
                        paddingValues = PaddingValues(15.dp, 15.dp)
                    )
                    section.subFoodSections.orEmpty().forEach { sub ->
                        state.value.restaurant?.let { r ->
                            ExpandableSectionFoods(
                                sectionTitle = sub.getSubName(),
                                foodList = sub.foodList,
                                onArrowClick = { viewModel.onSectionExpanded(sub.subSectionId) },
                                expanded = expandedState.value.contains(sub.subSectionId),
                                isShopClosed = r.isShopClosed
                            )
                        }
                    }
                }
            }
            GrayDivider()
        }
        GrayDivider()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SectionFoodsComposable(
    sectionTitle: String,
    foodList: List<Food>?,
    onArrowClick: () -> Unit,
    expanded: Boolean,
    isShopClosed: Boolean,
    modifier: Modifier = Modifier
) {
    val arrowRotationDegree by animateFloatAsState(
        animationSpec = if (expanded) tween(200) else tween(0),
        targetValue = if (expanded) 180f else 0f
    )

    Column {
        Box(
            modifier = modifier
                .noRippleClickable { onArrowClick() }
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth()
        ) {
            SectionHeading(
                title = sectionTitle,
                showSeeAllText = false,
                paddingValues = PaddingValues(0.dp, 8.dp)
            )
            Icon(
                painterResource(id = R.drawable.ic_expand),
                null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .rotate(arrowRotationDegree)
            )
        }
        if (expanded) {
            foodList.orEmpty().forEach { food ->
                FoodItem(
                    food = food,
                    isShopClosed = isShopClosed
                )
            }
        }
    }
}

@Composable
fun ExpandableSectionFoods(
    sectionTitle: String,
    foodList: List<Food>,
    onArrowClick: () -> Unit,
    expanded: Boolean,
    isShopClosed: Boolean,
    modifier: Modifier = Modifier
) {
    val arrowRotationDegree by animateFloatAsState(
        animationSpec = if (expanded) tween(200) else tween(0),
        targetValue = if (expanded) 180f else 0f
    )

    Column {
        Row(
            modifier = modifier
                .drawWithContent {
                    drawContent()
                    drawLine(
                        color = Color(0x14000000),
                        start = Offset(15.dp.toPx(), this.size.height),
                        end = Offset(
                            if (expanded) (this.size.width * 0.15f) else (this.size.width - 15.dp.toPx()),
                            this.size.height
                        ),
                        strokeWidth = 2f
                    )
                }
                .noRippleClickable { onArrowClick() }
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                sectionTitle,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 17.sp
                )
            )
            Icon(
                painterResource(id = R.drawable.ic_expand),
                null,
                modifier = Modifier.rotate(arrowRotationDegree)
            )
        }
        if (expanded) {
            foodList.forEach {
                FoodItem(
                    food = it,
                    isShopClosed = isShopClosed
                )
            }
        }
    }
}

@Composable
fun RestaurantOffersRows(
    rOffersList: List<Offer>,
    modifier: Modifier = Modifier
) {
    val horizontalScrollState = rememberLazyListState()
    LazyRow(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        state = horizontalScrollState,
        contentPadding = PaddingValues(start = 5.dp)
    ) {
        items(items = rOffersList) {
            OfferWideItem(
                it,
                Modifier.defaultMinSize(180.dp)
            )
        }
    }

    HorizontalSliderUi(
        horizontalScrollState = horizontalScrollState,
        modifier = Modifier.padding(vertical = 15.dp)
    )
}

@Composable
fun RestaurantBasicDetails(
    restaurant: Restaurant,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        Text(
            text = restaurant.name,
            style = Typography.h1,
            modifier = Modifier
                .defaultMinSize(minWidth = 250.dp, minHeight = 10.dp)
                .addShimmer(false)
        )
        Text(
            text = restaurant.dishTagline,
            style = Typography.h3,
            modifier = Modifier
                .defaultMinSize(minWidth = 100.dp, minHeight = 5.dp)
                .addShimmer(false)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = restaurant.getLocationTagline(),
                style = Typography.h3
            )
            Icon(
                Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .width(20.dp),
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun BestSafetyCard() {
    Card(
        backgroundColor = Color(0xFFE0F7FA),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(0.5.dp, Color(0x66009688)),
        elevation = 2.dp,
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = R.drawable.ic_safety),
                null,
                modifier = Modifier
                    .size(32.dp)
                    .weight(0.1f),
                contentScale = ContentScale.Inside
            )
            Text(
                "This restaurant follows Best Safety Standards",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                ),
                modifier = Modifier
                    .weight(0.8f)
                    .padding(horizontal = 1.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Image(
                painterResource(id = R.drawable.ic_next_arrow),
                null,
                modifier = Modifier
                    .size(13.dp)
                    .weight(0.1f),
                contentScale = ContentScale.Inside
            )
        }
    }
}

@Composable
fun ThreeSectionDetails(r: Restaurant) {
    Row(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .defaultMinSize(minWidth = 20.dp, minHeight = 8.dp)
                    .addShimmer(false)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_rating),
                    contentDescription = null,
                    modifier = Modifier
                        .width(18.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    r.rating.toString(),
                    style = Typography.h2
                )
                Icon(
                    Icons.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier
                        .width(22.dp)
                        .padding(horizontal = 0.dp)
                )
            }
            val movingList =
                listOf("Taste 74%", "Portion 70%", "500+ ratings", "Packing 7500")

            var i by remember { mutableStateOf(0) }
            LaunchedEffect(true) {
                while (true) {
                    delay(3000) // set here your delay between animations
                    i = ((i + 1) % movingList.size)
                }
            }
            AnimateTextUpDown(
                movingList[i],
                1,
                Modifier
                    .defaultMinSize(minWidth = 40.dp, minHeight = 5.dp)
                    .addShimmer(false)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (r.isShopClosed) "Closed" else "${r.distanceTimeMinutes} mins",
                style = Typography.h2,
                color = if (r.isShopClosed) Color.Red else Color.Black,
                modifier = Modifier
                    .defaultMinSize(minWidth = 20.dp, minHeight = 8.dp)
                    .addShimmer(false)
            )
            Text(
                "Delivery Time",
                modifier = Modifier
                    .defaultMinSize(minWidth = 40.dp, minHeight = 5.dp)
                    .addShimmer(false)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "₹${r.averagePricingForTwo}",
                style = Typography.h2,
                modifier = Modifier
                    .defaultMinSize(minWidth = 20.dp, minHeight = 8.dp)
                    .addShimmer(false)
            )
            Text(
                "Cost for 2",
                modifier = Modifier
                    .defaultMinSize(minWidth = 40.dp, minHeight = 5.dp)
                    .addShimmer(false)
            )
        }
    }
}

@Composable
fun PureVegComposable() {
    val isPureVeg = true
    Column(
        modifier = Modifier.padding(horizontal = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (isPureVeg) {
                    true -> {
                        Image(
                            painter = painterResource(id = R.drawable.ic_pureveg),
                            contentDescription = null
                        )
                    }
                    else -> {
                        var foodTypePreference by remember { mutableStateOf(false) }
                        Switch(
                            checked = foodTypePreference,
                            onCheckedChange = {
                                foodTypePreference = !foodTypePreference
                            }
                        )
                    }
                }
                Text(
                    text = if (isPureVeg) "PURE VEG" else "VEG ONLY",
                    style = Typography.h3,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_best_safety),
                contentDescription = null,
                modifier = Modifier
                    .width(85.dp)
            )
        }
        if (isPureVeg) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(BorderStroke(1.dp, Color(0x1A000000)))
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_pureveg),
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp)
                        .padding(8.dp)
                )
                Column(
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Showing only vegetarian options.",
                        style = Typography.h5,
                        fontWeight = FontWeight.SemiBold,

                        )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Tap on the VEG ONLY button to turn off the setting",
                    )
                }
            }
        }
    }
}

@Composable
fun RestaurantPageTopAppBar(
    viewModel: RestaurantViewModel,
    isScrolling: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(if (isScrolling) 1f else 0f)
                ) {
                    val viewState = viewModel.state.collectAsState()
                    val restaurant = viewState.value.restaurant

                    restaurant?.let {
                        Text(
                            text = it.name.uppercase(Locale.getDefault()),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (it.isShopClosed) "Closed for delivery" else "${it.distanceTimeMinutes} mins",
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (it.isShopClosed) Color.Red else Color(0xD9000000)
                        )
                    }

                }
                IconButton(
                    onClick = { },
                ) {
                    Icon(painterResource(id = R.drawable.ic_heart), null)
                }
                Spacer(modifier = Modifier.width(5.dp))
                IconButton(onClick = {}, modifier = modifier.noRippleClickable { }) {
                    Icon(painterResource(id = R.drawable.ic_search), null)
                }
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        modifier = Modifier
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color(0x14000000),
                    start = Offset(0f, this.size.height),
                    end = Offset(this.size.width, this.size.height),
                    strokeWidth = 3f,
                    alpha = if (isScrolling) 1f else 0f
                )
            },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(painterResource(id = R.drawable.ic_left_arrow), null)
            }
        },
        elevation = 0.dp
    )
}

@Composable
fun BottomQuickMenu(modifier: Modifier = Modifier) {
    val myExtendedFabSize = 52.dp
    val boxFabSize = 20.dp
    FloatingActionButton(
        modifier = modifier
            .sizeIn(
                minWidth = myExtendedFabSize,
                minHeight = myExtendedFabSize
            )
            .padding(bottom = 10.dp),
        onClick = { },
        backgroundColor = Color(0xFF303030),
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(3.dp, 3.dp)
    ) {
        Box(
            modifier = Modifier.padding(
                start = boxFabSize,
                end = boxFabSize
            ),
            contentAlignment = Alignment.Center
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_knife_fork),
                    null,
                    tint = Color.White,
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = "Browse Menu".uppercase(Locale.getDefault()),
                    style = TextStyle(
                        fontFamily = Prox,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodItemPreview() {
    FoodItem(
        food = PreviewData.prepareRestaurantFoods()[0],
        isShopClosed = true
    )
}

//@Preview("restaurant content", showBackground = true)
//@Composable
//fun RestaurantPreview() {
//    RestaurantContent(
//        RestaurantViewModel(RestaurantsRepository()), rememberLazyListState(), PaddingValues(10.dp), Modifier.fillMaxSize()
//    )
//}