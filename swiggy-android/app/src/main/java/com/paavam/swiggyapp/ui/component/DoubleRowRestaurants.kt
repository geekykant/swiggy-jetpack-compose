package com.paavam.swiggyapp.ui.component

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.paavam.swiggyapp.lib.LazyHorizontalGrid
import com.paavam.swiggyapp.lib.items
import com.paavam.swiggyapp.core.data.model.Restaurant
import com.paavam.swiggyapp.ui.RestaurantActivity
import com.paavam.swiggyapp.ui.component.listitem.RestaurantItem

/**
 * Spotlight Restaurants are in 6 columns x 2 rows.
 * We check if they are in the limits for the sublist windowing.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DoubleRowRestaurants(
    spotlightRestaurants: List<Restaurant>,
    modifier: Modifier = Modifier
) {
    fun nearestEven(size: Int): Int = if (size % 2 == 0) size else size.dec()
    val showUpToCount: Int = 12.coerceAtMost(nearestEven(spotlightRestaurants.size))
    val spotlightRestaurantsSublist = spotlightRestaurants.subList(0, showUpToCount)

    val context = LocalContext.current

    LazyHorizontalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(items = spotlightRestaurantsSublist) {
            RestaurantItem(
                r = it,
                modifier = modifier.fillParentMaxWidth(0.8f),
                onRestaurantClick = {
                    context.startActivity(Intent(context, RestaurantActivity::class.java))
                }
            )
        }
    }
}