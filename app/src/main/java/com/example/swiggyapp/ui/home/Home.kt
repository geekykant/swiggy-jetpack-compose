package com.example.swiggyapp.ui.home

import LazyHorizontalGrid
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.swiggyapp.R
import com.example.swiggyapp.data.*
import com.example.swiggyapp.ui.theme.Typography
import com.google.accompanist.coil.rememberCoilPainter
import items
import kotlinx.coroutines.delay
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(
    outerPaddingValues: PaddingValues
) {
    val scrollState = rememberLazyListState()
    var topBarElevation by remember { mutableStateOf(0.dp) }

    Scaffold(
        topBar = {
            topBarElevation = if (scrollState.firstVisibleItemScrollOffset != 0) {
                12.dp
            } else {
                0.dp
            }
            StickyTopAppBar(topBarElevation)
        }
    ) { innerPaddingValues ->
        HomeScreen(scrollState, outerPaddingValues, innerPaddingValues)
    }
}

@Composable
fun HomeScreen(
    scrollState: LazyListState,
    outerPaddingValues: PaddingValues,
    innerPaddingValues: PaddingValues
) {
    val widgetBottomPadding = 10.dp
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = outerPaddingValues.calculateBottomPadding()),
        state = scrollState
    ) {
        // Top announcements section
        item {
            TopHelloBar(prepareHelloBarContent())
            AnnouncementHeading(
                message = "As per state mandates, we will be operational till 8:00 PM",
                modifier = Modifier.padding(bottom = widgetBottomPadding),
            )
        }

        //Categories or Quick Tiles
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = widgetBottomPadding)
            ) {
                QuickTilesList(prepareTilesContent())
            }
        }

        item {
            SingleImageComposable(
                imageUrl = "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,q_auto,w_550,h_310,c_fill//edilicious_eszbcy.png",
                modifier = Modifier.padding(bottom = widgetBottomPadding)
            )
        }

        item {
            SectionHeading(
                "All Restaurants Nearby",
                "Discover unique tastes near you",
                R.drawable.ic_shopicon,
                showSeeAllIcon = true,
                seeAllOnClick = {

                }
            )
        }

        items(items = prepareRestaurants()) {
            RestaurantItem(it, Modifier.fillMaxWidth())
        }

        item {
            SectionHeading(
                "Top Offers",
                "Big Savings On Your Loved Eateries",
                R.drawable.ic_offers_filled,
                showSeeAllIcon = true,
                seeAllOnClick = {

                }
            )
            DoubleSectionRestaurants(spotlightRestaurants = prepareRestaurants())
        }

        item {
            FooterStaticImage()
        }
    }
}

