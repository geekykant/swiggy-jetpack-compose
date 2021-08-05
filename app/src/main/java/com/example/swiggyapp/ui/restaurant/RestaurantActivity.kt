package com.example.swiggyapp.ui.restaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swiggyapp.R
import com.example.swiggyapp.ui.home.AnimateUpDown
import com.example.swiggyapp.ui.home.noRippleClickable
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
    modifier: Modifier = Modifier
) {
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
            Column(
                modifier.padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Pizza Hut",
                    style = Typography.h1
                )
                Text(
                    text = "Pizzas, Fast Food",
                    style = Typography.h3
                )
                Text(
                    text = "Lulu Mall | 3.6 kms",
                    style = Typography.h3
                )
            }
        }

        item {
            DashedDivider()
            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("4.0", style = Typography.h2)
                    val movingList =
                        listOf("Tate 74%", "Portion 70%", "500+ ratings", "Packing 7500")

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
                    Text("39 mins", style = Typography.h2)
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

            Card(
                backgroundColor = Color(0xFFE0F7FA),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.5.dp, Color(0x66009688)),
                elevation = 2.dp,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 15.dp)
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

            DashedDivider()
        }

        item { GrayDivider() }
        val foodList = prepareRestaurantFoods()
        items(items = foodList) {
            FoodItemComposable(foodItem = it)
        }
        item { GrayDivider() }

    }
}

@Composable
fun FoodItemComposable(
    foodItem: FoodItem,
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
            modifier = Modifier.fillMaxWidth(0.75f),
        ) {
            Row(
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_foodtype),
                    contentDescription = null,
                    tint = when (foodItem.foodType) {
                        FoodType.VEG -> Color(0xFF008000)
                        FoodType.NON_VEG -> Color(0xFF008000)
                    },
                    modifier = Modifier.padding(end = 3.dp)
                )

                foodItem.starText?.let {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_rating),
                        contentDescription = null,
                        tint = Color(0xFFFFC300 )
                    )
                    Text(
                        text = it,
                        modifier = Modifier.padding(horizontal = 3.dp),
                        color = Color(0xFFFFC300 )
                    )
                }
            }

            Text(
                text = foodItem.name,
                style = Typography.h2,
            )

            Text(
                text = "₹${foodItem.price}"
            )
            foodItem.foodContents?.let {
                Text(
                    text = it,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Image(
            painter = rememberCoilPainter(
                foodItem.imageUrl,
                fadeInDurationMs = 100,
                previewPlaceholder = R.drawable.ic_restaurant1,
            ),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(3.dp))
                .fillMaxWidth(1f),
            contentScale = ContentScale.FillWidth,
        )
        Divider()
    }
}

@Composable
fun DashedDivider() {
    Box(
        modifier = Modifier
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

//@Preview("top bar preview", showBackground = true)
//@Composable
//fun TopBarPreview() {
//    RestaurantPageTopAppBar(false)
//}
//
//@Preview("bottom preview", showBackground = true)
//@Composable
//fun BottomPreview() {
//    BottomQuickMenu()
//}

@Preview("restaurant content", showBackground = true)
@Composable
fun RestaurantPreview() {
    RestaurantContent(
        rememberLazyListState(), PaddingValues(10.dp), Modifier.fillMaxSize()
    )
}