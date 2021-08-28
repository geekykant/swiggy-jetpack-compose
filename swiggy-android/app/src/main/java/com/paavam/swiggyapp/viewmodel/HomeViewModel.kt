package com.paavam.swiggyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.core.ResponseResult
import com.paavam.swiggyapp.core.data.cuisine.model.Cuisine
import com.paavam.swiggyapp.core.data.message.model.HelloBar
import com.paavam.swiggyapp.core.data.message.model.QuickTile
import com.paavam.swiggyapp.core.data.restaurant.model.Restaurant
import com.paavam.swiggyapp.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
):ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> get() = _state

    private val refreshing = MutableStateFlow(false)

    init {
        prepareHomeData()
    }

    private fun prepareHomeData() {
        viewModelScope.launch {
            val quickTiles = when (val quickTiles = homeRepository.fetchTilesContent()) {
                is ResponseResult.Success -> quickTiles.data
                is ResponseResult.Error -> emptyList() /* Throw error message */
            }
            val helloBarMessages =
                when (val helloBarMessages = homeRepository.fetchHelloBarContent()) {
                    is ResponseResult.Success -> helloBarMessages.data
                    is ResponseResult.Error -> emptyList() /* Throw error message */
                }
            val nearbyRestaurants =
                when (val nearbyRestaurants = homeRepository.fetchRestaurantsList()) {
                    is ResponseResult.Success -> nearbyRestaurants.data
                    is ResponseResult.Error -> emptyList() /* Throw error message */
                }
            val popularCurations =
                when (val popularCurations = homeRepository.fetchPopularCuisines()) {
                    is ResponseResult.Success -> popularCurations.data
                    is ResponseResult.Error -> emptyList() /* Throw error message */
                }

            _state.value = HomeViewState(
                quickTiles = quickTiles,
                helloBarMessages = helloBarMessages,
                nearbyRestaurants = nearbyRestaurants,
                popularCurations = popularCurations,
                refreshing = refreshing.value,
                errorMessage = null
            )
        }
    }

    fun refresh(force: Boolean = true) {
        viewModelScope.launch {
            runCatching {
                refreshing.value = true
                delay(3000)
                prepareHomeData()
            }
            refreshing.value = false
        }
    }
}

data class HomeViewState(
    val quickTiles: List<QuickTile> = emptyList(),
    val helloBarMessages: List<HelloBar>? = null,
    val nearbyRestaurants: List<Restaurant> = emptyList(),
    val popularCurations: List<Cuisine> = emptyList(),
    val refreshing: Boolean = false,
    val errorMessage: String? = null
)