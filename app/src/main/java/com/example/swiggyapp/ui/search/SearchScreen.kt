package com.example.swiggyapp.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swiggyapp.R
import com.example.swiggyapp.data.Cuisine
import com.example.swiggyapp.data.prepareSearchCuisines
import com.example.swiggyapp.ui.home.SectionHeading
import com.example.swiggyapp.ui.theme.Prox
import com.example.swiggyapp.ui.theme.SwiggyTheme
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun SearchScreen(
    outerPaddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(
                top = outerPaddingValues.calculateTopPadding(),
                bottom = outerPaddingValues.calculateBottomPadding()
            )
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 15.dp)
    ) {
        item {
            SearchBar(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth()
            )
            Divider(modifier = modifier.padding(vertical = 10.dp))
        }

        item {
            RecentSearchesComposable(prepareRecentSearches())
            Divider(
                modifier = modifier
                    .height(10.dp),
                color = Color(0xD000000)
            )
        }

        item {
            Spacer(modifier = modifier.height(20.dp))
            SectionHeading(
                title = "Popular Cuisines",
                showSeeAllText = false,
            )

            val searchCuisinesList = prepareSearchCuisines()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                searchCuisinesList.forEach {
                    CuisineItemComposable(
                        cuisine = it,
                        modifier = modifier.fillParentMaxWidth(0.20f)
                    )
                }
            }
            Divider(
                modifier = modifier
                    .height(10.dp),
                color = Color(0xD000000)
            )
        }
    }
}

@Composable
fun CuisineItemComposable(cuisine: Cuisine, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = rememberCoilPainter(
                cuisine.imageUrl,
                fadeInDurationMs = 100,
                previewPlaceholder = R.drawable.ic_cuisine
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Inside,
        )

        Text(
            text = cuisine.name,
            modifier = Modifier
                .padding(vertical = 2.dp),
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}

@Composable
fun RecentSearchesComposable(recentSearchesList: List<String>) {
    val showFullList = remember { mutableStateOf(false) }
    val textColor = Color.Black.copy(0.35f)

    SectionHeading(
        title = "Recent Searches",
        seeAllText = if (showFullList.value) "Show Less" else "Show More",
        showSeeAllText = recentSearchesList.size > 3,
        seeAllTextColor = MaterialTheme.colors.primaryVariant,
        seeAllOnClick = {
            showFullList.value = !showFullList.value
        }
    )

    recentSearchesList
        .subList(
            0,
            if (showFullList.value) recentSearchesList.size else 3
        ).forEach {
            Row(
                modifier = Modifier
                    .clickable { }
                    .padding(horizontal = 20.dp),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 5.dp),
                    tint = textColor
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = it,
                        color = textColor,
                        modifier = Modifier
                            .padding(vertical = 15.dp)
                            .fillMaxHeight(),
                        fontWeight = FontWeight.Normal,
                        fontSize = 19.sp
                    )
                    Divider(thickness = 0.5.dp)
                }
            }
        }
}

fun prepareRecentSearches() = listOf(
    "Pizza Hut",
    "Chicking",
    "Aryaas",
    "Admans Pizza",
    "Westleys Restocafe"
)

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    val searchText = remember { mutableStateOf("") }
    val searchTextStyle = TextStyle(
        fontFamily = Prox,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp
    )

    OutlinedTextField(
        value = searchText.value,
        onValueChange = { searchText.value = it },
        modifier = modifier,
        placeholder = {
            Text(
                "Search for restaurants and food",
                modifier = Modifier.fillMaxHeight(),
                textAlign = TextAlign.Center,
                style = searchTextStyle
            )
        },
        textStyle = searchTextStyle,
        trailingIcon = {
            if (searchText.value.isNotBlank())
                Icon(
                    Icons.Outlined.Close,
                    null,
                    modifier = Modifier.clickable { searchText.value = "" }
                )
        },
    )
}


@Preview("Search Area", showBackground = true)
@Composable
fun TestPreview() {
    SwiggyTheme {
        SearchScreen(outerPaddingValues = PaddingValues(10.dp))
    }
}
