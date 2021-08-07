package com.example.swiggyapp.ui.restaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swiggyapp.R
import com.example.swiggyapp.data.HelloBar
import com.example.swiggyapp.data.Offer
import com.example.swiggyapp.ui.home.*
import com.example.swiggyapp.ui.theme.Prox
import com.example.swiggyapp.ui.theme.SwiggyTheme
import com.example.swiggyapp.ui.theme.Typography
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.delay
import java.util.*

class RestaurantActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwiggyTheme {
                val scrollState = rememberLazyListState()
                var isScrollStateChanged by remember { mutableStateOf(false) }

                Scaffold(
                    topBar = {
                        isScrollStateChanged = scrollState.firstVisibleItemScrollOffset != 0
                        RestaurantPageTopAppBar(isScrollStateChanged)
                    },
                    floatingActionButton = { BottomQuickMenu() },
                    floatingActionButtonPosition = FabPosition.Center
                ) { outerPadding ->
                    RestaurantContent(
                        scrollState,
                        outerPadding,
                        Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun RestaurantContent(
    scrollState: LazyListState, // Higher level is invoked, and reflected throughout
    outerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: RestaurantViewModel = RestaurantViewModel()
) {
    val restaurantFoods = viewModel.restaurantFoods.collectAsState()
    val expandedSectionIds = viewModel.expandedFoodSectionIdsList.collectAsState()

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
            RestaurantBasicDetails()
            TopHelloBar(listOf(HelloBar("It's raining. To compensate your delivery partner, additional ₹12 delivery fee will apply")))
            DashedDivider(modifier = Modifier.padding(vertical = 5.dp))
        }

        item {
            ThreeSectionDetails()
            BestSafetyCard()
            DashedDivider(modifier = Modifier.padding(vertical = 10.dp))
            RestaurantOffersRows()

            GrayDivider()
        }

        item {
            PureVegComposable()
        }

        items(items = restaurantFoods.value.mainFoodSections.orEmpty()) {
            SectionHeading(title = it.getMainName(), showSeeAllText = false)

            it.mainFoodSections.orEmpty().forEach { sub ->
                ExpandableSectionFoods(
                    sectionTitle = sub.getSubName(),
                    foodList = sub.foodList,
                    onArrowClick = { viewModel.onSectionExpanded(sub.subSectionId) },
                    expanded = expandedSectionIds.value.contains(sub.subSectionId)
                )
            }
            GrayDivider()
        }
        item { GrayDivider() }

        item {
            FooterLicenseInfo(
                modifier = Modifier
                    .background(Color(0xD000000))
            )
        }
    }
}

@Composable
fun ExpandableSectionFoods(
    sectionTitle: String,
    foodList: List<FoodModel>,
    onArrowClick: () -> Unit,
    expanded: Boolean,
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
                FoodItemComposable(foodModel = it)
            }
        }
    }
}

@Composable
fun RestaurantOffersRows(
    modifier: Modifier = Modifier
) {
    val rOffersList = listOf(
        Offer(R.drawable.ic_offers_filled, 40, 80, "40METOO", 129),
        Offer(R.drawable.ic_offers_filled, 20, 180, "20OFFERPLOX", 600)
    )

    val horizontalScrollState = rememberLazyListState()
    LazyRow(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        state = horizontalScrollState,
        contentPadding = PaddingValues(start = 5.dp)
    ) {
        items(items = rOffersList) {
            OfferItemComposable(
                it,
                Modifier.defaultMinSize(180.dp)
            )
        }
    }

    /*
    Horizontal Slider implemented the hard way to track scroll on lazyrow.
     */
    val roundShape = RoundedCornerShape(5.dp)
    Box(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 15.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "",
            modifier = Modifier
                .height(3.5.dp)
                .simpleHorizontalScrollbar(horizontalScrollState)
                .background(Color(0x1A000000), roundShape)
                .fillMaxWidth(0.15f),
        )
    }
}

