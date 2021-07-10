package com.example.swiggyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.swiggyapp.ui.theme.Typography


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
    val name: String, val dishTagline: String, val location: String, val distance: String,
    val rating: Float, val distanceTimeMinutes: Int, val averagePricingForTwo: Int, val image: Int,
    val allOffers: List<Offer>?, val offerSnack: OfferSnack?, val isBestSafety: Boolean = false
)

@Composable
fun MyScreenContent() {
    fun prepareContent(): LinkedHashMap<String, String> {
        val tagTaglineMap = LinkedHashMap<String, String>()
        tagTaglineMap["Restaurant"] = "Enjoy your favourite treats"
        tagTaglineMap["Genie"] = "Anything you need, delivered"
        tagTaglineMap["Meat"] = "Fresh meat & seafood"
        tagTaglineMap["Book Shops"] = "Delivery from Book Shops"
        tagTaglineMap["Care Corner"] = "Find essentials & help loved ones"
        return tagTaglineMap
    }

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
                R.drawable.ic_restaurant1,
                listOf(Offer(R.drawable.ic_offers, "40% off upto ₹80")),
                OfferSnack("40% OFF", OfferSnackType.BASIC)
            ), Restaurant(
                "McDonald's", "American, Continental, Fast Food, Desserts",
                "Edapally", "3.6 kms", 3.2f, 39,
                200, R.drawable.ic_restaurant1, null,
                null
            )
        )

    Surface(color = MaterialTheme.colors.background) {
        Column(modifier = Modifier.fillMaxHeight()) {
            StickyTopAppBar()
            TopHelloBar(prepareHelloBarContent())
            BoxItemList(prepareContent())
            AllRestaurantsNearby(prepareRestaurants())
        }
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
    Row(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 2.dp, vertical = 0.dp)
                .width(130.dp)
                .height(128.dp),
        ) {
            Image(
                painterResource(id = r.image),
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .width(130.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.Crop
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
                        fontSize = 18.sp
                    )
                }
                OfferSnackType.FLAT_DEAL -> {

                }
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxHeight(),
        ) {
            Text(
                text = r.name,
                style = Typography.h1,
                maxLines = 1
            )
            Text(text = r.dishTagline, maxLines = 1)
            Text(text = r.location, maxLines = 1)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_rating),
                    contentDescription = null,
                    tint = Color.DarkGray
                )
                Text(text = r.rating.toString())
                Text(text = ".")
                Text(text = "₹${r.averagePricingForTwo} for two")
            }
            Divider()
            LazyRow {
                if (!r.allOffers.isNullOrEmpty()) {
                    items(items = r.allOffers) { offer ->
                        Row() {
                            Icon(
                                painterResource(id = R.drawable.ic_offers),
                                contentDescription = null
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
fun TopHelloBar(contentList: List<HelloContent>) {
    val singleTitle = contentList[0].message
    val roundShape = RoundedCornerShape(50)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(R.drawable.ic_rain),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(2.dp)
                .background(Color(0xF5ECECEC), roundShape)
                .clip(roundShape),
            contentScale = ContentScale.Inside,
        )


        val value by animateIntAsState(
            targetValue = 1,
            animationSpec = tween(
                durationMillis = 300,
                delayMillis = 50,
                easing = LinearOutSlowInEasing
            )
        )

        Text(
            text = singleTitle,
            modifier = Modifier.padding(3.dp),
            maxLines = 2,
        )
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
                        .padding(2.dp, 0.dp)
                        .weight(6f)
                ) {
                    Row {
                        Image(
                            painter = painterResource(R.drawable.ic_location),
                            contentDescription = null,
                            modifier = Modifier.padding(3.dp)
                        )
                        Text(
                            text = "Home",
                            style = Typography.h1,
                        )
                    }
                    Text(
                        "Tra-32 (last House On Right), Elamakkara Mamangalam, Ernakulam",
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(1.dp)
                        .weight(2f)
                        .align(Alignment.CenterVertically),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_offers),
                            contentDescription = null,
                            modifier = Modifier.padding(3.dp)
                        )
                        Text(text = "Offers", maxLines = 1)
                    }
                }
            }
        },
        backgroundColor = Color.White
    )
}

@Composable
fun BoxItemList(content: LinkedHashMap<String, String>, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        items(items = content.toList()) {
            BoxItem(it.first, it.second)
        }
    }
}

@Composable
fun BoxItem(heading: String, tagline: String) {
    val roundShape = RoundedCornerShape(20.dp)
    Column(
        modifier = Modifier
            .width(130.dp)
            .padding(5.dp)
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .border(1.dp, Color(0x32323299), shape = roundShape),
        ) {
            Text(
                text = heading,
                modifier = Modifier
                    .zIndex(2f)
                    .padding(horizontal = 3.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Typography.h1
            )
            Image(
                painter = painterResource(R.drawable.ic_deliveryman),
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
                .padding(horizontal = 3.dp, vertical = 2.dp),
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