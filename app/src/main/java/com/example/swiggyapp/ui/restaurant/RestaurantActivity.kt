package com.example.swiggyapp.ui.restaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swiggyapp.R
import com.example.swiggyapp.ui.theme.SwiggyTheme
import com.example.swiggyapp.ui.theme.Typography
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
                ) { outerPaddingValues ->
                    RestaurantContent(isScrollStateChanged, outerPaddingValues)
                }
            }
        }
    }
}

@Composable
fun BottomQuickMenu() {
    ExtendedFloatingActionButton(
        text = {
            Text(
                text = "Browse Menu".uppercase(Locale.getDefault()),
                color = Color.White,
                style = Typography.h4
            )
        },
        onClick = { },
        icon = {
            Icon(
                painterResource(id = R.drawable.ic_knife_fork),
                null,
                tint = Color.White
            )
        },
        backgroundColor = Color.DarkGray,
        elevation = FloatingActionButtonDefaults.elevation(3.dp, 3.dp)
    )
}

@Composable
fun RestaurantContent(
    isScrolling: Boolean,
    outerPaddingValues: PaddingValues
) {
    //implement it
}

@Composable
fun RestaurantPageTopAppBar(
    isScrolling: Boolean,
    modifier: Modifier = Modifier
) {
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
            }
    )
}

@Preview("bottom preview", showBackground = true)
@Composable
fun BottomPreview() {
    BottomQuickMenu()
}
@Preview("restaurant content", showBackground = true)
@Composable
fun RestaurantPreview() {
//    RestaurantContent(false, PaddingValues(10.dp))
}