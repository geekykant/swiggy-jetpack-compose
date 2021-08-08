package com.example.swiggyapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiggyapp.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> get() = _state

    private val refreshing = MutableStateFlow(false)

    init {
        prepareHomeData()
    }

    private fun prepareHomeData() {
        viewModelScope.launch {
            _state.value = HomeViewState(
                quickTiles = homeRepository.prepareTilesContent(),
                helloBarMessages = homeRepository.prepareHelloBarContent(),
                nearbyRestaurants = homeRepository.prepareRestaurants(),
                popularCurations = homeRepository.preparePopularCurations(),
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