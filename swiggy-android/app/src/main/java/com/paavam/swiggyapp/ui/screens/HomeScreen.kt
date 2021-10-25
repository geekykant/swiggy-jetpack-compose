package com.paavam.swiggyapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.AddressType
import com.paavam.swiggyapp.core.data.model.HelloBar
import com.paavam.swiggyapp.core.data.model.QuickTile
import com.paavam.swiggyapp.core.ui.UiState
import com.paavam.swiggyapp.lib.LazyHorizontalGrid
import com.paavam.swiggyapp.lib.items
import com.paavam.swiggyapp.ui.component.*
import com.paavam.swiggyapp.ui.component.anim.AnimateTextUpDown
import com.paavam.swiggyapp.ui.component.image.BannerImage
import com.paavam.swiggyapp.ui.component.listitem.CuisineItem
import com.paavam.swiggyapp.ui.component.listitem.QuickTileItem
import com.paavam.swiggyapp.ui.component.listitem.RestaurantItem
import com.paavam.swiggyapp.ui.component.text.AnnouncementHeading
import com.paavam.swiggyapp.ui.component.text.SectionHeading
import com.paavam.swiggyapp.ui.navigation.MainNavScreen
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.addShimmer
import com.paavam.swiggyapp.viewmodel.HomeViewModel
import com.paavam.swiggyapp.viewmodel.SwiggyViewModel
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@Composable
fun MainContent(
    outerPadding: PaddingValues,
    swiggyViewModel: SwiggyViewModel,
    mainNavController: NavController
) {
    val scrollState = rememberLazyListState()
    var isScrollStateChanged by remember { mutableStateOf(false) }

    val homeViewModel: HomeViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            isScrollStateChanged = scrollState.firstVisibleItemScrollOffset != 0
            HomeTopAppBar(
                isScrollStateChanged,
                swiggyViewModel,
                mainNavController
            )
        }
    ) {

//        val nearbyRestaurantsState =
//            homeViewModel.nearbyRestaurants.collectAsState(UiState.loading()).value
//
//        val popularCurationsState =
//            homeViewModel.popularCurations.collectAsState(UiState.loading()).value

//        when (nearbyRestaurantsState) {
//            is UiState.Loading -> LoadingHomeScreen()
//            is UiState.Failed -> ErrorHomeScreen()
//            is UiState.Success -> HomeScreen(
//                scrollState,
//                outerPadding,
//                mainNavController,
//                homeViewModel,
//                nearbyRestaurantsState.data,
//            )
//        }

        HomeScreen(
            scrollState,
            outerPadding,
            mainNavController,
            homeViewModel
        )
    }
}

@Composable
fun ErrorHomeScreen() {
    //
}

@Composable
fun LoadingHomeScreen() {
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)

    ) {
        GrayDivider(
            modifier = Modifier
                .height(130.dp)
                .padding(vertical = 10.dp)
                .addShimmer(true)
        )
        GrayDivider(
            modifier = Modifier
                .height(130.dp)
                .padding(vertical = 10.dp)
                .addShimmer(true)
        )
        GrayDivider(
            modifier = Modifier
                .height(80.dp)
                .padding(vertical = 10.dp)
                .addShimmer(true)
        )
        GrayDivider(
            modifier = Modifier
                .width(230.dp)
                .height(20.dp)
                .padding(vertical = 10.dp)
                .addShimmer(true)
        )

        Spacer(
            modifier = Modifier
                .height(20.dp)
                .addShimmer(true)
        )

        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
//            (1..10).toList()
        }

        Row(
            modifier = Modifier.padding(vertical = 10.dp)
        ) {

        }
    }
}

@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    scrollState: LazyListState, // Higher level is invoked, and reflected throughout
    outerPaddingValues: PaddingValues,
    mainNavController: NavController,
    homeViewModel: HomeViewModel,
