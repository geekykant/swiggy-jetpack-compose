package com.example.swiggyapp.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.swiggyapp.R
import com.example.swiggyapp.data.Cuisine
import com.example.swiggyapp.data.prepareSearchCuisines
import com.example.swiggyapp.ui.home.SectionHeading
import com.example.swiggyapp.ui.home.noRippleClickable
import com.example.swiggyapp.ui.theme.Prox
import com.example.swiggyapp.ui.theme.SwiggyTheme
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun SearchScreen(
    outerPaddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = viewModel()
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
                    .fillMaxWidth(),
                searchViewModel
            )
            Divider(modifier = modifier.padding(vertical = 10.dp))
        }

        item {
            fun prepareRecentSearches() =
                listOf("Pizza Hut", "Chicking", "Aryaas", "Admans Pizza", "Westleys Restocafe")
            RecentSearchesComposable(prepareRecentSearches(), searchViewModel)
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
                        modifier = Modifier
                            .noRippleClickable {
                                searchViewModel.onSearchTextChange(it.name)
                            }
                            .fillParentMaxWidth(0.20f)
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
fun SearchBar(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel
) {
    val searchText: String by searchViewModel.searchText.observeAsState("")
    val searchTextStyle = TextStyle(
        fontFamily = Prox,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp
    )

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchViewModel.onSearchTextChange(it) },
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
            if (searchText.isNotBlank())
                Icon(
                    Icons.Outlined.Close,
                    null,
                    modifier = Modifier
                        .clickable { searchViewModel.onSearchTextChange("") }
                )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity)
        ),
        maxLines = 1
    )
}

@Composable
fun CuisineItemComposable(
    cuisine: Cuisine,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 12.sp,
    bottomTextPadding: PaddingValues = PaddingValues(4.dp)
) {
    Column(
        modifier = modifier.padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = rememberCoilPainter(
                cuisine.imageUrl,
                fadeInDurationMs = 100,
                previewPlaceholder = R.drawable.ic_cuisine,
            ),
            contentDescription = null,
            modifier = Modifier
                .shadow(1.dp, CircleShape)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )

        Text(
            text = cuisine.name,
            modifier = Modifier
                .padding(bottomTextPadding),
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun RecentSearchesComposable(
    recentSearchesList: List<String>,
    searchViewModel: SearchViewModel
) {
    val showFullList = remember { mutableStateOf(false) }

    SectionHeading(
        title = "Recent Searches",
        seeAllText = if (showFullList.value) "Show Less" else "Show More",
        showSeeAllText = recentSearchesList.size > 3,
        seeAllTextColor = MaterialTheme.colors.primaryVariant,
        seeAllOnClick = {
            showFullList.value = !showFullList.value
        }
    )

    val showCount = if (showFullList.value) recentSearchesList.size else 3
    recentSearchesList.subList(0, showCount).forEach {
        RecentSearchItem(
            it,
            onClick = { searchItem ->
                searchViewModel.onSearchTextChange(searchItem)
            }
        )
    }
}

@Composable
fun RecentSearchItem(searchItem: String, onClick: (searchItem: String) -> Unit) {
    val textColor = Color.Black.copy(0.35f)
    Row(
        modifier = Modifier
            .noRippleClickable { onClick(searchItem) }
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
                text = searchItem,
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


@Preview("Search Area", showBackground = true)
@Composable
fun TestPreview() {
    SwiggyTheme {
        SearchScreen(outerPaddingValues = PaddingValues(10.dp))
    }
}
