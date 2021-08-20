package com.paavam.swiggyapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.lib.LazyHorizontalGrid
import com.paavam.swiggyapp.lib.items
import com.paavam.swiggyapp.model.HelloBar
import com.paavam.swiggyapp.model.QuickTile
import com.paavam.swiggyapp.ui.RestaurantActivity
import com.paavam.swiggyapp.ui.component.CuisineItem
import com.paavam.swiggyapp.ui.component.DoubleRowRestaurants
import com.paavam.swiggyapp.ui.component.FooterStaticImage
import com.paavam.swiggyapp.ui.component.HorizontalSliderUi
import com.paavam.swiggyapp.ui.component.anim.AnimateUpDown
import com.paavam.swiggyapp.ui.component.image.BannerImage
import com.paavam.swiggyapp.ui.component.listitem.QuickTileItem
import com.paavam.swiggyapp.ui.component.listitem.RestaurantItem
import com.paavam.swiggyapp.ui.component.text.AnnouncementHeading
import com.paavam.swiggyapp.ui.component.text.SectionHeading
import com.paavam.swiggyapp.ui.theme.SwiggyTheme
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun MainContent(
    outerPaddingValues: PaddingValues
) {
    val scrollState = rememberLazyListState()
    var isScrollStateChanged by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            isScrollStateChanged = scrollState.firstVisibleItemScrollOffset != 0
            StickyTopAppBar(isScrollStateChanged)
        }
    ) { innerPaddingValues ->
        HomeScreen(scrollState, outerPaddingValues, innerPaddingValues)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    scrollState: LazyListState, // Higher level is invoked, and reflected throughout
    outerPaddingValues: PaddingValues,
    innerPaddingValues: PaddingValues,
) {
    val viewModel: HomeViewModel = hiltViewModel()
//    val viewModel: HomeViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()

    val widgetBottomPadding = 10.dp
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = outerPaddingValues.calculateBottomPadding()),
        state = scrollState
    ) {
        // Top announcements section
        item {
            viewState.helloBarMessages?.let { TopHelloBar(it) }
            AnnouncementHeading(
                message = "As per state mandates, we will be operational till 8:00 PM",
                modifier = Modifier.padding(bottom = widgetBottomPadding),
            )
        }

        //Categories or Quick Tiles
        item {
            QuickTilesList(
                viewState.quickTiles, Modifier
                    .fillMaxWidth()
                    .padding(bottom = widgetBottomPadding)
            )
        }

        item {
            BannerImage(
                imageUrl = "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,q_auto,w_550,h_310,c_fill//edilicious_eszbcy.png",
                modifier = Modifier.padding(bottom = widgetBottomPadding)
            )
        }

        item {
            SectionHeading(
                title = "Popular Curations",
                showSeeAllText = false
            )
            LazyHorizontalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(bottom = widgetBottomPadding),
                contentPadding = PaddingValues(start = 10.dp)
            ) {
                items(items = viewState.popularCurations) {
                    CuisineItem(
                        it,
                        modifier = Modifier
                            .clickable { }
                            .padding(horizontal = 8.dp, vertical = 10.dp)
                            .fillParentMaxWidth(0.25f),
                        fontSize = 14.sp,
                        bottomTextPadding = PaddingValues(top = 10.dp)
                    )
                }
            }
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


        items(items = viewState.nearbyRestaurants) {
            val context = LocalContext.current
            RestaurantItem(
                it,
                Modifier.fillMaxWidth(),
                onRestaurantClick = {
                    context.startActivity(Intent(context, RestaurantActivity::class.java))
                }
            )
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
            DoubleRowRestaurants(spotlightRestaurants = viewState.nearbyRestaurants)
        }

        item {
            FooterStaticImage()
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
        AnimateUpDown(contentList[i].message, 2)
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
fun BasicOfferSnackComposable(
    message: String,
    invert: Boolean,
    onClick: () -> Unit = {},
    modifier: Modifier
) {
    val roundShape = RoundedCornerShape(5.dp)
    var textColor = Color.White
    var backgroundColor = MaterialTheme.colors.primary
    var borderColor = Color(0x0)
    var fontWeight = FontWeight.Bold

    if (invert) {
        backgroundColor = Color.White
        textColor = MaterialTheme.colors.primary
        fontWeight = FontWeight.ExtraBold
        borderColor = Color(0x1A000000)
    }

    Text(
        text = message,
        maxLines = 1,
        style = Typography.h1,
        modifier = modifier
            .clickable { onClick() }
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
fun StickyTopAppBar(isScrollStateChanged: Boolean, modifier: Modifier = Modifier) {
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
        elevation = if (isScrollStateChanged) 12.dp else 0.dp,
        modifier = Modifier
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color(0x14000000),
                    start = Offset(0f, this.size.height),
                    end = Offset(this.size.width, this.size.height),
                    strokeWidth = 3f,
                    alpha = if (isScrollStateChanged) 0f else 1f
                )
            }
    )
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
        items(items = content) {
            QuickTileItem(it.title, it.tagLine, it.imageUrl)
        }
    }

    HorizontalSliderUi(horizontalScrollState, Modifier.padding(top = 10.dp, bottom = 15.dp))
}

@Preview("HomeScreen", showBackground = true)
@Composable
fun HomePreview() {
    SwiggyTheme {
//        MainContent(outerPaddingValues = PaddingValues(10.dp))
        HomeScreen(
            rememberLazyListState(), PaddingValues(), PaddingValues()
        )
    }
}

