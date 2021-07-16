package com.example.swiggyapp

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
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

enum class OfferSnackType {
    FLAT_DEAL, BASIC
}

class OfferSnack(val message: String, val offerType: OfferSnackType)
class HelloContent(
    val message: String,
    val isClickable: Boolean = false,
    val navTarget: Boolean = false
)

class Offer(val icon: Int, val offerMessage: String)
class Restaurant(
    val name: String,
    val dishTagline: String,
    val location: String,
    val distance: String,
    val rating: Float,
    val distanceTimeMinutes: Int,
    val averagePricingForTwo: Int,
    val imageUrl: String,
    val allOffers: List<Offer>?,
    val offerSnack: OfferSnack?,
    val isBestSafety: Boolean = false
)

class TagTagline(val title: String, val tagLine: String, val imageUrl: String)

@Composable
fun MyScreenContent() {
    fun prepareContent(): List<TagTagline> =
        listOf(
            TagTagline(
                "Restaurant", "Enjoy your favourite treats",
                "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_220,h_220,c_fill/yy09xti5d3buoklibtuc"
            ),
            TagTagline(
                "Genie", "Anything you need, delivered",
                "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,q_auto,w_220,h_220,c_fill/ic_deliveryman_fstjt2"
            ),
            TagTagline(
                "Meat", "Fresh meat & seafood",
                "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,q_auto,w_220,h_220/pexels-geraud-pfeiffer-6542791_my49hm.jpg"
            ),
            TagTagline(
                "Book Shops", "Delivery from Book Shops",
                "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,q_auto,w_220,h_220,c_fill/pexels-martin-de-arriba-7171398_mtyiv4.jpg"
            ),
            TagTagline(
                "Care Corner", "Find essentials & help loved ones",
                "https://res.cloudinary.com/paavam/image/upload/fl_lossy,f_auto,q_auto,w_220,h_220,c_fill/pexels-karolina-grabowska-4226773_ju5e0w.jpg"
            )
        )

    fun prepareHelloBarContent(): List<HelloContent> =
        listOf(
            HelloContent("Rainy weather! Additional fee will apply to reward delivery partners for being out on the streets"),
            HelloContent(
                "Why not let us cover the delivery fee on your food orders?",
                isClickable = true,
                navTarget = true
            )
        )

    fun prepareRestaurants(): List<Restaurant> =
        listOf(
            Restaurant(
                "Aryaas",
                "South Indian, Chineese, Arabian, North India",
                "Kakkanad",
                "7.2 kms",
                4.2f,
                53,
                400,
                "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_200,h_220,c_fill/jmkzdtpvr6njj3wvokrj",
                listOf(Offer(R.drawable.ic_offers, "40% off upto ₹80")),
                OfferSnack("40% OFF", OfferSnackType.BASIC)
            ), Restaurant(
                "McDonald's",
                "American, Continental, Fast Food, Desserts",
                "Edapally",
                "3.6 kms",
                3.2f,
                39,
                200,
                "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_200,h_220,c_fill/ndxghfzqe4qc2dacxiwd",
                null,
                null
            ), Restaurant(
                "Hotel Matoshri",
                "Chinese, Fast Food",
                "Kaveri Hospital, Shingoli",
                "1.2 kms",
                3.8f,
                53,
                200,
                "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_200,h_220,c_fill/shxshuxficcjcwyixw0s",
                listOf(Offer(R.drawable.ic_offers, "40% off upto ₹80")),
                OfferSnack("20% OFF", OfferSnackType.BASIC)
            )
        )

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

        Column(modifier = Modifier.fillMaxHeight()) {
            StickyTopAppBar()
            TopHelloBar(prepareHelloBarContent())
            BoxItemList(prepareContent())
            var prepList = prepareRestaurants()
            for(i in 1..20){
                prepList = prepList.plus(
                    prepList.get(i%3)
                )
            }

            AllRestaurantsNearby(prepList)
        }
    }
}

@Composable
fun TopHelloBar(contentList: List<HelloContent>) {
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
        modifier = Modifier
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
            modifier = Modifier.padding(3.dp).height(28.dp),
            maxLines = 2
        )
    }
}

@Composable
fun AllRestaurantsNearby(restaurantsList: List<Restaurant>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = restaurantsList) {
            RestaurantItem(it)
        }
    }
}

@Composable
fun RestaurantItem(r: Restaurant) {
    val height = 120.dp

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
                    .height(110.dp)
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
fun StickyTopAppBar() {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
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
        backgroundColor = MaterialTheme.colors.surface
    )
}

@Composable
fun BoxItemList(content: List<TagTagline>, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier.height(150.dp)) {
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