@Composable
fun FooterStaticImage(
    modifier: Modifier = Modifier,
    imageUrl: String = "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto/footer_graphic_vxojqs",
) {
    Image(
        rememberCoilPainter(imageUrl, previewPlaceholder = R.drawable.ic_restaurant1),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun SingleImageComposable(imageUrl: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clickable { }
            .padding(horizontal = 15.dp, vertical = 0.dp)
            .fillMaxWidth()
    ) {
        Image(
            rememberCoilPainter(
                imageUrl,
                fadeInDurationMs = 300,
                previewPlaceholder = R.drawable.ic_restaurant1
            ),
            contentDescription = null,
            modifier = Modifier
                .height(210.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DoubleSectionRestaurants(
    spotlightRestaurants: List<Restaurant>,
    modifier: Modifier = Modifier
) {
    /* Spotlight Restaurants are in 6 columns x 2 rows
       We need to check and see they are in the limits for the sublist windowing.
    */
    fun nearestEven(size: Int): Int = if (size % 2 == 0) size else size.dec()
    val spotlightRestaurantsSublist =
        spotlightRestaurants.subList(
            0,
            12.coerceAtMost(nearestEven(spotlightRestaurants.size))
        )

    LazyHorizontalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(items = spotlightRestaurantsSublist) {
            RestaurantItem(r = it, modifier = Modifier.fillParentMaxWidth(0.8f))
        }
    }
}

@Composable
fun TopHelloBar(contentList: List<HelloBar>, modifier: Modifier = Modifier) {
    val roundShape = RoundedCornerShape(50)
    var i by remember { mutableStateOf(0) }
    val helloCount = contentList.size

    LaunchedEffect(true) {
        while (true) {
            delay(3000) // set here your delay between animations
            i = ((i + 1) % helloCount)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(R.drawable.ic_rain),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Top)
                .padding(2.dp, 8.dp)
                .background(Color(0xF5ECECEC), roundShape)
                .clip(roundShape),
            contentScale = ContentScale.Inside,
        )
        AnimateUpDown(contentList[i].message)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimateUpDown(message: String, modifier: Modifier = Modifier) {
    AnimatedContent(targetState = message,
        transitionSpec = {
            if (targetState > initialState) {
                slideInVertically({ height -> height }) + fadeIn() with
                        slideOutVertically({ height -> -height }) + fadeOut()
            } else {
                slideInVertically({ height -> -height }) + fadeIn() with
                        slideOutVertically({ height -> height }) + fadeOut()
            }.using(SizeTransform(clip = false))
        }
    ) {
        Text(
            text = message,
            modifier = modifier
                .padding(horizontal = 5.dp, vertical = 3.dp)
                .height(30.dp),
            maxLines = 2
        )
    }
}

@Composable
fun SectionHeading(
    title: String,
    tagline: String? = null,
    iconResId: Int? = null,
    showSeeAllIcon: Boolean = false,
    showSeeAllText: Boolean = true,
    seeAllText: String = "See All",
    seeAllTextColor: Color = Color.Black,
    seeAllOnClick: () -> Unit = { }
) {
    Column(
        modifier = Modifier
            .padding(15.dp, 8.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                if (iconResId != null) {
                    Image(
                        painter = painterResource(iconResId),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
                Text(
                    text = title,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier
                        .padding(5.dp, 0.dp)
                        .alignByBaseline()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            ) {
                if (showSeeAllText) {
                    Text(
                        text = seeAllText.uppercase(Locale.getDefault()),
                        style = if(showSeeAllIcon) Typography.h3 else Typography.body1,
                        fontWeight = if(showSeeAllIcon) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .clickable { seeAllOnClick() }
                            .padding(5.dp, 0.dp)
                            .align(if(showSeeAllIcon) Alignment.CenterVertically else Alignment.Bottom),
                        color = seeAllTextColor,
                    )
                }

                if (showSeeAllIcon) {
                    Image(
                        painter = painterResource(R.drawable.ic_see_all),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }

        if (tagline != null) {
            Text(
                text = tagline,
                color = Color(0xD9000000),
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun RestaurantItem(r: Restaurant, modifier: Modifier = Modifier) {
    val height = 120.dp
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .heightIn(0.dp, height)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 0.dp)
                .width(90.dp)
                .height(height),
        ) {
            Image(
                rememberCoilPainter(
                    r.imageUrl,
                    fadeInDurationMs = 300,
                    previewPlaceholder = R.drawable.ic_restaurant1
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(110.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop,
            )
            val snackModifier = Modifier.align(Alignment.BottomCenter)
            when (r.offerSnack?.offerType) {
                OfferSnackType.BASIC ->
                    BasicOfferSnackComposable(
                        message = r.offerSnack.message,
                        invert = false,
                        modifier = snackModifier
                    )
                OfferSnackType.INVERT_BASIC ->
                    BasicOfferSnackComposable(
                        message = r.offerSnack.message,
                        invert = true,
                        modifier = snackModifier
                    )
                OfferSnackType.FLAT_DEAL ->
                    FlatDealOfferSnackComposable(
                        message = r.offerSnack.message,
                        modifier = snackModifier
                    )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 15.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = r.name,
                style = Typography.h2,
                maxLines = 1
            )
            Text(
                text = r.dishTagline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(text = r.location, maxLines = 1)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_rating),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(
                    text = r.rating.toString(),
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.dp)
                )
                Text(
                    text = ".",
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.dp)
                )
                Text(
                    text = "₹${r.averagePricingForTwo} for two",
                    modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.dp)
                )
            }

            if (!r.allOffers.isNullOrEmpty()) {
                Divider()
            }

            LazyColumn {
                if (!r.allOffers.isNullOrEmpty()) {
                    items(items = r.allOffers) { offer ->
                        Row {
                            Icon(
                                painterResource(id = R.drawable.ic_offers_filled),
                                contentDescription = null,
                                modifier = Modifier.padding(end = 5.dp),
                                tint = Color(0xFFFA5520)
                            )
                            Text(
                                text = offer.offerMessage,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun FlatDealOfferSnackComposable(
    message: String,
    modifier: Modifier,
    roundShape: RoundedCornerShape = RoundedCornerShape(5.dp)
) {
    Text(
        text = message,
        maxLines = 1,
        style = Typography.h1,
        modifier = modifier
            .shadow(8.dp, roundShape)
            .background(Color.White, roundShape)
            .padding(horizontal = 10.dp, vertical = 3.dp),
        color = Color(0xFFFF5722),
        fontSize = 18.sp
    )
}

@Composable
fun BasicOfferSnackComposable(message: String, invert: Boolean, modifier: Modifier) {
    val roundShape = RoundedCornerShape(5.dp)
    var textColor = Color.White
    var backgroundColor = MaterialTheme.colors.primaryVariant
    var borderColor = Color(0x0)
    var fontWeight = FontWeight.Bold

    if (invert) {
        backgroundColor = Color.White
        textColor = MaterialTheme.colors.primaryVariant
        fontWeight = FontWeight.ExtraBold
        borderColor = Color(0x1A000000)
    }

    Text(
        text = message,
        maxLines = 1,
        style = Typography.h1,
        modifier = modifier
            .shadow(8.dp, roundShape)
            .background(backgroundColor, roundShape)
            .border(1.dp, borderColor, shape = roundShape)
            .padding(horizontal = 10.dp, vertical = 3.dp),
        color = textColor,
        fontSize = 15.sp,
        fontWeight = fontWeight
    )
}

@Composable
fun StickyTopAppBar(scrolledElevation: Dp, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .clickable { }
                        .padding(0.dp, 0.dp)
                        .weight(0.6f)
                ) {
                    Row {
                        Image(
                            painter = painterResource(R.drawable.ic_location),
                            contentDescription = null,
                            modifier = Modifier.padding(3.dp)
                        )
                        Text(
                            text = "Home",
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Text(
                        "Tra-32 (last House On Right), Elamakkara Mamangalam, Ernakulam",
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xD9000000)
                    )
                }
                Column(
                    modifier = Modifier
                        .clickable { }
                        .padding(4.dp)
                        .weight(0.2f)
                        .align(Alignment.CenterVertically),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_offers),
                            contentDescription = null,
                            modifier = Modifier.padding(3.dp),
                        )
                        Text(text = "Offers", maxLines = 1, style = Typography.h2)
                    }
                }
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = scrolledElevation,
        modifier = Modifier
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color(0x14000000),
                    start = Offset(0f, this.size.height),
                    end = Offset(this.size.width, this.size.height),
                    strokeWidth = 3f,
                    alpha = if (scrolledElevation == 0.dp) 1f else 0f
                )
            })
}

@Composable
fun QuickTilesList(content: List<QuickTile>, modifier: Modifier = Modifier) {
    val horizontalScrollState = rememberLazyListState()
    LazyRow(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        contentPadding = PaddingValues(start = 10.dp),
        state = horizontalScrollState
    ) {
        items(items = content.toList()) {
            QuickTileComposable(it.title, it.tagLine, it.imageUrl)
        }
    }

    /*
    Horizontal Slider implemented the hard way to track scroll on lazyrow.
     */
    val roundShape = RoundedCornerShape(5.dp)
    Box(
        modifier = Modifier
            .padding(top = 5.dp, bottom = 10.dp)
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
fun Modifier.simpleHorizontalScrollbar(
    state: LazyListState,
    height: Dp = 3.5.dp
): Modifier = composed {
    drawWithContent {
        drawContent()
        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (firstVisibleElementIndex != null) {
            val elementWidth = this.size.width / state.layoutInfo.totalItemsCount
            val scrollbarOffsetX = firstVisibleElementIndex * elementWidth
            val scrollbarWidth = state.layoutInfo.visibleItemsInfo.size * elementWidth

            drawRoundRect(
                color = Color.Red,
                topLeft = Offset(scrollbarOffsetX, 0f),
                size = Size(scrollbarWidth, height.toPx()),
                cornerRadius = CornerRadius(15.dp.toPx(), 15.dp.toPx())
            )
        }
    }
}

@Composable
fun QuickTileComposable(heading: String, tagline: String, imageUrl: String) {
    val roundShape = RoundedCornerShape(15.dp)
    Column(
        modifier = Modifier
            .width(118.dp)
            .padding(5.dp)
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .shadow(2.dp, roundShape)
                .border(0.7.dp, Color(0x1A000000), shape = roundShape),
        ) {
            Text(
                text = heading,
                modifier = Modifier
                    .zIndex(2f)
                    .padding(horizontal = 2.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Typography.h2,
                fontWeight = FontWeight.ExtraBold
            )
            Image(
                painter = rememberCoilPainter(
                    imageUrl,
                    fadeInDurationMs = 300,
                    previewPlaceholder = R.drawable.ic_deliveryman
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(roundShape),
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = tagline,
            modifier = Modifier
                .padding(vertical = 2.dp),
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}

@Composable
fun AnnouncementHeading(message: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp, 10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .width(9.dp)
                .height(60.dp)
                .align(Alignment.CenterVertically)
                .clip(RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp))
                .background(MaterialTheme.colors.primaryVariant)
        ) { }
        Text(
            text = message,
            style = Typography.h1,
            color = Color.Black,
            modifier = modifier
                .padding(start = 15.dp, end = 3.dp)
                .align(Alignment.CenterVertically)
        )
    }
}