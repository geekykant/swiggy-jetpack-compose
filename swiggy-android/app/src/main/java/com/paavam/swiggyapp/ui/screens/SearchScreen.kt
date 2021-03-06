package com.paavam.swiggyapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.ui.component.listitem.CuisineItem
import com.paavam.swiggyapp.ui.component.listitem.RecentSearchItem
import com.paavam.swiggyapp.ui.component.text.SectionHeading
import com.paavam.swiggyapp.ui.theme.DividerGreyColor
import com.paavam.swiggyapp.ui.theme.Prox
import com.paavam.swiggyapp.ui.theme.SwiggyTheme
import com.paavam.swiggyapp.ui.utils.noRippleClickable
import com.paavam.swiggyapp.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    outerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = modifier
            .padding(
                top = outerPadding.calculateTopPadding(),
                bottom = outerPadding.calculateBottomPadding()
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
            RecentSearchesComposable(prepareRecentSearches())
            Divider(
                modifier = modifier
                    .height(10.dp),
                color = DividerGreyColor
            )
        }

        item {
            Spacer(modifier = modifier.height(20.dp))
            SectionHeading(
                title = "Popular Cuisines"
            )

            val searchCuisinesList = PreviewData.prepareSearchCuisines()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                searchCuisinesList.forEach {
                    CuisineItem(
                        cuisine = it,
                        modifier = Modifier
                            .height(160.dp)
                            .noRippleClickable {
                                searchViewModel.onSearchTextChange(it.name)
                            }
                            .fillParentMaxWidth(0.20f)
                    )
                }
            }
            Spacer(modifier = modifier.height(10.dp))
            Divider(
                modifier = modifier
                    .height(10.dp),
                color = DividerGreyColor
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
            focusedIndicatorColor = MaterialTheme.colors.onSurface.copy(0.5f),
            backgroundColor = MaterialTheme.colors.background.copy(alpha = 0.1f)
        ),
        maxLines = 1
    )
}

@Composable
fun RecentSearchesComposable(
    recentSearchesList: List<String>,
    searchViewModel: SearchViewModel = hiltViewModel()
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


@Preview("Cuisine Area", showBackground = true)
@Composable
fun TestPreview() {
    SwiggyTheme {
        CuisineItem(PreviewData.preparePopularCurations()[0])
    }
}
