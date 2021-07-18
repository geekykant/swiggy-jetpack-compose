package com.example.swiggyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.swiggyapp.R
import com.example.swiggyapp.data.*
import com.example.swiggyapp.ui.theme.SwiggyTheme
import com.example.swiggyapp.ui.theme.Typography
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyScreenContent()
        }
    }
}

@Composable
fun MyScreenContent() {
    SwiggyTheme {
        // Remember a SystemUiController
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = MaterialTheme.colors.isLight

        SideEffect {
            // Update all of the system bar colors to be transparent, and use
            // dark icons if we're in light theme
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
            // setStatusBarsColor() and setNavigationBarsColor() also exist
        }

        val scrollState = rememberLazyListState()
        var topBarElevation by remember { mutableStateOf(0.dp) }

        Scaffold(
            topBar = {
                val modifier: Modifier
                when (scrollState.firstVisibleItemIndex) {
                    0 -> {
                        topBarElevation = 0.dp
                        modifier = Modifier
                            .fillMaxWidth()
                    }
                    else -> {
                        topBarElevation = 12.dp
                        modifier = Modifier
                            .fillMaxWidth()
                    }
                }
                StickyTopAppBar(topBarElevation, modifier)
            },
            content = {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = scrollState
                ) {
                    // My Books section
                    item {
                        TopHelloBar(prepareHelloBarContent())
                    }

                    item{
                        AnnouncementHeading(message = "As per state mandates, we will be operational till 8:00 PM")
                    }

                    item {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            BoxItemList(prepareTilesContent())
                        }
                    }
                    item {
                        SectionHeading(
                            "All Restaurants Nearby",
                            "Discover unique tastes near you", R.drawable.ic_shopicon
                        )
                    }
                    items(items = prepareRestaurants()) {
                        RestaurantItem(it)
                    }

                    // Turning the list in a list of lists of two elements each
//                    items(wishlisted.windowed(2, 1, true)) { sublist ->
//                        Row(Modifier.fillMaxWidth()) {
//                            sublist.forEach { item ->
//                                Text(
//                                    item, modifier = Modifier
//                                        .height(40.dp)
//                                        .padding(4.dp)
//                                        .background(Color.Yellow)
//                                        .fillParentMaxWidth(.5f)
//                                )
//                            }
//                        }
//                    }

                }
            }
        )

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
            .padding(horizontal = 6.dp, vertical = 6.dp),
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
private fun AnimateUpDown(message: String) {
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
            modifier = Modifier
                .padding(3.dp)
                .height(28.dp),
            maxLines = 2
        )
    }
}

@Composable
fun SectionHeading(title: String, tagline: String, iconResId: Int) {
    Column(
        modifier = Modifier
            .padding(15.dp, 8.dp)
            .fillMaxWidth(),
    ) {
        Row {
            Image(
                painter = painterResource(iconResId),
                contentDescription = null,
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = title,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                    .padding(5.dp, 0.dp)
                    .alignByBaseline()
            )
        }
        Text(
            text = tagline,
            color = Color(0xD9000000),
            style = MaterialTheme.typography.h3
        )
    }
}

@Composable
fun RestaurantItem(r: Restaurant) {
    val height = 110.dp
    Row(
        modifier = Modifier
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
                    .height(100.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop,
            )
            when (r.offerSnack?.offerType) {
                OfferSnackType.BASIC -> {
                    val roundShape = RoundedCornerShape(5.dp)
                    Text(
                        text = r.offerSnack.message,
                        maxLines = 1,
                        style = Typography.h1,
                        modifier = Modifier
                            .shadow(8.dp, roundShape)
                            .background(Color(0xFFFF5722), roundShape)
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                            .align(Alignment.BottomCenter),
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
                OfferSnackType.FLAT_DEAL -> {
                    val roundShape = RoundedCornerShape(5.dp)
                    Text(
                        text = r.offerSnack.message,
                        maxLines = 1,
                        style = Typography.h1,
                        modifier = Modifier
                            .shadow(8.dp, roundShape)
                            .background(Color.White, roundShape)
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                            .align(Alignment.BottomCenter),
                        color = Color(0xFFFF5722),
                        fontSize = 18.sp
                    )
                }
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
                    modifier = Modifier.padding(0.dp, 0.dp, 2.dp, 0.dp)
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
                        Row() {
                            Icon(
                                painterResource(id = R.drawable.ic_offers_filled),
                                contentDescription = null,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp)
                            )
                            Text(text = offer.offerMessage)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun StickyTopAppBar(scrolledElevation: Dp = 0.dp, modifier: Modifier) {
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
    )
}

@Composable
fun BoxItemList(content: List<QuickTile>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
            .height(150.dp)
            .fillMaxWidth()
    ) {
        items(items = content.toList()) {
            BoxItem(it.title, it.tagLine, it.imageUrl)
        }
    }
}

@Composable
fun BoxItem(heading: String, tagline: String, imageUrl: String) {
    val roundShape = RoundedCornerShape(15.dp)
    Column(
        modifier = Modifier
            .width(120.dp)
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
                .padding(horizontal = 0.dp, vertical = 2.dp),
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyScreenContent()
}

@Composable
fun AnnouncementHeading(message: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp, 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier
                .width(12.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(0.dp,25.dp, 25.dp, 0.dp))
                .background(Color.Red)
        ) { }
        Text(
            text = message,
            style = Typography.h2,
            color = Color.Black,
            modifier = modifier
                .padding(start = 15.dp, end = 3.dp)
                .align(Alignment.CenterVertically)
        )
    }
}