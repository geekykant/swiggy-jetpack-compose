package com.paavam.swiggyapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.*
import com.paavam.swiggyapp.core.ui.UiState
import com.paavam.swiggyapp.ui.component.lib.LazyHorizontalGrid
import com.paavam.swiggyapp.ui.component.lib.items
import com.paavam.swiggyapp.ui.component.*
import com.paavam.swiggyapp.ui.component.anim.AnimateTextUpDown
import com.paavam.swiggyapp.ui.component.image.ImageWithPlaceholder
import com.paavam.swiggyapp.ui.component.listitem.CuisineItem
import com.paavam.swiggyapp.ui.component.listitem.QuickTileItem
import com.paavam.swiggyapp.ui.component.listitem.RestaurantItem
import com.paavam.swiggyapp.ui.component.text.AnnouncementHeading
import com.paavam.swiggyapp.ui.component.text.SectionHeading
import com.paavam.swiggyapp.ui.navigation.MainNavScreen
import com.paavam.swiggyapp.ui.theme.Typography
import com.paavam.swiggyapp.ui.utils.addShimmer
import com.paavam.swiggyapp.ui.utils.gesturesDisabled
import com.paavam.swiggyapp.viewmodel.HomeUiState
import com.paavam.swiggyapp.viewmodel.HomeViewModel
import com.paavam.swiggyapp.viewmodel.SwiggyViewModel
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@Composable
fun MainContent(
    outerPadding: PaddingValues,
    swiggyViewModel: SwiggyViewModel,
    mainNavController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            val isScrollStateChanged by remember {
                derivedStateOf { scrollState.firstVisibleItemScrollOffset != 0 }
            }

            HomeTopAppBar(
                isScrollStateChanged,
                swiggyViewModel,
                mainNavController
            )
        }
    ) {
        val homeViewState = homeViewModel.state.collectAsState()
        when (homeViewState.value.uiState) {
            is UiState.Loading -> LoadingHomeScreen()
            is UiState.Failed -> ErrorHomeScreen()
            is UiState.Success -> {
                when (homeViewState.value) {
                    is HomeUiState.SwiggyNotAvailableInArea -> {
                        /* Show Swiggy awesomeness not available in area screen */
                    }
                    is HomeUiState.SwiggyAvailableLoadHomeScreen -> {
                        SuccessHomeScreen(
                            scrollState, outerPadding, mainNavController,
                            homeViewState.value as HomeUiState.SwiggyAvailableLoadHomeScreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorHomeScreen() {
    //show error screen
}

/* Shimmer Effect Loading HomeScreen */
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
                .width(80.dp)
                .height(20.dp)
                .padding(vertical = 10.dp)
                .addShimmer(true)
        )

        val size = 85.dp
        LazyRow(
            modifier = Modifier
                .gesturesDisabled(true)
        ) {
            items(items = (1..10).toList()) {
                Column(
                    modifier = Modifier
                        .width(width = size)
                        .padding(end = 10.dp)
                ) {
                    GrayDivider(
                        modifier = Modifier
                            .addShimmer(true)
                            .size(size)
                            .clip(RoundedCornerShape(5.dp))
                    )
                    GrayDivider(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .addShimmer(true)
                            .size(width = size, height = 15.dp)
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        ImageWithPlaceholder(
            imageUrl = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .addShimmer(true)
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)),
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@ExperimentalFoundationApi
@Composable
fun SuccessHomeScreen(
    scrollState: LazyListState, // Higher level is invoked, and reflected throughout
    outerPaddingValues: PaddingValues,
    mainNavController: NavController,
    homeUiState: HomeUiState.SwiggyAvailableLoadHomeScreen,
    widgetBottomPadding: Dp = 10.dp
) {
    val homeViewModel: HomeViewModel = hiltViewModel()

    /**
     * These few sections are collected as observable state,
     * so as to display the data only when they are available.
     * Otherwise Loading Animation will progress for each scroll.
     */
    val popularCurationsState = homeViewModel.popularCurations.collectAsState(UiState.loading())
    val nearbyRestaurantsState = homeViewModel.nearbyRestaurants.collectAsState(UiState.loading())
    val latestOnBlockRestaurantsState =
        homeViewModel.latestOnBlockRestaurants.collectAsState(UiState.loading())
    val topOfferRestaurantsState =
        homeViewModel.topOfferRestaurants.collectAsState(UiState.loading())

    val helloBarMessages = homeUiState.helloBarMessages
    val quickTiles = homeUiState.quickTiles
    val announcementMessage = homeUiState.announcementMsg

    val isRefreshing by remember { mutableStateOf(homeUiState.uiState) }
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing is UiState.Loading),
        onRefresh = { homeViewModel.refresh() },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = outerPaddingValues.calculateBottomPadding()),
            state = scrollState
        ) {
            // Top announcements section
            item {
                TopHelloBar(helloBarMessages)
            }

            item {
                if (announcementMessage.isNotBlank()) {
                    AnnouncementHeading(
                        message = announcementMessage,
                        modifier = Modifier.padding(bottom = widgetBottomPadding),
                    )
                }
            }

            //Categories or Quick Tiles
            item {
                QuickTilesList(
                    quickTiles,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                val roundShape = RoundedCornerShape(18.dp)
                SectionHeading("Gear-up for the big season!")
                LazyRow(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .padding(bottom = widgetBottomPadding)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = PreviewData.prepareOffersSquareBanners()) {
                        ImageWithPlaceholder(
                            imageUrl = it,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clickable { }
                                .size(160.dp)
                                .shadow(2.dp, roundShape)
                                .border(0.7.dp, Color(0x1A000000), shape = roundShape)
                                .clip(roundShape)
                        )
                    }
                }
            }

            item {
                SectionHeading(
                    "Top Picks For You",
                    null,
                    R.drawable.ic_thumbs_up,
                    showSeeAllIcon = false
                )
                SingleRowRestaurants(
                    homeUiState.topPicksRestaurants,
                    paddingValues = PaddingValues(horizontal = 15.dp),
                    mainNavController
                )
            }

            when (popularCurationsState.value) {
                is UiState.Success -> {
                    item {
                        val cuisineList =
                            (popularCurationsState.value as UiState.Success<List<Cuisine>>).data
                        SectionHeading(title = "Popular Curations")
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
                }
                is UiState.Loading -> {
                    item { ShowCircularBottomProgress() }
                    return@LazyColumn
                }
                is UiState.Failed -> {
                    /* Do something  */
                }
            }

            when (latestOnBlockRestaurantsState.value) {
                is UiState.Success -> {
                    item {
                        Column(
                            modifier = Modifier.padding(bottom = widgetBottomPadding)
                        ) {
                            SectionHeading(
                                "Latest on the block!",
                                null,
                                R.drawable.ic_offers_filled,
                                showSeeAllIcon = false
                            )
                            DoubleRowRectangleRestaurants(
                                spotlightRestaurants = (latestOnBlockRestaurantsState.value as UiState.Success<List<Restaurant>>).data,
                                paddingValues = PaddingValues(horizontal = 15.dp),
                                modifier = Modifier.fillParentMaxWidth(0.45f),
                                mainNavController = mainNavController
                            )
                        }
                    }
                }
                is UiState.Loading -> {
                    item { ShowCircularBottomProgress() }
                    return@LazyColumn
                }
                is UiState.Failed -> {
                    // Throw/Collect Errors
                }
            }

            when (topOfferRestaurantsState.value) {
                is UiState.Success -> {
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
                            spotlightRestaurants = (topOfferRestaurantsState.value as UiState.Success<List<Restaurant>>).data,
                            mainNavController = mainNavController
                        )
                    }
                }
                is UiState.Loading -> {
                    item { ShowCircularBottomProgress() }
                    return@LazyColumn
                }
                is UiState.Failed -> {
                    // do something
                }
            }

            val bannersUrls = PreviewData.prepareBannerImages()

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = widgetBottomPadding)
                ) {
                    SectionHeading("Today's Featured")

                    val pagerState = rememberPagerState()
                    HorizontalPager(
                        count = bannersUrls.size,
                        state = pagerState,
                        contentPadding = PaddingValues(start = 15.dp, end = 32.dp),
                        modifier = Modifier
                            .fillMaxWidth(),
                        itemSpacing = 15.dp
                    ) { page_no ->
                        // Our page content
                        ImageWithPlaceholder(
                            imageUrl = bannersUrls[page_no],
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clickable { }
                                .height(180.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)),
                        )
                    }

                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
                        activeColor = MaterialTheme.colors.primaryVariant,
                        inactiveColor = Color(0x1A000000)
                    )
                }
            }

            when (nearbyRestaurantsState.value) {
                is UiState.Success -> {
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

                    items(items = (nearbyRestaurantsState.value as UiState.Success<List<Restaurant>>).data) {
                        RestaurantItem(
                            it,
                            Modifier.fillMaxWidth(),
                            onRestaurantClick = {
                                mainNavController.navigate(MainNavScreen.Restaurant.route + "/${it.restaurantId}") {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                    item {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primaryVariant,
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
                            modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp)
                        ) {
                            Text(
                                "See all restaurants",
                                style = Typography.h5,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .padding(horizontal = 2.dp, vertical = 5.dp)
                                    .align(Alignment.CenterVertically)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                is UiState.Loading -> {
                    item { ShowCircularBottomProgress() }
                    return@LazyColumn
                }
                is UiState.Failed -> {
                    // do something
                }
            }

            item {
                FooterStaticImage()
            }
        }
    }
}

@Composable
fun ShowCircularBottomProgress(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 20.dp, bottom = 75.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            strokeWidth = 2.dp
        )
    }
}

@Composable
fun TopHelloBar(contentList: List<HelloBar>, modifier: Modifier = Modifier) {
    val roundShape = RoundedCornerShape(50)
    var i by remember { mutableStateOf(0) }
    val helloCount = contentList.size

    if (helloCount == 0) return

    LaunchedEffect(1) {
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
                        .clickable {
                            mainNavController.navigate(MainNavScreen.ShowAddresses.route) {
                                launchSingleTop = true
                            }
                        }
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
//                            mainNavController.navigate(MainNavScreen.ShowAddresses.route){
//                        launchSingleTop = true
//                    }
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

    HorizontalSliderUi(horizontalScrollState, Modifier.padding(top = 10.dp, bottom = 20.dp))
}