@Composable
fun OfferItemComposable(
    offer: Offer,
    modifier: Modifier = Modifier
) {
    val roundShape = RoundedCornerShape(5.dp)
    Column(
        modifier = modifier
            .padding(5.dp)
            .border(0.7.dp, Color(0x1A000000), shape = roundShape)
            .padding(8.dp),
    ) {
        Row(
            modifier = modifier.padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = offer.icon),
                contentDescription = null,
                modifier = Modifier.size(16.dp, 16.dp)
            )
            Text(
                text = offer.offerMessage(),
                maxLines = 1,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    letterSpacing = (-0.5).sp,
                ),
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
        Text(
            text = offer.shortCodeInfo(),
            maxLines = 1,
            style = TextStyle(
                fontSize = 12.sp,
                letterSpacing = (-0.5).sp,
            ),
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
fun RestaurantBasicDetails(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        Text(
            text = "Pizza Hut",
            style = Typography.h1
        )
        Text(
            text = "Pizzas, Fast Food",
            style = Typography.h3
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lulu Mall | 3.6 kms",
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
fun ThreeSectionDetails() {
    val isClosed = false
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
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_rating),
                    contentDescription = null,
                    modifier = Modifier
                        .width(18.dp)
                        .padding(end = 4.dp)
                )
                Text(
                    "4.0",
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
            AnimateUpDown(movingList[i], 1)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isClosed) "Closed" else "39 mins",
                style = Typography.h2,
                color = if (isClosed) Color.Red else Color.Black
            )
            Text("Delivery Time")
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("₹350", style = Typography.h2)
            Text("Cost for 2")
        }
    }
}

@Composable
fun PureVegComposable() {
    val isPureVeg = true
    Row(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 10.dp)
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
}

@Composable
fun FoodItemComposable(
    foodModel: FoodModel,
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
                    tint = when (foodModel.foodType) {
                        FoodType.VEG -> Color(0xFF008000)
                        FoodType.NON_VEG -> Color(0xFF008000)
                    },
                    modifier = Modifier.padding(end = 5.dp)
                )

                foodModel.starText?.let {
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
                text = foodModel.name,
                style = Typography.h2,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "₹${foodModel.price}"
            )
            Spacer(modifier = Modifier.height(3.dp))
            foodModel.foodContents?.let {
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
        ) {
            Image(
                painter = rememberCoilPainter(
                    foodModel.imageUrl,
                    fadeInDurationMs = 100,
                    previewPlaceholder = R.drawable.ic_restaurant1,
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(110.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            AddToCartComposable(
                message = "ADD",
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .align(Alignment.BottomCenter)
            )
        }
    }
    Divider(
        modifier = modifier.padding(horizontal = 15.dp),
        thickness = 0.5.dp
    )
}

@Composable
fun AddToCartComposable(
    message: String,
    modifier: Modifier = Modifier
) {
    val roundShape = RoundedCornerShape(5.dp)
    val greenColor = Color(0xFF81C784)
    val backgroundColor = Color.White
    val fontWeight = FontWeight.ExtraBold
    val borderColor = Color(0x1A000000)

    var itemCounter by remember { mutableStateOf(0) }

    if (itemCounter > 0) {
        Row(
            modifier = modifier
                .clip(roundShape)
                .shadow(8.dp, roundShape)
                .background(backgroundColor, roundShape)
                .border(1.dp, borderColor, shape = roundShape),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_minus),
                contentDescription = null,
                modifier = Modifier
                    .clickable { itemCounter -= 1 }
                    .padding(horizontal = 5.dp, vertical = 5.dp),
                tint = Color.LightGray
            )
            Text(
                text = itemCounter.toString(),
                style = Typography.h1,
                color = greenColor,
                fontSize = 15.sp,
                fontWeight = fontWeight,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null,
                modifier = Modifier
                    .clickable { itemCounter += 1 }
                    .padding(horizontal = 5.dp, vertical = 3.dp),
                tint = greenColor
            )
        }
    } else {
        Text(
            text = message,
            maxLines = 1,
            style = Typography.h1,
            modifier = modifier
                .clip(roundShape)
                .shadow(8.dp, roundShape)
                .clickable { itemCounter += 1 }
                .background(backgroundColor, roundShape)
                .border(1.dp, borderColor, shape = roundShape)
                .padding(horizontal = 10.dp, vertical = 5.dp),
            color = greenColor,
            fontSize = 15.sp,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun DashedDivider(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, 0f),
                    end = Offset(this.size.width, 0f),
                    strokeWidth = 2f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                )
            }
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

@Composable
fun GrayDivider(
    heightDp: Dp = 8.dp,
) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightDp),
        color = Color(0xD000000)
    )
}

@Composable
fun RestaurantPageTopAppBar(
    isScrolling: Boolean,
    isShopClosed: Boolean = false,
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
                    Text(
                        text = "Alakapuri".uppercase(Locale.getDefault()),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = if (isShopClosed) "Closed for delivery" else "52 mins",
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = if (isShopClosed) Color.Red else Color(0xD9000000)
                    )
                }
                IconButton(
                    onClick = {},
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
            IconButton(onClick = {}) {
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
    FoodItemComposable(foodModel = prepareRestaurantFoods()[0])
}

@Preview("restaurant content", showBackground = true)
@Composable
fun RestaurantPreview() {
    RestaurantContent(
        rememberLazyListState(), PaddingValues(10.dp), Modifier.fillMaxSize()
    )
}