//    nearbyRestaurants: List<Restaurant>
) {
    val homeViewState by homeViewModel.state.collectAsState()
    val widgetBottomPadding = 10.dp

    val nearbyRestaurantsState =
        homeViewModel.nearbyRestaurants.collectAsState(UiState.loading()).value

    val popularCurationsState =
        homeViewModel.popularCurations.collectAsState(UiState.loading()).value

//    val quickTilesState =
//        homeViewModel.quickTiles.collectAsState(UiState.loading()).value
//    val helloBarState =
//        homeViewModel.helloBarMessages.collectAsState(UiState.loading()).value

    val helloBarMessages = homeViewModel.helloBarMessages.collectAsState()
    val quickTiles = homeViewModel.quickTiles.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(homeViewState.refreshing),
        onRefresh = { homeViewModel.refresh(force = true) },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = outerPaddingValues.calculateBottomPadding()),
            state = scrollState
        ) {
            // Top announcements section
            item {
                TopHelloBar(helloBarMessages.value)
                AnnouncementHeading(
                    message = "As per state mandates, we will be operational till 8:00 PM",
                    modifier = Modifier.padding(bottom = widgetBottomPadding),
                )
            }

            //Categories or Quick Tiles
            item {
                QuickTilesList(
                    quickTiles.value,
                    modifier = Modifier
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
                    "Top Picks For You",
                    null,
                    R.drawable.ic_offers_filled,
                    showSeeAllIcon = false,
                    showSeeAllText = false
                )
                val topPicksRestaurants = PreviewData.prepareRestaurants()
                SingleRowRestaurants(
                    topPicksRestaurants,
                    paddingValues = PaddingValues(start = 15.dp),
                    mainNavController
                )
            }

            item {
                when (popularCurationsState) {
                    is UiState.Success -> {
                        val cuisineList = popularCurationsState.data
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
                            items(items = cuisineList) {
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
                    else -> {
                        /* Do something  */
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

            val nearbyRestaurants = PreviewData.prepareRestaurants()
            items(items = nearbyRestaurants) {
                RestaurantItem(
                    it,
                    Modifier.fillMaxWidth(),
                    onRestaurantClick = {
                        mainNavController.navigate(MainNavScreen.Restaurant.route + "/${it.restaurantId}")
                    }
                )
            }

            item {
                SectionHeading(
                    "Latest on the block!",
                    null,
                    R.drawable.ic_offers_filled,
                    showSeeAllIcon = false,
                    showSeeAllText = false
                )
                DoubleRowRectangleRestaurants(
                    spotlightRestaurants = nearbyRestaurants,
                    paddingValues = PaddingValues(horizontal = 15.dp),
                    modifier = Modifier.fillParentMaxWidth(0.45f),
                    mainNavController = mainNavController
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
                DoubleRowRestaurants(
                    spotlightRestaurants = nearbyRestaurants,
                    mainNavController = mainNavController
                )
            }

            item {
                FooterStaticImage()
            }
        }
    }
}

@Composable
fun TopHelloBar(contentList: List<HelloBar>, modifier: Modifier = Modifier) {
    val roundShape = RoundedCornerShape(50)
    var i by remember { mutableStateOf(0) }
    val helloCount = contentList.size

    if(helloCount == 0) return

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
        AnimateTextUpDown(contentList[i].message, 2)
    }
}

@Composable
fun HomeTopAppBar(
    isScrollStateChanged: Boolean,
    swiggyViewModel: SwiggyViewModel,
    mainNavController: NavController,
    modifier: Modifier = Modifier,
) {
    val addressState = swiggyViewModel.defaultAddress.collectAsState()
    val address = addressState.value

    TopAppBar(
        title = {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .clickable { mainNavController.navigate(MainNavScreen.ShowAddresses.route) }
                        .padding(0.dp, 8.dp)
                        .weight(0.6f)
                ) {
                    Row {
                        Image(
                            painter = painterResource(R.drawable.ic_location),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 5.dp)
                        )
                        Text(
                            text = if (address?.type == AddressType.HOME) "Home" else "Other",
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Text(
                        text = address?.fullAddress ?: " ",
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xD9000000)
                    )
                }
                Column(
                    modifier = Modifier
                        .clickable {
//                            mainNavController.navigate(MainNavScreen.ShowAddresses.route)
                        }
                        .padding(5.dp, 15.dp)
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
