package com.paavam.swiggyapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paavam.swiggyapp.core.repository.HomeRepository
import com.paavam.swiggyapp.core.repository.ResponseResult
import com.paavam.swiggyapp.data.Cuisine
import com.paavam.swiggyapp.data.HelloBar
import com.paavam.swiggyapp.data.QuickTile
import com.paavam.swiggyapp.data.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
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